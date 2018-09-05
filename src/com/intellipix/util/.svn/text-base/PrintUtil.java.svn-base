package com.intellipix.util;

import java.util.List;

import com.intellipix.steganography.AbstractDataItem;

import android.util.Log;

public class PrintUtil {

    private static final String TAG = "PrintUtil";
	   
    public static void printString(String stringValue) {
    	  Log.v(TAG, stringValue);
    }
    
	private static String breakStringIntoQuad(String binString) {
		StringBuffer strBuffer = new StringBuffer(40);
		strBuffer.append(binString.substring(0, 8));
		strBuffer.append(" ");
		strBuffer.append(binString.substring(8, 16));
		strBuffer.append(" ");
		strBuffer.append(binString.substring(16, 24));
		strBuffer.append(" ");
		strBuffer.append(binString.substring(24));

		return strBuffer.toString();
	}

	private static String zeroFilledBinString(int value, int valueLength) {
		String binString = Integer.toBinaryString(value);
		for (int deficit = valueLength - binString.length(); deficit > 0; --deficit) {
			binString = "0" + binString;
		}
		return binString;
	}
	
	private static String zeroFilledDecString(int value,int valueLength) {
		String decString=Integer.toString(value);
		for (int deficit=valueLength-decString.length(); deficit>0; --deficit) {
			decString="0"+decString;
		}
		
		return decString;
	}
	
	public void printList(List <AbstractDataItem>  dataItemList) {
		for (AbstractDataItem dataItem:dataItemList) {
	  	  Log.v(TAG, "*Data Item (" + dataItem.getId() + ") =" + dataItem.getLength());    		 
	    }	
	}
	
	public static void printIntBuffer(int ...targetBuffer) {
		for (int i = 0; i < targetBuffer.length; i++) {
			String binString = zeroFilledBinString(targetBuffer[i], 32);
			Log.v(TAG,"Int Buffer[" + zeroFilledDecString(i, 3)
					+ "] = " + breakStringIntoQuad(binString));

		}
	}
	
	public static void printIntBuffer(int numberToPrint,int ...targetBuffer) {
		for (int i = 0; i < numberToPrint ; i++) {
			String binString = zeroFilledBinString(targetBuffer[i], 32);
			Log.v(TAG,"Int Buffer[" + zeroFilledDecString(i, 3)
					+ "] = " + breakStringIntoQuad(binString));

		}
	}
	
	public static void printIntBuffer(String label,int ...targetBuffer) {
		for (int i = 0; i < targetBuffer.length; i++) {
			String binString = zeroFilledBinString(targetBuffer[i], 32);
			Log.v(TAG,label + ">" + zeroFilledDecString(i, 3)
					+ " = " + breakStringIntoQuad(binString));

		}
	}
	public static void printByteBuffer(String label,byte ...buffer) {
		Log.v(TAG,"\nBuffer " + label);
		for (int i=0; i < buffer.length; i++) {	
			byte value=(byte) buffer[i];
			Log.v(TAG,"Buffer[" + zeroFilledDecString(i,3) + "] =" + zeroFilledBinString(value,8));		 			
		}		
	}

}
