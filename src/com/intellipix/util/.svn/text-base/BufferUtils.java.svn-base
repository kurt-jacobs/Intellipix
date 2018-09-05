package com.intellipix.util;

import java.util.Calendar;
import java.util.Date;

import com.intellipix.steganography.BufferConstants;
import com.intellipix.steganography.DataExtractor;

public class BufferUtils {

	private static int getHeaderBytesRequired() {
	  int intsToRead=3;
	   return intsToRead;       
	}
  
	public static byte[] getTodaysDateAsBytes() {
		byte[] calendarBytes=new byte[5];
		// most significant to least ...
		calendarBytes[0]=(byte)Calendar.getInstance().get(Calendar.YEAR);
		calendarBytes[1]=(byte)Calendar.getInstance().get(Calendar.MONTH);
		calendarBytes[2]=(byte)Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
		calendarBytes[3]=(byte)Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		calendarBytes[4]=(byte)Calendar.getInstance().get(Calendar.MINUTE);
		
		return calendarBytes;		
	}
	
	public static Date getDateFromDateBytes(byte[] dateBytes) {
		Calendar myCalendar=Calendar.getInstance();
		myCalendar.set(Calendar.YEAR, dateBytes[0]);
		myCalendar.set(Calendar.MONTH, dateBytes[1]);
		myCalendar.set(Calendar.DAY_OF_MONTH, dateBytes[2]);
		myCalendar.set(Calendar.HOUR_OF_DAY, dateBytes[3]);
		myCalendar.set(Calendar.MINUTE, dateBytes[4]);
		
	    return myCalendar.getTime();
	}
	
	public static boolean isBitmapEnhanced(int[] bitmap) {
        boolean enhanced=false;
        int intsToRead=getHeaderBytesRequired();
        if (bitmap.length>intsToRead) {        	 
        	DataExtractor dataExtractor=new DataExtractor();
        	byte applicationId[]=dataExtractor.extractByteArrayFromIntArray(bitmap,BufferConstants.APP_TAG);
        	enhanced=(new String(applicationId).equals(BufferConstants.APPLICATION_TAG));
         }
     
		return enhanced;		
	}
	
	public static long getDataSize(int[] bitmap) {
		long dataSize=-1;

		DataExtractor dataExtractor=new DataExtractor();
		byte byteArray[]=dataExtractor.extractByteArrayFromIntArray(bitmap,
				BufferConstants.APP_TAG+BufferConstants.DATA_SIZE);
		 
		dataSize=dataExtractor.getDataItemSize(byteArray,BufferConstants.APP_TAG);
		return dataSize;		
	}
	
	 // create a buffer that with all bits on...
	public static byte[] getByteBuffer(int bufferSize) {
		byte[] byteBuffer=new byte[bufferSize];
		for (int i=0; i < byteBuffer.length; i++) {
			byteBuffer[i]=(byte)0x0;

		}
		return byteBuffer;
	}
}
