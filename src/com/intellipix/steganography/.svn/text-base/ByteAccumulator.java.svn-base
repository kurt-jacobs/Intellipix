package com.intellipix.steganography;

import java.io.ByteArrayOutputStream;

import com.intellipix.util.PrintUtil;

public class ByteAccumulator {

	private ByteArrayOutputStream outputStream;
	private int shiftAmt=6;
	private int byteCnt=0;
	private int currentValue=0;
	
	public ByteAccumulator() {
		outputStream=new ByteArrayOutputStream();
	}

	public void putByte(byte sourceByte) {
		currentValue |= (sourceByte& 0x03) << shiftAmt;
		  shiftAmt-=2;
		  if (shiftAmt<0) {
			byteCnt++;
		    shiftAmt=6;
    	    outputStream.write((byte)currentValue);
  		    currentValue=0;  		  
		}
	}
	
	public int getOutputLength() {
		return byteCnt;
	}
	
	public byte[] getByteArray() {
		return outputStream.toByteArray();
	}
	
	public static void main(String args[]) {
		byte[] test={ (byte) 0xb7, (byte) 0x03, (byte)0xfd };  // 10110111,00000011 ,11111101
		ByteAccumulator byteAccumulator=new ByteAccumulator();
		for (int i=0; i < test.length; i++) {
			byteAccumulator.putByte(test[i]);			 
		}		
		byte[] byteArray=byteAccumulator.getByteArray();
		PrintUtil.printByteBuffer("result",byteArray);
	}
	
}
