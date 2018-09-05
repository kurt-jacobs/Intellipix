package com.intellipix.steganography;

public class AbstractDataItemInstanceFactory implements InstanceFactory{
    private String fullyQualifiedClassName;
    private byte mimeType;
	
	public AbstractDataItemInstanceFactory(String fullyQualifiedClassName,byte mimeType) throws ClassNotFoundException {  
		  this.fullyQualifiedClassName=fullyQualifiedClassName;
		  this.mimeType=mimeType;
	}
	
	
	@Override
	public Object newInstance() {
		 AbstractDataItem dataItem=null;
		
		 try {
			 InstanceFactory factory=new InstanceFactory.InstantiateSpecifiedClass(fullyQualifiedClassName);
			  dataItem=(AbstractDataItem)factory.newInstance();
			  dataItem.setMimeType(mimeType);		 
         } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
         return dataItem;
	}

}
