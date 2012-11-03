package com.betterjargs.arguments;

import java.io.File;

public class BetterJargsTerminal {

   private File inputxml;
   private File outputdirectory;

   public BetterJargsTerminal(String[] args) throws IllegalArgumentException {
      super();
      int len = args.length;
      int i=0;
      for(;i+1<len;i+=2){
         String key = args[i];
         String val = args[i+1];
         if("--input-xml".equals(key)){
            String newPath = __getPath(val);
            inputxml = new File(newPath);
            continue;
         }
         if("--output-directory".equals(key)){
            String newPath = __getPath(val);
            outputdirectory = new File(newPath);
            if(!outputdirectory.isDirectory()) {
               throw new IllegalArgumentException("Directory doesn't exist :'"+val+"'.  Given by argument '"+key+"'.");
            }
            continue;
         }
      }
      if(i - len != 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
         if(inputxml==null) {
            throw new IllegalArgumentException("The following argument is required: '--input-xml'.");
         }
         if(outputdirectory==null) {
            throw new IllegalArgumentException("The following argument is required: '--output-directory'.");
         }
   }

   public File getInputxml(){
      return inputxml;
   }
   public File getOutputdirectory(){
      return outputdirectory;
   }
   private String __getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
}