package com.intellipix.steganography;

import java.util.HashMap;
import java.util.Map;

public class DataOverlayAdapterFactory {
	@SuppressWarnings("unchecked")
	private static Map<Byte, DataOverlayAdapter> overlayAdapterMap = null;

	@SuppressWarnings("unchecked")
	private static Map<Byte, DataOverlayAdapter> getOverlayAdapterMap() {
		if (overlayAdapterMap == null) {
			overlayAdapterMap = new HashMap<Byte, DataOverlayAdapter>();
			overlayAdapterMap.put(MimeTypes.CAPTION,
					new StringDataOverlayAdapter());
			overlayAdapterMap.put(MimeTypes.MAP,
					new LocationDataOverlayAdapter());
			overlayAdapterMap.put(MimeTypes.AUDIO,
					new DataOverlayAdapter());
			overlayAdapterMap.put(MimeTypes.DATE,
					new DataOverlayAdapter());
		}

		return overlayAdapterMap;
	}

	public static DataOverlayAdapter getDataOverlayAdapter(byte mimeType) {
		return getOverlayAdapterMap().get(mimeType);
	}

}
