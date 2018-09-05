package com.intellipix;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.intellipix.filters.PNGFileFilter;
import com.intellipix.util.BufferUtils;
import com.intellipix.util.FileUtils;

public abstract class FileSelect extends ListActivity {
    private static final int DIALOG_PROGRESS = 1;
 
    protected int filesToLoad=0;
    private static Handler mProgressHandler;
	private static ProgressDialog mProgressDialog;
	protected static List<File> fileList;
	private static HashMap<String,Boolean> enchancedFileMap;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		fileList=
          	FileUtils.getFileList(getFilenameFilter(),"/sdcard");	
	 
		filesToLoad=fileList.size();
		if (filesToLoad>0) {
		   mProgressHandler=new FileLoadProgressHandler(filesToLoad);
	  	   showDialog(DIALOG_PROGRESS);
		   createEnchancedFileMap(fileList);
		}
		
		setListAdapterForActivity();
	}
	  
	
	protected void setListAdapterForActivity() {
		setListAdapter(new PhotoSelectAdapter(this));
	}
	
	
    private void createEnchancedFileMap(List<File> fileList) {
    	enchancedFileMap=new HashMap<String,Boolean>();
    	for (File file : fileList) {
    	  enchancedFileMap.put(file.getAbsolutePath(),isEnhanced(file));     
           mProgressHandler.sendEmptyMessage(0);
    	}    	
    }
	
	private class FileLoadProgressHandler extends Handler {
        private int maxFiles;
        private int filesloaded=0;
		private FileLoadProgressHandler(int maxFilesParm) {
		   maxFiles=(maxFilesParm-1);
		   filesloaded=0;
		}
		
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);		
			if (filesloaded > maxFiles) {
				mProgressDialog.dismiss();				
			}
			// No matter how fast it loads, show the dialog
			// for a period of time to let user see it.
			else if (filesloaded == maxFiles) {
				filesloaded++;
				mProgressDialog.incrementProgressBy(1);	
			    mProgressHandler.sendEmptyMessageDelayed(0,
			    		IntellipixConstants.SHOW_LOAD_PROGRESS_MILLIS);			
			} else {
				filesloaded++;
				mProgressDialog.incrementProgressBy(1);			
			}
		}
	}
	
    private static class PhotoSelectAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private Bitmap enhancedIcon;
        private Bitmap blankIcon;
  
        public PhotoSelectAdapter(Context context) {
        	
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
         
            // Icons bound to the rows.
            enhancedIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.enhanced48x48);
            blankIcon = BitmapFactory.decodeResource(context.getResources(), R.drawable.blank48x48);
        }

        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return fileList.size();
        }

        /**
         * Since the data comes from an array, just returning the index is
         * sufficent to get at the data. If we were using a more complex data
         * structure, we would return whatever object represents one row in the
         * list.
         *
         * @see android.widget.ListAdapter#getItem(int)
         */
        public Object getItem(int position) {
            return position;
        }

        /**
         * Use the array index as a unique id.
         *
         * @see android.widget.ListAdapter#getItemId(int)
         */
        public long getItemId(int position) {
            return position;
        }

        /**
         * Make a view to hold each row.
         *
         * @see android.widget.ListAdapter#getView(int, android.view.View,
         *      android.view.ViewGroup)
         */
        public View getView(int position, View convertView, ViewGroup parent) {
            // A ViewHolder keeps references to children views to avoid unneccessary calls
            // to findViewById() on each row.
            ViewHolder holder;

            // When convertView is not null, we can reuse it directly, there is no need
            // to reinflate it. We only inflate a new View when the convertView supplied
            // by ListView is null.
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.list_item_icon_text, null);

                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.text);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);

                convertView.setTag(holder);
            } else {
                // Get the ViewHolder back to get fast access to the TextView
                // and the ImageView.
                holder = (ViewHolder) convertView.getTag();
            }

            // Bind the data efficiently with the holder.
            holder.text.setText(fileList.get(position).getAbsolutePath());
            if (enchancedFileMap.get(fileList.get(position).
            		getAbsolutePath()).equals(Boolean.TRUE)) {
            	holder.icon.setImageBitmap(enhancedIcon);
            } else {
            	holder.icon.setImageBitmap(blankIcon);
            }
 
            return convertView;
        }

        static class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
    
    private static boolean isEnhanced(File file) {
    	boolean enhanced=false;
    	if (file.length() > 100) {
      	  Bitmap bitmap=BitmapFactory.decodeFile(file.getAbsolutePath());
          int picw=IntellipixConstants.ENCHANCED_QUERY_SIZE_BYTES;
    	  int pich=1;
          int[] pix = new int[picw*pich];
          bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
          enhanced=BufferUtils.isBitmapEnhanced(pix);
          pix=null;
    	}
        return enhanced;
    }
 
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
          case DIALOG_PROGRESS:
            mProgressDialog = new ProgressDialog(FileSelect.this);
            mProgressDialog.setIcon(R.drawable.alert_dialog_icon);
            mProgressDialog.setTitle(R.string.loading_files);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setMax(filesToLoad);
            return mProgressDialog;
           
          }         
        return null;
    }
  
    public abstract FilenameFilter getFilenameFilter();
    
    
}
