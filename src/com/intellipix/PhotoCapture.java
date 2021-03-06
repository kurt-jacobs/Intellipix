package com.intellipix;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.Window;

import com.intellipix.util.BufferUtils;
import com.intellipix.util.PrintUtil;
import com.intellipix.util.location.LocationManagerHelper;

public class PhotoCapture extends Activity {
    private Preview preview;
    private LocationManagerHelper locationHelper;
    private static final int LATITUDE_INDEX=0;
    private static final int LONGITUDE_INDEX=1;
    @Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        // Hide the window title.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    
        // Create our Preview view and set it as the content of our activity.
        preview = new Preview(this);
        setContentView(
        		preview, 
        		new ViewGroup.LayoutParams(
        				ViewGroup.LayoutParams.FILL_PARENT, 
        				ViewGroup.LayoutParams.FILL_PARENT));
        
        locationHelper = new LocationManagerHelper(this);        
    }
    
    // TODO : stub -replace with real impl
    private long[] getLocationData() {
    	long[] locationArray  = locationHelper.getLastKnownLocationAsLongArray();    	
//        long[] locationArray=new long[2];
//        locationArray[LATITUDE_INDEX]=32958523;
//        locationArray[LONGITUDE_INDEX]=-96730077;
        return locationArray;
     }
    
    // Allow the user to cancel with the back button but don't allow the user to 
    // hit arbitrary keys (like a monkey)
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {				
		if (keyCode==KeyEvent.KEYCODE_BACK) {    
			setResult(this.RESULT_CANCELED, (new Intent()).setAction(Integer.toString(-1)));
			finish(); 
			return true;
		} else { 
			boolean isCaptureKey = keyCode == KeyEvent.KEYCODE_CAMERA || keyCode == KeyEvent.KEYCODE_DPAD_CENTER;
			if (!isCaptureKey)  {
				return true;
			}
		}
		
		preview.takePicture(new PictureCallback() {
			@Override
			public void onPictureTaken(byte[] jpegData, Camera camera) {
				if (jpegData == null) {
					return;
				}				
				BitmapFactory.Options decodeOptions = new BitmapFactory.Options();
				decodeOptions.inPreferredConfig = Bitmap.Config.ARGB_8888;
				final Bitmap bitmap = BitmapFactory.decodeStream(new ByteArrayInputStream(jpegData), null, decodeOptions);
				
				File tmpFile = null;
				try {
					tmpFile = File.createTempFile("ipix", ".png", new File("/sdcard"));
				} catch (IOException e) {			
					e.printStackTrace();
				}
				
				FileOutputStream fos = null; 
				BufferedOutputStream bos = null;
				try {
					fos= new FileOutputStream(tmpFile);
					bos = new BufferedOutputStream(fos);
					bitmap.compress(CompressFormat.PNG, 100, bos);
					bos.flush();
				} catch (Throwable thrown) {
					if (bos != null) {
						try { bos.close(); } catch (Throwable thrown1) {}
					}
					if (fos != null) {
						try { fos.close(); } catch (Throwable thrown2) {}
					}
				}
						
		      	Intent intent=new Intent();
				intent.setClass(PhotoCapture.this, AssembleReview.class);
				Bundle bundle=new Bundle();
			    bundle.putString(BundleKey.PHOTO_PATH, tmpFile.getAbsolutePath());
			    bundle.putByte(BundleKey.PHOTO_SOURCE, IntellipixConstants.CAMERA_PHOTO_SOURCE);
			    bundle.putByteArray(BundleKey.DATE, BufferUtils.getTodaysDateAsBytes());
			    bundle.putLongArray(BundleKey.LOCATION,  getLocationData());
		        intent.putExtras(bundle);
				startActivity(intent);
			}			
		});	
		
		return true;
	}

}

// ----------------------------------------------------------------------

class Preview extends SurfaceView implements SurfaceHolder.Callback {
    SurfaceHolder mHolder;
    Camera mCamera;
    
    Preview(Context context) {
        super(context);
        
        // Install a SurfaceHolder.Callback so we get notified when the
        // underlying surface is created and destroyed.
        mHolder = getHolder();
        mHolder.addCallback(this);
        mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

	public void surfaceCreated(SurfaceHolder holder) {
        // The Surface has been created, acquire the camera and tell it where
        // to draw.
        mCamera = Camera.open();
        mCamera.getParameters().setPictureFormat(PixelFormat.JPEG);
        mCamera.setPreviewDisplay(holder);
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        // Surface will be destroyed when we return, so stop the preview.
        // Because the CameraDevice object is not a shared resource, it's very
        // important to release it when the activity is paused.
        mCamera.stopPreview();  
        mCamera.release();
        mCamera = null;
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
        // Now that the size is known, set up the camera parameters and begin
        // the preview.
        Camera.Parameters parameters = mCamera.getParameters();
        parameters.setPreviewSize(w, h);
        mCamera.setParameters(parameters);
        mCamera.startPreview();
    }
    
    public void takePicture(PictureCallback jpeg) {
    	if (mCamera != null) {
    		mCamera.takePicture(null, null, jpeg);
    	}
    }
    
   

}