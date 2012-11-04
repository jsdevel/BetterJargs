package com.betterjargs.arguments;

import java.io.File;

public class BetterJargsTerminal {
   private boolean __showHelpOnNoArgs=true;
   private File inputxml;
   private File outputdirectory;

   public BetterJargsTerminal(String[] args) throws IllegalArgumentException {
      super();
      if(__showHelpOnNoArgs && args.length == 0){
         System.out.print(BetterJargsHelp.getHelpMenu());
         System.exit(0);
      }
      int len = args.length;
      int i=0;
      for(;i+1<len;i+=2){
         String key = args[i];
         String val = args[i+1];
         if("--input-xml".equals(key)){
            String newPath = getPath(val);
            inputxml = new File(newPath);
            continue;
         }
         if("--output-directory".equals(key)){
            String newPath = getPath(val);
            outputdirectory = new File(newPath);
            if(!outputdirectory.isDirectory()) {
               throw new IllegalArgumentException("Directory doesn't exist :'"+val+"'.  Given by argument '"+key+"'.");
            }
            if(outputdirectory.exists() && !outputdirectory.canWrite()) {
               throw new IllegalArgumentException("The following file may not be overwritten to: '"+outputdirectory+"'.");
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
   }
   public File getInputxml(){
      return inputxml;
   }
   public File getOutputdirectory(){
      return outputdirectory;
   }
   private String getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
   public final boolean getBoolean(String bool){
      if(bool != null){
         String s = bool.toLowerCase();
         if("true".equals(bool) || "yes".equals(bool) || "1".equals(bool)){
            return true;
         }
      }
      return false;
   }
}
