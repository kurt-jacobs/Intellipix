package com.intellipix;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;

public class SaveFileEntry extends Activity {
	private EditText saveFilenameText;
 	private static String REQUIRED_EXT=".png";
 	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		String filename=bundle.getString(BundleKey.FILENAME);		
		setContentView(R.layout.save_file_entry);

		saveFilenameText = (EditText) findViewById(R.id.saveedittext);   
		saveFilenameText.setText(filename);
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
  
	 // TODO - add validation later
	private boolean isFilenameValid(String filename) {
		return true;
	}
	 
	 private OnClickListener okButtonListener = new OnClickListener() {
		 public void onClick(View v) {  
			 try {
				 String filename=saveFilenameText.getText().toString();
				 if (!filename.toLowerCase().endsWith(REQUIRED_EXT)) {
					 filename=filename + REQUIRED_EXT;
				 }
				 Intent intent=new Intent();
				 intent.setClass(SaveFileEntry.this, SaveFileEntry.class);
				 Bundle bundle=new Bundle();
				 bundle.putString(BundleKey.FILENAME, filename);			 
				 intent.putExtras(bundle);			   
				 setResult(RESULT_OK, intent);	
				 finish();
			 } catch (Exception e) {
				 e.printStackTrace();
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
 
}
