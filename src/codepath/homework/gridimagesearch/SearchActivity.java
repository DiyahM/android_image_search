package codepath.homework.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends Activity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
	ImageResultArrayAdapter imageAdapter;
	SharedPreferences pref;
	Integer start;
	String query;
		  

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		pref = PreferenceManager.getDefaultSharedPreferences(this);
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		start = 0;
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position,
					long rowId) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
			
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}
	
	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v) {
		query = etQuery.getText().toString();
		start = 0;
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		
		getImageResults();
		
		
		LinearLayout myLayout = (LinearLayout) findViewById(R.id.llFooter);
		myLayout.setVisibility(View.VISIBLE);
		start = start + 8;
		
	}
	
	public void onSearchSettings(MenuItem mi) {
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}
	
	public void getImageResults() {
		String imageSize = pref.getString("imageSize", "");
		String imageType = pref.getString("imageType", "");
		String colorFilter = pref.getString("colorFilter", "");
		String siteFilter = Uri.encode(pref.getString("siteFilter", ""));
		
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?v=1.0&rsz=8&start=" +
		            start + "&q=" + Uri.encode(query) + "&imgsx=" + imageSize + "&imgcolor=" +
				    colorFilter + "&imgtype=" + imageType + "&as_sitesearch=" +
		            siteFilter,
		           new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject response) {
				JSONArray imageJsonResults = null;
				try {
					
					imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					imageResults.clear();
					imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		
	}
	
	public void onLoadMore(View v) {
		getImageResults();
		if (start < 65 ) {
		  start = start + 8;
		} else {
			start = 0;
		}
		
	}
	

}
