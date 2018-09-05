package com.intellipix;

import java.util.HashMap;
import java.util.Map;

import android.app.ListActivity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.intellipix.steganography.MimeTypes;

 

public class InventoryActivity extends ListActivity {
   private static Map <Integer,Bitmap> mimeTypeImageMap;
   private static Map <Integer,String> mimeTypeStringMap; 
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Bundle bundle = this.getIntent().getExtras();
		int[] mimeTypeArray=bundle.getIntArray("inventory");		
		setAdapter(mimeTypeArray);
	}
	
	protected void setAdapter(int[] mimeTypeArray) {
		setListAdapter(new InventoryAdapter(this,mimeTypeArray));
	}
	
    private static class InventoryAdapter extends BaseAdapter {
        private LayoutInflater mInflater;
        private int[] mimeTypeArray;
        
        public InventoryAdapter(Context context,int[] mimeTypeArrayParm) {
        	
            // Cache the LayoutInflate to avoid asking for a new one each time.
            mInflater = LayoutInflater.from(context);
            mimeTypeArray=mimeTypeArrayParm;
            
            if (mimeTypeImageMap==null) {
          	  mimeTypeImageMap=new HashMap<Integer,Bitmap>();
        	  mimeTypeImageMap.put(new Integer(MimeTypes.AUDIO), 
                 BitmapFactory.decodeResource(context.getResources(), 
                		R.drawable.audio_mime_type48x48));
         	  mimeTypeImageMap.put(new Integer(MimeTypes.MAP), 
                    BitmapFactory.decodeResource(context.getResources(), 
                    		R.drawable.map_mime_type48x48));
        	  mimeTypeImageMap.put(new Integer(MimeTypes.CAPTION), 
                    BitmapFactory.decodeResource(context.getResources(), 
                    		R.drawable.caption_mime_type48x48));
        	  mimeTypeImageMap.put(new Integer(MimeTypes.DATE), 
                      BitmapFactory.decodeResource(context.getResources(), 
                      		R.drawable.date_mime_type48x48));
            }
            if (mimeTypeStringMap==null) {
        	  mimeTypeStringMap=new HashMap<Integer,String>();
        	  mimeTypeStringMap.put(new Integer(MimeTypes.AUDIO),                   
        			context.getResources().getString(R.string.audio_mime_type));
        	  mimeTypeStringMap.put(new Integer(MimeTypes.MAP),                   
        			context.getResources().getString(R.string.map_mime_type));
        	  mimeTypeStringMap.put(new Integer(MimeTypes.CAPTION),                   
        			context.getResources().getString(R.string.caption_mime_type));
        	  mimeTypeStringMap.put(new Integer(MimeTypes.DATE),                   
          			context.getResources().getString(R.string.date_mime_type));
            }
        }

   
        /**
         * The number of items in the list is determined by the number of speeches
         * in our array.
         *
         * @see android.widget.ListAdapter#getCount()
         */
        public int getCount() {
            return mimeTypeArray.length;
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
            holder.text.setText(mimeTypeStringMap.get(mimeTypeArray[position]));
            holder.icon.setImageBitmap(mimeTypeImageMap.get(mimeTypeArray[position]));
            
            return convertView;
        }

        static class ViewHolder {
            TextView text;
            ImageView icon;
        }
    }
 
}
