package com.intellipix.steganography;

import java.util.ArrayList;

public class DataExtractor {
 
	
	public byte[] extractByteArrayFromIntArray(int[] source, long bytesToExtract) {   

		ByteAccumulator byteAccumulator=new ByteAccumulator();
        boolean done=false;
        int i=0; int sftAmt=16;
        while (!done) {
        	byteAccumulator.putByte((byte)(source[i]>>sftAmt));  
        	done= (bytesToExtract==(byteAccumulator.getOutputLength())); 
        	sftAmt-=8;
        	if (sftAmt<0) {
        		i++;
        		sftAmt=16;
        	}   
        }

		return byteAccumulator.getByteArray();
	}    
 
	public byte[] getDataBuffer(byte[] sourceBuffer,int startIndex,long length) {
		byte byteArray[]=new byte[(int) length];
		for (int i=0; i < length; i++) {
			byteArray[i]=sourceBuffer[startIndex+i];
		}
		return byteArray;
		
	}
	
	public long getDataItemSize(byte[] sourceBuffer,int startIndex) {
		long size=0;
		byte[] sizeBuffer=getDataBuffer(sourceBuffer,startIndex,BufferConstants.DATA_SIZE);
		size=Long.parseLong(new String(sizeBuffer), 16);
		return size;
	}
	    
	private byte getSingleByteValue(byte[] sourceBuffer,int startIndex) {		 
		byte[] dataId=getDataBuffer(sourceBuffer,startIndex,BufferConstants.DATA_ID);	
		return dataId[0];
	}
	
	@SuppressWarnings("unchecked")
	public ArrayList <AbstractDataItem> getDataItem(byte[] sourceBuffer,int startIndex) {
		byte applicationId[]=getDataBuffer(sourceBuffer,startIndex,BufferConstants.APP_TAG);
		ArrayList <AbstractDataItem> dataItemList=new ArrayList<AbstractDataItem> ();

		if (new String(applicationId).equals(BufferConstants.APPLICATION_TAG)) {
			startIndex+=(BufferConstants.APP_TAG); 
			long bundleSize=getDataItemSize(sourceBuffer,startIndex);
			startIndex+=(BufferConstants.DATA_SIZE);
			byte numberElements=getSingleByteValue(sourceBuffer,startIndex);
			startIndex+=(BufferConstants.NUM_ELEMENTS); 

			for (int i=0; i < numberElements; i++) {
				// get Id
				byte id=getSingleByteValue(sourceBuffer,startIndex);
				startIndex+=(BufferConstants.DATA_ID);
				// get mime type
				byte mimeType=getSingleByteValue(sourceBuffer,startIndex);
				startIndex+=(BufferConstants.MIME_TYPE);
				// get size
				long itemSize=getDataItemSize(sourceBuffer,startIndex);
				startIndex+=(BufferConstants.DATA_SIZE);
				// get data
				AbstractDataItem dataItem =DataItemFactory.getDataItem(mimeType);
				dataItem.convertToItem(getDataBuffer(sourceBuffer,startIndex,itemSize));
				dataItem.setId(id);
				dataItem.setMimeType(mimeType);
				dataItemList.add(dataItem);
				startIndex+=itemSize;
			}

		}
		return dataItemList;
	}


	
}
