package com.intellipix.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

 

public class FileUtils {
 
      public static List<File> getFileList(FilenameFilter fileFilter,String path) {
          File home = new File(path);
          List<File> fileList = new ArrayList<File>();
          if (home.listFiles(fileFilter).length > 0) {
              for (File file : home.listFiles(fileFilter)) {            	  
               	    fileList.add(file);            	  
              }
          }         
          return fileList;
      }
      
      public static boolean filesAvailable(FilenameFilter fileFilter,String path) {          
          return (getFileList(fileFilter,path).size()>0);
      }
 
}
