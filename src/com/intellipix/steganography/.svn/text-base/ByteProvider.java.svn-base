package com.intellipix.steganography;

import com.intellipix.util.PrintUtil;

public class ByteProvider {
    private  byte[] byteArray;
	private int byteIndex;
	private int maxSize=0;
	private int shiftAmt=6;
	
	public ByteProvider(byte[] byteArrayParm) {
		byteArray=byteArrayParm;
		maxSize=byteArray.length;
	}
	
	public byte getNextByte() {
		byte nextByte=0;		
		if (byteIndex<maxSize) {
		  nextByte=(byte)((byteArray[byteIndex]>>shiftAmt) & 0x03);
		    shiftAmt-=2;
		  if (shiftAmt<0) {
		    shiftAmt=6;
		    byteIndex++;
		  }
		}	
		return nextByte;
	}
	
	public static void main(String args[]) { 
		String testString="abcdef";
		byte[] test=testString.getBytes();
	 	
		ByteProvider byteProvider=new ByteProvider(test);
		for (int i=0; i < 4*test.length; i++) {
			byte aByte=byteProvider.getNextByte();
			PrintUtil.printByteBuffer("Test " + i + ")",aByte);
		}	
		
		
	}
	
	
}
