package com.betterjargs.arguments;

import java.io.File;
public class BetterJargsArguments {

   private File inputxml=null;
   private File outputdirectory=null;

   public BetterJargsArguments(
      final File inputxml,
      final File outputdirectory
   ) throws Throwable {
      if(outputdirectory != null){
         if(!outputdirectory.exists() || !outputdirectory.isDirectory()){
            outputdirectory.mkdirs();
         }
         if(!(outputdirectory.exists() && outputdirectory.isDirectory())){
            throw new IllegalArgumentException("Directory doesn't exist :'"+outputdirectory+"'.  Given by argument 'outputdirectory'.");
         }
      }
      if(inputxml==null) {
         throw new IllegalArgumentException("The following argument is required: '--input-xml'.");
      }
      this.inputxml=inputxml;
      this.outputdirectory=outputdirectory;
   }

   public File getInputxml(){
      return inputxml;
   }
   public boolean hasInputxml(){
      return inputxml!=null;
   }
   public File getOutputdirectory(){
      return outputdirectory;
   }
   public boolean hasOutputdirectory(){
      return outputdirectory!=null;
   }
}
