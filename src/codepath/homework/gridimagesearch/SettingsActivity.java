package codepath.homework.gridimagesearch;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity {
	
	Spinner imageSize, colorFilter, imageType;
	ArrayAdapter<CharSequence> isAdapter, cfAdapter, itAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setSpinnerItems();	
		
	}
	
	public void setSpinnerItems() {
		imageSize = (Spinner) findViewById(R.id.sImageSize);
		isAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_size_array, android.R.layout.simple_spinner_item);
		isAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imageSize.setAdapter(isAdapter);
		
		colorFilter = (Spinner) findViewById(R.id.sColorFilter);
		cfAdapter = ArrayAdapter.createFromResource(this,
		        R.array.color_filter_array, android.R.layout.simple_spinner_item);
		cfAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		colorFilter.setAdapter(cfAdapter);
		
		imageType = (Spinner) findViewById(R.id.sImageType);
		itAdapter = ArrayAdapter.createFromResource(this,
		        R.array.image_type_array, android.R.layout.simple_spinner_item);
		itAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		imageType.setAdapter(itAdapter);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit = pref.edit();
		edit.putString(findViewById(R.id.etSiteFilter).getTag().toString(),
				((EditText)findViewById(R.id.etSiteFilter)).getText().toString());
		edit.putString("imageSize", imageSize.getSelectedItem().toString());
		edit.putString("imageType", imageType.getSelectedItem().toString());
		edit.putString("colorFilter", colorFilter.getSelectedItem().toString());
		edit.commit();
	    super.onBackPressed();
	}

	

}
