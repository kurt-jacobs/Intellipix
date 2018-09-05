package com.intellipix;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.intellipix.filters.PNGFileFilter;
import com.intellipix.util.FileUtils;

public class OpeningActivity extends Activity {
	 private static final int NO_SUITABLE_FILES_FOUND = 1;
	 private static final int DELETE_FILE = Menu.FIRST;
	   
	 public void onCreate(Bundle savedInstanceState) {
	   super.onCreate(savedInstanceState);
       setContentView(R.layout.welcome);    
       
       ImageButton diskButton = (ImageButton) findViewById(R.id.disk);
       diskButton.setOnClickListener(diskButtonListener);   
       
       ImageButton cameraButton = (ImageButton) findViewById(R.id.camera);
       cameraButton.setOnClickListener(cameraButtonListener);   
	 }
 
	 private OnClickListener diskButtonListener = new OnClickListener() {
		 @SuppressWarnings("unchecked")
		 public void onClick(View v) {  			 
			 if (FileUtils.filesAvailable(new PNGFileFilter(),"/sdcard")) {		 
			   Intent intent=new Intent();
			   intent.setClass(OpeningActivity.this, FileSelectForReview.class);
			   OpeningActivity.this.startActivity(intent);
			 } else {
				 showDialog(NO_SUITABLE_FILES_FOUND);
			 }
		 }   	
	 };
	 
	 private OnClickListener cameraButtonListener = new OnClickListener() {
		 @SuppressWarnings("unchecked")
		 public void onClick(View v) {  			 			 			 			 		
		    Intent intent=new Intent();
		    intent.setClass(OpeningActivity.this, PhotoCapture.class);
		    OpeningActivity.this.startActivity(intent);
		 }   	
	 };
	 
	 
	 @Override
	 protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case NO_SUITABLE_FILES_FOUND:
	            return new AlertDialog.Builder(OpeningActivity.this)
	                .setIcon(R.drawable.alert_dialog_icon)
	                .setTitle(R.string.no_compatable_files_found)
	                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
                             // nothing needed here - window will just close
	                    }
	                }).create();
	        }
			return null;
	 }
	 
	 
	 @Override
	 public boolean onCreateOptionsMenu(Menu menu) {
		 super.onCreateOptionsMenu(menu);
		 menu.add(0, DELETE_FILE, 0, R.string.delete_file);
		 return true;
	 }

	 @Override
	 public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {

		 case DELETE_FILE:
			 if (FileUtils.filesAvailable(new PNGFileFilter(),"/sdcard")) {		 
				 Intent intent=new Intent();
				 intent.setClass(OpeningActivity.this, FileSelectForDelete.class);
				 OpeningActivity.this.startActivity(intent);
			 } else {
				 showDialog(NO_SUITABLE_FILES_FOUND);
			 }
			 return true;
		 }

		 return false;
	 }
	 
}
