package com.intellipix;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
 
 
public class IntellipixMap extends MapActivity {

    private MapView map;
    private MapController mc;
    private GeoPoint mDefPoint;
    private View zoomView;
  
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    	long[] locationArray=this.getIntent().getLongArrayExtra(BundleKey.LOCATION);
    	byte photoSource=this.getIntent().getByteExtra(BundleKey.PHOTO_SOURCE,(byte) 0);
        setContentView(R.layout.map_view);
        map = (MapView) findViewById(R.id.map);
        zoomView = map.getZoomControls();
        zoomView.setLayoutParams(new ViewGroup.LayoutParams
        (ViewGroup.LayoutParams.WRAP_CONTENT,
        ViewGroup.LayoutParams.WRAP_CONTENT)); 
        map.addView(zoomView);
       
        int latitute= (int) (locationArray[0]);
        int longitute=(int) (locationArray[1]);
        mDefPoint = new GeoPoint(latitute,longitute);
        
        Drawable marker=getPushPin(photoSource); 
        marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());  
        map.getOverlays().add(new SitesOverlay(marker));          
        map.displayZoomControls(true);  
     
        mc = map.getController();      
        mc.setZoom(12);
    	mc.setCenter(mDefPoint);  
   }
    
    private Drawable getPushPin(byte photoSource) {
    	return (photoSource==IntellipixConstants.CAMERA_PHOTO_SOURCE) ?
    	    getResources().getDrawable(R.drawable.camera_graphic) :
    	    getResources().getDrawable(R.drawable.red_pushpin) ;
    }
       
    private class SitesOverlay extends ItemizedOverlay {  
    	private List <OverlayItem>items=new ArrayList<OverlayItem>();  
    	private Drawable marker=null;  

    	public SitesOverlay(Drawable marker) {  
    		super(marker);  
    		this.marker=marker;  

    		items.add(new OverlayItem(mDefPoint,  
    				"My Location", "Picture Taken Here"));  
    		populate();  
    	}  

    	@Override  
    	protected OverlayItem createItem(int i) {  
    		return(items.get(i));  
    	}  

    	@Override  
    	public void draw(Canvas canvas, MapView mapView, boolean shadow) {  
    		super.draw(canvas, mapView, shadow);  

    		boundCenterBottom(marker);  
    	}  

    	@Override  
    	protected boolean onTap(int i) {  
    		Toast.makeText(IntellipixMap.this, items.get(i).getSnippet(), Toast.LENGTH_SHORT).show();  
    		return(true);  
    	}  

    	@Override  
    	public int size() {  
    		return(items.size());  
    	}  
    }  
    
    
   @Override
     protected boolean isRouteDisplayed() {
         return false;
      }
}
