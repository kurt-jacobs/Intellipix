package com.intellipix.steganography;

import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("unchecked")
public class DataItemFactory {
	 
	@SuppressWarnings("unchecked")
	private static Map<Byte, String> mimeTypeDateItemClassNameMap = null;

	@SuppressWarnings("unchecked")
	private static Map<Byte, String> getMimeTypeDateItemClassNameMap() {
		if (mimeTypeDateItemClassNameMap == null) {
			mimeTypeDateItemClassNameMap = new HashMap<Byte, String>();
			mimeTypeDateItemClassNameMap.put(MimeTypes.CAPTION,StringDataItem.class.getName());		 		
			mimeTypeDateItemClassNameMap.put(MimeTypes.MAP,LocationDataItem.class.getName());				 
			mimeTypeDateItemClassNameMap.put(MimeTypes.AUDIO,ByteArrayDataItem.class.getName());	
			mimeTypeDateItemClassNameMap.put(MimeTypes.DATE,ByteArrayDataItem.class.getName());	
		}

		return mimeTypeDateItemClassNameMap;
	}
	
	public static AbstractDataItem getDataItem(byte mimeType) {
		AbstractDataItem dataItem=null;
		
		try {
			AbstractDataItemInstanceFactory factory=
				new AbstractDataItemInstanceFactory(getMimeTypeDateItemClassNameMap().get(mimeType),mimeType);		 
			dataItem=(AbstractDataItem)factory.newInstance();
		} catch (ClassNotFoundException e) {
		
			e.printStackTrace();
		}
		
		return dataItem;
	}

}
