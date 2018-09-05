package com.intellipix;

import java.io.FilenameFilter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.intellipix.filters.PNGFileFilter;

public class FileSelectForReview extends FileSelect {
	
    public FilenameFilter getFilenameFilter() {
    	return new PNGFileFilter();
    }
    
    @SuppressWarnings("unchecked")
	@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
    	if (filesToLoad>0) {     	
      	  Intent intent=new Intent();
		  intent.setClass(this, AssembleReview.class);
		  Bundle bundle=new Bundle();
	      bundle.putString(BundleKey.PHOTO_PATH, fileList.get(position).getAbsolutePath());
	      bundle.putInt(BundleKey.PHOTO_SOURCE, IntellipixConstants.DISK_PHOTO_SOURCE);
          intent.putExtras(bundle);
		  startActivity(intent);
    	}
      
    }
	
}
