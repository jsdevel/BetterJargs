package com.betterjargs.arguments;

import java.io.File;
import java.util.List;
public class BetterJargsArguments {

   private File inputxml=null;
   private File outputdirectory=null;
   private List<File> inputfiles=null;

   public BetterJargsArguments(
      final File inputxml,
      final File outputdirectory,
      final List<File> inputfiles
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
      this.inputfiles=inputfiles;
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
   public List<File> getInputfiles(){
      return inputfiles;
   }
   public boolean hasInputfiles(){
      return inputfiles!=null;
   }
}
