package com.intellipix.datatypes;


public class LocationSummary  {

    private long latitude;
    private long longitude;
    private byte photoSource;
    
    public LocationSummary(long latitude, long longitude,byte photoSource) {
    	setLatitude(latitude);
    	setLongitude(longitude);    
    	setPhotoSource(photoSource);
    }
    
	public long getLatitude() {
		return latitude;
	}
	
	public void setLatitude(long latitude) {
		this.latitude = latitude;
	}
	
	public long getLongitude() {
		return longitude;
	}
 
	public void setLongitude(long longitude) {
		this.longitude = longitude;
	}
 
	public byte[] getBytes() {
      StringBuffer strBuffer=new StringBuffer();  
	  strBuffer.append(String.format("%08X", getLatitude()));
  	  strBuffer.append(String.format("%08X", getLongitude()));
  	  strBuffer.append(photoSource);
  	 return strBuffer.toString().getBytes();
	}

	public byte getPhotoSource() {
		return photoSource;
	}

	public void setPhotoSource(byte photoSource) {
		this.photoSource = photoSource;
	}
	
}
