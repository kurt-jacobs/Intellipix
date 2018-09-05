package com.intellipix.steganography;

public abstract class AbstractDataItem <T> {
	private byte id;
	protected T item;
	protected byte[] rawData;
    protected byte mimeType;
  	 	
	public void setId(byte id) {
      this.id=id;
       
	}
	
	public byte getId() {		 
   	  return id;
	}
 
	public void setItem(T item) {
		this.item=item;
	}
 
	
	public long getLength() {		 
		return getItemInBytes().length;
	}
	
	public byte getMimeType() {
		return mimeType;
	}

	public void setMimeType(byte mimeType) {
		this.mimeType = mimeType;
	}
	
	public abstract byte[] getItemInBytes();
	public abstract void convertToItem(byte[] rawData);	 
	public abstract T getItem();
}
