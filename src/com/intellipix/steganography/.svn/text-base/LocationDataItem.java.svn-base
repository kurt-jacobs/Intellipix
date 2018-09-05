package com.intellipix.steganography;

import com.intellipix.datatypes.LocationSummary;
import com.intellipix.util.PrintUtil;


public class LocationDataItem extends AbstractDataItem <LocationSummary> {

	public LocationDataItem() {
		setMimeType(MimeTypes.MAP);
	}
	
	
	@Override
	public void convertToItem(byte[] rawData) {
		byte[] latBytes=new byte[8];
		System.arraycopy(rawData, 0, latBytes, 0, 8);
		byte[] longBytes=new byte[8];
		System.arraycopy(rawData, 8, longBytes, 0, 8);
 
		try {
  		  long latitude=Long.parseLong(new String(latBytes), 16);
  	      long longitute=Long.parseLong(new String(longBytes), 16);
		  item=new LocationSummary(latitude,longitute,rawData[rawData.length-1]);	 
		}
        catch (Exception e) {
        	PrintUtil.printString("Exceptin in LocationDataItem.convertToItem");
        	item=new LocationSummary(0,0,(byte)0);		
        }
		
	}

	@Override
	public LocationSummary getItem() {		 
		return item;
	}

	@Override
	public byte[] getItemInBytes() {
		 
		return item.getBytes();
	}

}
