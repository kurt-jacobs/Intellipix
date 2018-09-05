package com.intellipix;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

import com.intellipix.steganography.MimeTypes;

public class CaptionEntry extends Activity {
    private static final int USAGE_DIALOG = 1;
	private EditText captionText;
 
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.caption_entry);

		captionText = (EditText) findViewById(R.id.captionedittext);   
		ImageButton cancel = (ImageButton) findViewById(R.id.cancel_button);
		cancel.setOnClickListener(cancelButtonListener); 
		
		ImageButton ok = (ImageButton) findViewById(R.id.ok_button);
		ok.setOnClickListener(okButtonListener); 
	}
 
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
			 try {
				 String caption=captionText.getText().toString();
				 
				 Intent intent=new Intent();
				 intent.setClass(CaptionEntry.this, AddContent.class);
				 Bundle bundle=new Bundle();
				 bundle.putString(BundleKey.CAPTION, caption);
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
		            return new AlertDialog.Builder(CaptionEntry.this)
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
