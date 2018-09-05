package com.intellipix.steganography;

import com.intellipix.datatypes.LocationSummary;

public class LocationDataOverlayAdapter extends DataOverlayAdapter <LocationSummary>{
 
	@Override
	public int overlayData(LocationSummary locationSummary, byte[] buffer, int index) {		
	  	byte[] byteArrayToOverlay=locationSummary.getBytes();
		index=overlayData(byteArrayToOverlay,buffer,index);
		return index;
	}
  
}
