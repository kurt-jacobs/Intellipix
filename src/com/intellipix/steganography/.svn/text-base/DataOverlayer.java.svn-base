package com.intellipix.steganography;



@SuppressWarnings("unchecked")
public class DataOverlayer {
	private static DataOverlayer instance=null;	
    

	private DataOverlayer() {
		
	}
	
	public static DataOverlayer getInstance() {
		if (instance==null) {
			instance=new DataOverlayer();
		}
		return instance;
	}
 
    // front pad the size of the buffer
//    private byte[] getItemLength(long length) {    	
//       String itemLengthInHex=Long.toHexString(length); 
//       for (int deficit=BufferConstants.DATA_SIZE-itemLengthInHex.length(); deficit>0; --deficit) {
//    	   itemLengthInHex="0"+itemLengthInHex;
//		}	  
//       
//       return itemLengthInHex.getBytes();
//    }
    
    
    private byte[] getItemLength(long length) {  
    	String hexString=String.format("%06X", length);
        return hexString.getBytes();
     }
    
    public int calculateOverLaySize(AbstractDataItem ...dataItems) {
    	int overlayLength=0;
 
        overlayLength=BufferConstants.APP_TAG;
    	overlayLength+=BufferConstants.DATA_SIZE;  
    	overlayLength+=BufferConstants.NUM_ELEMENTS;  
    	
		for (AbstractDataItem dataItem : dataItems) {	   		 
		  overlayLength+=BufferConstants.DATA_ID;     // id into bitmap	
		  overlayLength+=BufferConstants.MIME_TYPE;   // mime type;
		  overlayLength+= BufferConstants.DATA_SIZE;  // size of data
	      overlayLength+=dataItem.getLength();        // length of data
		}
		
		return overlayLength; 	
    }
    
    public void overlayByteArrayOnIntArray(byte[] source, int[] target) {    	
		   ByteProvider byteProvider=new ByteProvider(source);
		  
	    	for (int index=0; index<target.length; index++) {
	    		// get a version of the target that has bottom 2 bits cleared out
	    		target[index] &= 0x00fcfcfc; // mask out bits in 3 positions ...
	    		target[index] |= 0xff000000; // ensure alpha is ff
	    		target[index] |= (byteProvider.getNextByte() << 16);
	        	target[index] |= (byteProvider.getNextByte() << 8);    		
	    		target[index] |= (byteProvider.getNextByte());       	    			    	 
	    	}
	}    

  
	public void overlayData(byte[] target, AbstractDataItem ...dataItems) {
		int index=0; 
		
		byte numberElements=(byte)dataItems.length;
		index=DataOverlayAdapter.overlayData(BufferConstants.APPLICATION_TAG.getBytes(), 
				target, index);
		index=DataOverlayAdapter.overlayData(getItemLength(calculateOverLaySize(dataItems)), target, index);
		// Write the number of elements we have
		index=DataOverlayAdapter.overlayData(numberElements, 
				target, index);
		for (AbstractDataItem dataItem : dataItems) {	   
		  // put id into bitmap	
		  index=DataOverlayAdapter.overlayData(dataItem.getId(), target, index);
		  // put mime type into bitmap
		  index=DataOverlayAdapter.overlayData(dataItem.getMimeType(), target, index); 
		  // put data into bitmap
		  index=DataOverlayAdapter.overlayData(getItemLength(dataItem.getLength()), target, index);
		  DataOverlayAdapter adapter=
			  DataOverlayAdapterFactory.getDataOverlayAdapter(dataItem.getMimeType());			 
		  index=adapter.overlayData(dataItem.getItem(), target,index);
		}
	}

 
}
