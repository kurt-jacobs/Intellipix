package com.intellipix;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.intellipix.datatypes.LocationSummary;
import com.intellipix.mimehandlers.MimeHandler;
import com.intellipix.steganography.AbstractDataItem;
import com.intellipix.steganography.ByteArrayDataItem;
import com.intellipix.steganography.DataExtractor;
import com.intellipix.steganography.DataItemIds;
import com.intellipix.steganography.DataOverlayer;
import com.intellipix.steganography.LocationDataItem;
import com.intellipix.steganography.MimeTypes;
import com.intellipix.steganography.StringDataItem;
import com.intellipix.util.BufferUtils;

@SuppressWarnings("unchecked")
public class AssembleReview extends Activity {
	private static final byte NO_MIMETYPE_FOUND=0;
	private static final byte NO_PHOTO_SOURCE_FOUND = 0;
    private static final int NO_INVENTORY_FOUND = 1;
     
    private Bitmap bitmap=null;
    private Bitmap enhancedBitmap;
    private boolean isEnhancedBitmap=false;
    private ImageView view;
 
    private MediaPlayer mediaPlayer=null;
	private ArrayList <AbstractDataItem> inventoryList;
	private Map<Byte, MimeHandler> mimeTypeHandlerMap = null;
	private String bitmapPath;
 	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.assemble);
		inventoryList=new ArrayList<AbstractDataItem>();
	    bitmapPath=this.getIntent().getStringExtra(BundleKey.PHOTO_PATH);
		bitmap=BitmapFactory.decodeFile(bitmapPath);
		enhancedBitmap=bitmap;
		isEnhancedBitmap=isEnhanced(enhancedBitmap);
		if (isEnhancedBitmap) {
			inventoryList=getDataItemList(bitmap);
		}
		// If we have a camera source, put the map in the list automatically...
		byte photoSource=this.getIntent().getByteExtra(BundleKey.PHOTO_SOURCE,NO_PHOTO_SOURCE_FOUND);
        if (photoSource==IntellipixConstants.CAMERA_PHOTO_SOURCE) {
           handleAddMapMimeType( this.getIntent().getLongArrayExtra(BundleKey.LOCATION),IntellipixConstants.CAMERA_PHOTO_SOURCE);
           byte[] dateArray=this.getIntent().getByteArrayExtra(BundleKey.DATE);
           if (dateArray!=null) {
        	   handleAddDateMimeType(dateArray);
           }
        }
           
		
		ImageButton inventoryButton=(ImageButton)findViewById(R.id.inventory_folder);
		inventoryButton.setOnClickListener(inventoryListener);  
		ImageButton addButton=(ImageButton)findViewById(R.id.add_content);
		addButton.setOnClickListener(addContentListener);		
		ImageButton removeButton=(ImageButton)findViewById(R.id.remove_content);
		removeButton.setOnClickListener(removeContentListener);
		ImageButton saveButton=(ImageButton)findViewById(R.id.save);
		saveButton.setOnClickListener(saveListener);
		
		setBitmap(bitmap);
	}
 
	// This will have the effect of centering the image on the display.
	private void setBitmap(Bitmap bitmap) {
		DisplayMetrics dm = new DisplayMetrics();
		view=(ImageView) findViewById(R.id.imagePreview);
		view.setOnClickListener(photoClickListener);
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		view.setMinimumWidth(dm.widthPixels);
		view.setImageBitmap(bitmap);

	}
	
	private Map<Byte, MimeHandler> getMimeTypeHandlerMap() {
		if (mimeTypeHandlerMap == null) {
			mimeTypeHandlerMap = new HashMap<Byte, MimeHandler>();
			mimeTypeHandlerMap.put(MimeTypes.CAPTION,
					captionMimeHandler);
			mimeTypeHandlerMap.put(MimeTypes.MAP,
					mapMimeHandler);
			mimeTypeHandlerMap.put(MimeTypes.AUDIO,
					audioMimeHandler);
			mimeTypeHandlerMap.put(MimeTypes.DATE,
					dateMimeHandler);
		}
		return mimeTypeHandlerMap;
	}
	
    // Don't auto play Map and any other times that navigate away from this
	// activity.
	private boolean isActionableWithoutNavigation(Byte mimeType) {
	   return ((mimeType== MimeTypes.AUDIO) || (mimeType == MimeTypes.CAPTION));
	}
	
	private void traverseAndExecuteMimeList( List <AbstractDataItem> anInventoryList) {
		for (AbstractDataItem dataItem : anInventoryList) {
		   byte mimeType=dataItem.getMimeType();
		   if (isActionableWithoutNavigation(mimeType)) {
  		     MimeHandler handler=getMimeTypeHandlerMap().get(dataItem.getMimeType());
		     handler.handleMimeType(dataItem);
		   }		 
		}    		
	}
 
	
	MimeHandler captionMimeHandler = new MimeHandler()   {
		@Override
		public void handleMimeType(AbstractDataItem dataItem) {			
		    String caption=((StringDataItem)dataItem).getItem();
			showToast(caption);
		}			
	};
 
	MimeHandler audioMimeHandler = new MimeHandler()   {
		@Override
		public void handleMimeType(AbstractDataItem dataItem) {
			writeAudioFile((byte[])dataItem.getItem());	
 	        playAudioFile("/sdcard/test.dat");
		}			
	};
	
	MimeHandler dateMimeHandler = new MimeHandler()   {
		@Override
		public void handleMimeType(AbstractDataItem dataItem) {
			byte[]dateArray=(byte[])dataItem.getItem();	
			Date date=BufferUtils.getDateFromDateBytes(dateArray);
//			SimpleDateFormat simpleDateFormat=new SimpleDateFormat();
//			simpleDateFormat.format(date);
			showToast(date.toString());
		}			
	};
	

	MimeHandler mapMimeHandler =new MimeHandler () {
		@Override
		public void handleMimeType(AbstractDataItem dataItem) {
			 LocationSummary locationSummary=((LocationDataItem)dataItem).getItem();
			 long latitude=locationSummary.getLatitude();
			 long longitude=locationSummary.getLongitude();
			 byte photoSource=locationSummary.getPhotoSource();
			 
			 Intent intent=new Intent();
			 intent.setClass(AssembleReview.this, IntellipixMap.class);
			 Bundle bundle=new Bundle();
			 long[] latituteLongituteArray=new long[] { latitude,longitude };
			 bundle.putLongArray(BundleKey.LOCATION, latituteLongituteArray);
			 bundle.putByte(BundleKey.PHOTO_SOURCE, photoSource);
			 intent.putExtras(bundle);			   
			 AssembleReview.this.startActivity(intent);		
		}			
	};
	
	@SuppressWarnings("unchecked")
	private AbstractDataItem[] getDataItem(List <AbstractDataItem> anInventoryList) {
		AbstractDataItem[] abstractDataItemArray=new AbstractDataItem[anInventoryList.size()];
        int i=0;
		for (AbstractDataItem abstractDataItem : anInventoryList) {
        	abstractDataItemArray[i]=abstractDataItem;
        	i++;
        }
 
		return abstractDataItemArray;
	}

	private void overlayData() {
		int picw=bitmap.getWidth();
		int pich=bitmap.getHeight();
		int[] pix = new int[picw*pich];
		bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);

	 	AbstractDataItem[] abstractDataItem=getDataItem(inventoryList);
		int sizeRequired=DataOverlayer.getInstance().calculateOverLaySize(abstractDataItem);

		byte[] buffer= BufferUtils.getByteBuffer(sizeRequired);
		DataOverlayer.getInstance().overlayData(buffer,abstractDataItem);
		DataOverlayer.getInstance().overlayByteArrayOnIntArray(buffer, pix);   

		enhancedBitmap = Bitmap.createBitmap(picw, pich, Bitmap.Config.ARGB_8888);
		enhancedBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);
		pix = null;
	}
	
	
    @SuppressWarnings("unchecked")
	private OnClickListener overlayListener = new OnClickListener() {
    	public void onClick(View v) {

    	  int picw=	bitmap.getWidth();
    	  int pich=bitmap.getHeight();
          int[] pix = new int[picw*pich];
    	  bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
    	
    	  AbstractDataItem[] abstractDataItem=getDataItem(inventoryList);
    	  int sizeRequired=DataOverlayer.getInstance().calculateOverLaySize(abstractDataItem);
   
    	  byte[] buffer= BufferUtils.getByteBuffer(sizeRequired);
    	  DataOverlayer.getInstance().overlayData(buffer,abstractDataItem);
    	  DataOverlayer.getInstance().overlayByteArrayOnIntArray(buffer, pix);   

    	  enhancedBitmap = Bitmap.createBitmap(picw, pich, Bitmap.Config.ARGB_8888);
    	  enhancedBitmap.setPixels(pix, 0, picw, 0, 0, picw, pich);
    	  pix = null;
    	  view.setImageBitmap(enhancedBitmap);
    	}   	    	
    };
 
    @SuppressWarnings("unchecked")
    private OnClickListener addContentListener = new OnClickListener() {
    	public void onClick(View v) { 
    		try {    		 
    			Bundle bundle=new Bundle();    	   
    			bundle.putIntArray("inventory",getInventoryListAsIntArray()); 
    			Intent intent=new Intent();
    			intent.setClass(AssembleReview.this, AddContent.class);
    			intent.putExtras(bundle);
    			AssembleReview.this.startActivityForResult(intent, IntellipixConstants.INVENTORY_ADD);

    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}   		    	
    };
    
    @SuppressWarnings("unchecked")
    private OnClickListener removeContentListener = new OnClickListener() {
    	public void onClick(View v) { 
    		try {
    			if (inventoryList.size()==0) {
    				showDialog(NO_INVENTORY_FOUND);
    			} else {
    				Bundle bundle=new Bundle();    	   
    				bundle.putIntArray("inventory",getInventoryListAsIntArray()); 
    				Intent intent=new Intent();
    				intent.setClass(AssembleReview.this, RemoveContent.class);
    				intent.putExtras(bundle);
    				AssembleReview.this.startActivityForResult(intent, IntellipixConstants.INVENTORY_REMOVE);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}   	
    };
 
    private static boolean isEnhanced(Bitmap bitmap) {
        int picw=IntellipixConstants.ENCHANCED_QUERY_SIZE_BYTES;
    	int pich=1;
        int[] pix = new int[picw*pich];
        bitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
        boolean enhanced=BufferUtils.isBitmapEnhanced(pix);
        pix=null;
        return enhanced;
    }
    
    @SuppressWarnings("unchecked")
	private ArrayList <AbstractDataItem> getDataItemList(Bitmap bitmap) {
    	int picw=	enhancedBitmap.getWidth();
    	int pich=enhancedBitmap.getHeight();
    	int[] pix = new int[picw*pich];
    	enhancedBitmap.getPixels(pix, 0, picw, 0, 0, picw, pich);
    	ArrayList <AbstractDataItem> dataItemList=new ArrayList<AbstractDataItem>();
    	boolean isEnhanced=BufferUtils.isBitmapEnhanced(pix);
    	if (isEnhanced) {
    		long dataSize=BufferUtils.getDataSize(pix);
    		DataExtractor extractor=new DataExtractor();
    		byte[]results=extractor.extractByteArrayFromIntArray(pix, dataSize);
    		dataItemList=extractor.getDataItem(results,0);
    	}
    	
    	return dataItemList;
    }
    
    private OnClickListener photoClickListener = new OnClickListener() {
    	@SuppressWarnings("unchecked")
		public void onClick(View v) { 
    		try {
    			if (isEnhancedBitmap) {
    			   traverseAndExecuteMimeList(inventoryList);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}   	
    };
    
    private OnClickListener inventoryListener = new OnClickListener() {
    	@SuppressWarnings("unchecked")
    	public void onClick(View v) { 
    		try {
    			if (inventoryList.size()==0) {
    				 showDialog(NO_INVENTORY_FOUND);
    			} else {
    				Bundle bundle=new Bundle();    	   
    				bundle.putIntArray("inventory",getInventoryListAsIntArray()); 
    				Intent intent=new Intent();
    				intent.setClass(AssembleReview.this, InventorySelect.class);
    				intent.putExtras(bundle);
    				AssembleReview.this.startActivityForResult(intent, IntellipixConstants.INVENTORY_SELECT);
    			}
    		} catch (Exception e) {
    			e.printStackTrace();
    		}
    	}   	
    };
    
    private OnClickListener saveListener = new OnClickListener() {
    	@SuppressWarnings("unchecked")
		public void onClick(View v) {   	
    		if (enhancedBitmap!=null) {
    	  	  String pathWithoutSDCard=bitmapPath.substring("/sdcard/".length());	
    		  Bundle bundle=new Bundle();    	   
			  bundle.putString(BundleKey.FILENAME,pathWithoutSDCard); 
			  Intent intent=new Intent();
			  intent.setClass(AssembleReview.this, SaveFileEntry.class);
			  intent.putExtras(bundle);
			  AssembleReview.this.startActivityForResult(intent, IntellipixConstants.SAVE_FILE);	
    		}
    	}   	
    };
    
    private void handleInventorySelect(int selectedPosition) {
      AbstractDataItem dataItem=inventoryList.get(selectedPosition);
   	  MimeHandler handler=getMimeTypeHandlerMap().get(dataItem.getMimeType());
	  handler.handleMimeType(dataItem); 
    }
    
    private void handleInventoryRemove(int selectedPosition) {
       if ( selectedPosition < inventoryList.size()) {
    	   inventoryList.remove(selectedPosition);
       }
    }
    
    private void handleAddDateMimeType(byte[] dateByteArray) {
    	ByteArrayDataItem byteArrayDataItem=new ByteArrayDataItem();
		byteArrayDataItem.setMimeType(MimeTypes.DATE);
		byteArrayDataItem.setId(DataItemIds.DATA_BUFFER_ID);
		byteArrayDataItem.setItem(dateByteArray);
		inventoryList.add(byteArrayDataItem);     	
    }
    
    private void handleAddMapMimeType(long[] latlongValues,byte photoSource) {
    	if (latlongValues!=null) {         
		  LocationDataItem locationDataItem=new LocationDataItem();
		  locationDataItem.setId(DataItemIds.DATA_BUFFER_ID);
		  locationDataItem.setItem(new LocationSummary(latlongValues[0],latlongValues[1],photoSource));
          inventoryList.add(locationDataItem);   
    	}
    }
    
    private void handleAddCaptionMimeType(String caption) {
		StringDataItem captionDataItem=new StringDataItem();
		captionDataItem.setMimeType(MimeTypes.CAPTION);
		captionDataItem.setId(DataItemIds.DATA_BUFFER_ID);
		captionDataItem.setItem(caption);
		inventoryList.add(captionDataItem);    	
    }
        
    private void handleAddAudioMimeType(Uri uri) {
    	ByteArrayDataItem byteArrayDataItem=new ByteArrayDataItem();
		byteArrayDataItem.setMimeType(MimeTypes.AUDIO);
		byteArrayDataItem.setId(DataItemIds.DATA_BUFFER_ID);
		byteArrayDataItem.setItem((byte[])createAudioFile(uri));
		inventoryList.add(byteArrayDataItem); 
		// remove the db entry
		removeAudioUri(uri);
    }
    
    private void handleSaveFile(String filename) {
       overlayData();
       writeEnhancedBitmap(filename,enhancedBitmap);
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode,
		Intent data) {
    	if (resultCode==RESULT_OK) {
    	   if (requestCode== IntellipixConstants.SAVE_FILE) {
    		   handleSaveFile(data.getStringExtra(BundleKey.FILENAME));
    	   }
    	   else if (requestCode==IntellipixConstants.INVENTORY_SELECT) {
    		   handleInventorySelect(Integer.parseInt(data.getAction()));
    	   } else if (requestCode==IntellipixConstants.INVENTORY_REMOVE) {
    		   handleInventoryRemove(Integer.parseInt(data.getAction()));
    	   // TODO : can't create a general purpose handler and pass the bundle
    	   // because we can't access the bundle directly ...look into a 
    	   // better way to do this.
      	   } else if (requestCode==IntellipixConstants.INVENTORY_ADD) {
      		   byte mimeType=data.getByteExtra(BundleKey.MIMETYPE,NO_MIMETYPE_FOUND);
      		   if (mimeType!=NO_MIMETYPE_FOUND) {
      			    switch (mimeType) {
      			    case MimeTypes.MAP:
      			    	// may not have been stored but yet but content has been added
      			    	isEnhancedBitmap=true; 
      			    	handleAddMapMimeType( data.getLongArrayExtra(BundleKey.LOCATION),IntellipixConstants.DISK_PHOTO_SOURCE);
      			    	break;
      			    case MimeTypes.CAPTION:
      			    	handleAddCaptionMimeType( data.getStringExtra(BundleKey.CAPTION));
        			    // may not have been stored but yet but content has been added
      			    	isEnhancedBitmap=true; 
      			    	break;
      			    case MimeTypes.AUDIO:
      		        	handleAddAudioMimeType(data.getData());
      		        	isEnhancedBitmap=true; 
      			    	break;
      			    case MimeTypes.DATE:
      		        	 // TODO - Handle
      		        	isEnhancedBitmap=true; 
      			    	break;
      			    }
      		   }
  	       }    	
    	}
    }
    
    private int[] getInventoryListAsIntArray() {
    	int[] inventoryStringArray=new int[inventoryList.size()];
    	int index=0;
    	for (AbstractDataItem item : inventoryList) {
    		inventoryStringArray[index]=item.getMimeType();
    		index++;
    	}    	 
    	return inventoryStringArray;
    }

    private byte[] createAudioFile(Uri uri) {        
    	ByteArrayOutputStream out=null;
    	try {      
    		 
    		InputStream inputStream=getContentResolver().openInputStream(uri);
    	    out=new ByteArrayOutputStream();
    		byte buf[]=new byte[1024];
    		int len;
    		while((len=inputStream.read(buf))>0)
    			out.write(buf,0,len);
    		out.close();
    		inputStream.close();
    	}

    	catch (IOException e){
    		e.printStackTrace();
    	}    	
    	return out.toByteArray();
    }    
    
    // remove the db entry
    private void removeAudioUri(Uri uri) {
    	getContentResolver().delete(uri, null, null);
    }
    
//    private byte[] createAudioFile() {        
//    	ByteArrayOutputStream out=null;
//    	try {        		         		    	 
//    		InputStream inputStream= getResources().openRawResource(R.raw.dean1);
//    	    out=new ByteArrayOutputStream();
//    		byte buf[]=new byte[1024];
//    		int len;
//    		while((len=inputStream.read(buf))>0)
//    			out.write(buf,0,len);
//    		out.close();
//    		inputStream.close();
//    	}
//
//    	catch (IOException e){
//    		e.printStackTrace();
//    	}    	
//    	return out.toByteArray();
//    }    
    
    
    private void writeEnhancedBitmap(String filename,Bitmap bitmap) {     
    	File bufferedFile=null;     
      	bufferedFile=new File("/sdcard",filename);
    	OutputStream fos;
		try {
			fos = new FileOutputStream(bufferedFile);
			boolean value=bitmap.compress(Bitmap.CompressFormat.PNG,100,fos);
	    	fos.flush();
	    	fos.close();  	
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    	 		
    }    
       
    private File writeAudioFile(byte[] byteArray) {        
    	File bufferedFile=null;
    	
    	try {        		         		
    		bufferedFile=new File("/sdcard","test.dat");
    		OutputStream out=new FileOutputStream(bufferedFile);    	 
     	    out.write(byteArray);
    		out.close();    		 
    	}

    	catch (IOException e){
    		e.printStackTrace();
    	}
    	
    	return bufferedFile;
    }    
    
    private View inflateView(int resource) {
        LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        return vi.inflate(resource, null);
    }
    /**
     * The toast pops up a quick message to the user showing what could be
     * the text of an incoming message.  It uses a custom view to do so.
     */
    protected void showToast(String text) {
        // create the view
        View view = inflateView(R.layout.caption_panel);

        // set the text in the view
        TextView tv = (TextView)view.findViewById(R.id.message);
        tv.setText(text);

        // show the toast
        Toast toast = new Toast(this);
        toast.setView(view);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.show();
    }
    
    private void playAudioFile(String filename) {
    	try {
    		if (mediaPlayer==null) {
    			mediaPlayer = new MediaPlayer();
    			mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
    				@Override
    				public void onCompletion(MediaPlayer arg0) {
    					mediaPlayer.reset();				
    				}
    			});
    		}
    		if (!mediaPlayer.isPlaying()) {
    			mediaPlayer.setDataSource(filename);
    			mediaPlayer.prepare(); 
    			mediaPlayer.start();
    		}
    	} catch (IllegalArgumentException e) {					 
    		e.printStackTrace();
    	} catch (IllegalStateException e) {					 
    		e.printStackTrace();
    	} catch (IOException e) {			 
    		e.printStackTrace();
    	}
    }
    
    @Override
	 protected Dialog onCreateDialog(int id) {
	        switch (id) {
	        case NO_INVENTORY_FOUND:
	            return new AlertDialog.Builder(AssembleReview.this)
	                .setIcon(R.drawable.alert_dialog_icon)
	                .setTitle(R.string.no_brain)
	                .setPositiveButton(R.string.alert_dialog_ok, new DialogInterface.OnClickListener() {
	                    public void onClick(DialogInterface dialog, int whichButton) {
                            // nothing needed here - window will just close
	                    }
	                }).create();
	        }
			return null;
	 }
    
}
