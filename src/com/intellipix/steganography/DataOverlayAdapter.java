package com.intellipix.steganography;

public class DataOverlayAdapter <T> {
    
  
    public static int overlayData(byte source,byte[] target,int index) {
       return overlayData(new byte[] { source } , target, index); 
    }
    
    public static int overlayData(byte[] source,byte[] target,int targetIndex) {
     
    	for (byte sourceByte : source) {
    		target[targetIndex]=sourceByte;
    		++targetIndex;
 		
    	}
    	return targetIndex;
    	
    }
    
	public int overlayData(T sourceData,byte[] buffer,int index) {
		return overlayData((byte[])sourceData,buffer,index);
	}
}
