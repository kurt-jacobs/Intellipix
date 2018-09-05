package com.intellipix.steganography;

public class StringDataOverlayAdapter extends DataOverlayAdapter <String>{

	@Override
	public int overlayData(String sourceData, byte[] buffer, int index) {
		byte[] byteArrayToOverlay=sourceData.trim().getBytes();
		
		index=overlayData(byteArrayToOverlay,buffer,index);
		return index;
	}

	 

}
