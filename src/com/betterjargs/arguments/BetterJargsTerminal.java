package com.betterjargs.arguments;

import java.io.File;
public class BetterJargsTerminal {
   private static final boolean __showHelpOnNoArgs=true;

   public static BetterJargsArguments getArguments(String[] args) throws Throwable {
      File inputxml=null;
      File outputdirectory=null;
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
            continue;
         }
         throw new IllegalArgumentException("Unknown argument: "+key);
      }
      if(i - len != 0){
         throw new IllegalArgumentException("An even number of arguments must be given.");
      }
      return new BetterJargsArguments(
            inputxml,
            outputdirectory
      );
   }
   public static final String getPath(String path){
      String pathToUse;
      if(path.startsWith("/")){
         pathToUse = path;
      } else {
         pathToUse = System.getProperty("user.dir")+"/"+path;
      }
      return pathToUse;
   }
   public static final boolean getBoolean(String bool){
      if(bool != null){
         String s = bool.toLowerCase();
         if("true".equals(bool) || "yes".equals(bool) || "1".equals(bool)){
            return true;
         }
      }
      return false;
   }
}
