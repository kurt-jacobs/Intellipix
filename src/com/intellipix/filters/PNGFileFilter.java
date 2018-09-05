package com.intellipix.filters;

import java.io.File;
import java.io.FilenameFilter;


public class PNGFileFilter implements FilenameFilter {
	  private static String PNG=".PNG";
	
	  public boolean accept(File dir, String name) {
		  return (name.toUpperCase().endsWith(PNG));
	  }
}
