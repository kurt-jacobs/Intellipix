package com.intellipix.steganography;

public class StringDataItem extends AbstractDataItem <String> {

	public StringDataItem() {
	
	}
	
	@Override
	public byte[] getItemInBytes() {		 
   	  return item.getBytes();
	}

	@Override
	public String getItem() {		 
		return item;
	}

	@Override
	public void convertToItem(byte[] rawData) {
		item=new String(rawData);		
	}
	 
	 
}
