package com.intellipix;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ListView;

public class InventorySelect extends InventoryActivity {

	   
    @SuppressWarnings("unchecked")
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        setResult(RESULT_OK, (new Intent()).setAction(Integer.toString(position)));
    	finish();      
    }
	
	  // Don't let user hit the back key- parent is waiting for result 
    @Override
    public boolean onKeyDown(int keyCode, android.view.KeyEvent event) {
       boolean handled=false;
    	
       switch (keyCode) {
          case KeyEvent.KEYCODE_BACK:
            handled=true;
            setResult(this.RESULT_CANCELED, (new Intent()).setAction(Integer.toString(-1)));
            finish(); 
            break;
       }
 
       return handled;
    }
	
}
