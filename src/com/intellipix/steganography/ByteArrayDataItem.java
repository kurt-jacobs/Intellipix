package com.intellipix.steganography;

public class ByteArrayDataItem extends AbstractDataItem<byte[]> {

	@Override
	public void convertToItem(byte[] rawData) {
		item=rawData;		
	}

	@Override
	public byte[] getItem() {	 
		return getItemInBytes();
	}

	@Override
	public byte[] getItemInBytes() {
	   return item;
	}

}
