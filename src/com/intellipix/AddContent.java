package com.intellipix;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

import com.intellipix.steganography.MimeTypes;

public class AddContent extends InventoryActivity {
	
	   
	private static final int[] availableMimeType= { MimeTypes.CAPTION,MimeTypes.MAP, MimeTypes.AUDIO };  // TODO: add others later 
	private int userSelectedMimeType;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		 		 
		setAdapter(availableMimeType);
	}
	   
    @SuppressWarnings("unchecked")
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	userSelectedMimeType=availableMimeType[position];
        handleAddMimeType(userSelectedMimeType);
    }
    
    private void handleAddMapMimeType() {
      Intent intent=new Intent();
	  intent.setClass(AddContent.this, MapEntry.class);
	  AddContent.this.startActivityForResult(intent, IntellipixConstants.INVENTORY_ADD);	    	
    }
    
    private void handleAddCaptionMimeType() {
      Intent intent=new Intent();
  	  intent.setClass(AddContent.this, CaptionEntry.class);
  	  AddContent.this.startActivityForResult(intent, IntellipixConstants.INVENTORY_ADD);	    	
    }
    
	private void handleAddAudioMimeType() {		
		Intent intent = new Intent(MediaStore.Audio.Media.RECORD_SOUND_ACTION);
		startActivityForResult(intent, IntellipixConstants.INVENTORY_ADD);
	}
    
    private void handleAddMimeType(int mimeType) {
    	switch (mimeType) {
    	 case MimeTypes.CAPTION:
   		    handleAddCaptionMimeType();    	
   		    break;
   		  
    	  case MimeTypes.MAP:
    		 handleAddMapMimeType();    	
    		 break;
    		 
    	  case MimeTypes.AUDIO:
    		 handleAddAudioMimeType();
    	}
    }
	
	// Don't let user hit the back key- parent is waiting for result 
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
       boolean handled=false;
    	
       switch (keyCode) {
          case KeyEvent.KEYCODE_BACK:
            handled=true;
            setResult(RESULT_CANCELED, (new Intent()).setAction(Integer.toString(-1)));
            finish(); 
            break;
       }
 
       return handled;
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data) {
    	if (resultCode==RESULT_OK) {
    	   if (requestCode==IntellipixConstants.INVENTORY_ADD) {    		    
    	 	   data.putExtra(BundleKey.MIMETYPE, (byte)userSelectedMimeType);     		 		   
    		   setResult(RESULT_OK, data);
    		   finish(); 
    	   }  
    	}
    }
	
}
