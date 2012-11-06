package com.betterjargs.arguments;

import java.io.File;

public class BetterJargsArguments {

   private File inputxml=null;
   private File outputdirectory=null;

   public BetterJargsArguments(
      final File inputxml,
      final File outputdirectory
   ){
      if(outputdirectory != null && !outputdirectory.isDirectory()) {
         throw new IllegalArgumentException("Directory doesn't exist :'"+outputdirectory+"'.  Given by argument 'outputdirectory'.");
      }
      if(outputdirectory!=null && outputdirectory.exists() && !outputdirectory.canWrite()) {
         throw new IllegalArgumentException("The following file may not be overwritten to: 'outputdirectory'.");
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
   public File getOutputdirectory(){
      return outputdirectory;
   }
}