package com.intellipix;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.intellipix.util.location.LocationManagerHelper;

public class MapEntry extends Activity {
    private static final int USAGE_DIALOG = 1;
	private static final float MULTIPLIER=1000000;
	private EditText latitudeEditText;
	private EditText longitudeEditText;
	private LocationManagerHelper locationHelper;
	 	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.map_entry);
		
		locationHelper = new LocationManagerHelper(this); 
		
	    latitudeEditText = (EditText) findViewById(R.id.latedittext);
		longitudeEditText = (EditText) findViewById(R.id.longedittext);

		ImageButton preview = (ImageButton) findViewById(R.id.map_entry_preview);
		preview.setOnClickListener(previewButtonListener); 
		   
		ImageButton cancel = (ImageButton) findViewById(R.id.cancel_button);
		cancel.setOnClickListener(cancelButtonListener); 
		
		ImageButton ok = (ImageButton) findViewById(R.id.ok_button);
		ok.setOnClickListener(okButtonListener); 
		
		initWithCurrentLocation();
	}
				
	private void initWithCurrentLocation() {		
		Location location = locationHelper.getLastKnownLocation();
		if (location != null)
		{
			latitudeEditText.setText(String.format("%.6f", location.getLatitude()));
			longitudeEditText.setText(String.format("%.6f", location.getLongitude()));
		}		
	}

	private OnClickListener previewButtonListener = new OnClickListener() {
		 @SuppressWarnings("unchecked")
		 public void onClick(View v) {  
			 float latitude=0;
			 float longitude=0;
			 
			 try {
				 latitude=Float.parseFloat(latitudeEditText.getText().toString());
				 longitude=Float.parseFloat(longitudeEditText.getText().toString());
				 Intent intent=new Intent();
				 intent.setClass(MapEntry.this, IntellipixMap.class);
				 Bundle bundle=new Bundle();
				 long[] latituteLongituteArray=
					 new long[] { (long)(latitude*MULTIPLIER),(long)(longitude * MULTIPLIER)};
				 bundle.putLongArray("location", latituteLongituteArray);
				 intent.putExtras(bundle);			   
				 MapEntry.this.startActivity(intent);		
			 } catch (Exception e) {
				 showDialog(USAGE_DIALOG); 
			 }
		 }   	
	 };
	 
	 private OnClickListener cancelButtonListener = new OnClickListener() {
		 @SuppressWarnings("unchecked")
		 public void onClick(View v) {  
			 cancelAdd();			 
		 }
	 };
	 
	 
	 private void cancelAdd() {
		setResult(RESULT_CANCELED, (new Intent()).setAction(Integer
				.toString(-1)));
		finish();
	}
	 
	 
	 private OnClickListener okButtonListener = new OnClickListener() {
		 public void onClick(View v) {  
			 float latitude=0;
			 float longitude=0;

			 try {
				 latitude=Float.parseFloat(latitudeEditText.getText().toString());
				 longitude=Float.parseFloat(longitudeEditText.getText().toString());
				 Intent intent=new Intent();
				 intent.setClass(MapEntry.this, AddContent.class);
				 Bundle bundle=new Bundle();
				 long[] latituteLongituteArray=
					 new long[] { (long)(latitude*MULTIPLIER),(long)(longitude * MULTIPLIER)};
				 bundle.putLongArray(BundleKey.LOCATION, latituteLongituteArray);
				 intent.putExtras(bundle);			   
				 setResult(RESULT_OK, intent);	
				 finish();
			 } catch (Exception e) {
				 showDialog(USAGE_DIALOG);
			 }		 
		 }
	 };
 
	 
	  // Don't let user hit the back key- parent is waiting for result
	    @Override
	    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
	       boolean handled=false;
	    	
	       switch (keyCode) {
	          case KeyEvent.KEYCODE_BACK:
	            handled=true;
	            cancelAdd();
	            break;
	       }
	 
	       return handled;
	    }
	    
	    @Override
		 protected Dialog onCreateDialog(int id) {
		        switch (id) {
		        case USAGE_DIALOG:
		            return new AlertDialog.Builder(MapEntry.this)
		                .setIcon(R.drawable.alert_dialog_icon)
		                .setTitle(R.string.map_entry_usage)
		                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
		                    public void onClick(DialogInterface dialog, int whichButton) {
	                            // nothing needed here - window will just close
		                    }
		                }).create();
		        }
				return null;
		 }
}
