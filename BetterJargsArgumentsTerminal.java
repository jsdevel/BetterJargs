package com.betterjargs.arguments;

import java.io.File;

public class BetterJargsArgumentsTerminal {

   private File inputxml;
   private File outputdirectory;

   public BetterJargsArgumentsTerminal(String[] args){
      int len = args.length;
      int i=0;
      for(;i+1<len;i+=2){
         String key = args[i];
         String val = args[i+1];
      }
      if(3 % len > 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
   }

   public File getInputxml(){
      return inputxml;
   }
   public File getOutputdirectory(){
      return outputdirectory;
   }
   public String getPathToUse(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
}