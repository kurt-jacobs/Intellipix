package com.intellipix.steganography;

import java.util.List;
import java.util.Random;

import com.intellipix.util.PrintUtil;


public class TestHarness {
	 
      
    // create a buffer that with all bits on...
	private static byte[] getByteBuffer(int bufferSize) {
		byte[] byteBuffer=new byte[bufferSize];
		for (int i=0; i < byteBuffer.length; i++) {
			byteBuffer[i]=(byte)0x00;

		}
		return byteBuffer;
	}

    // create a buffer that with all bits on...
	private static int[] getIntBuffer(int bufferSize) {
		Random rand=new Random(System.currentTimeMillis());
		int[] intBuffer=new int[bufferSize];
		for (int i=0; i < intBuffer.length; i++) {
			intBuffer[i]=0;//rand.nextInt();
		}
		return intBuffer;
	}
 
	
	// This string in this data is the actual data hence is gets
	// the DATA_BUFFER_ID
	@SuppressWarnings("unchecked")
	private static AbstractDataItem[] getDataItem() {
		AbstractDataItem[] abstractDataItemArray=new AbstractDataItem[2];
		
		StringDataItem stringDataItem=new StringDataItem();
		stringDataItem.setId(DataItemIds.FILENAME_ID);
		stringDataItem.setItem("a");
 
		StringDataItem stringDataItem2=new StringDataItem();
		stringDataItem2.setId(DataItemIds.DATA_BUFFER_ID);
		stringDataItem2.setItem("Mr. Shnayderman--come here--I want to see you");
		
		abstractDataItemArray[0]=stringDataItem;
		abstractDataItemArray[1]=stringDataItem2;
		return abstractDataItemArray;
	}
	
    private static String translateIdToString(byte id) {
    	if (id==DataItemIds.FILENAME_ID) 
    		return "File name";
    	else 
    		return "Data Buffer";
    }
	
 
    
	@SuppressWarnings("unchecked")
	public static void main(String args[]) {
				
		// After size issues/boundary checks implements, try overlaying real data.		
		int sizeRequired=DataOverlayer.getInstance().calculateOverLaySize(getDataItem());
		System.out.println("SizeReq=" + sizeRequired);
		byte[] buffer= getByteBuffer(sizeRequired);
		DataOverlayer.getInstance().overlayData(buffer,getDataItem());
    	//PrintUtil.printByteBuffer("data", buffer);
	 	int[] targetBuffer=getIntBuffer(255);
	 	DataOverlayer.getInstance().overlayByteArrayOnIntArray(buffer, targetBuffer);
	 	//PrintUtil.printIntBuffer(targetBuffer);
	 	
 		DataExtractor extractor=new DataExtractor();
 		byte[]results=extractor.extractByteArrayFromIntArray(targetBuffer, sizeRequired);
 		//PrintUtil.printByteBuffer("Extracted " + results.length,results);
 		
		List <AbstractDataItem> dataItemList=extractor.getDataItem(results,0);
		for (AbstractDataItem dataItem:dataItemList) {
			System.out.println("Data Item (" + translateIdToString(dataItem.getId()) + ") =" + dataItem.getItem());
		}
		
	} 
	
}
