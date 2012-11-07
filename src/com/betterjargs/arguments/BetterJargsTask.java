package com.betterjargs.arguments;

import org.apache.tools.ant.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import java.io.File;
import java.util.List;

public class BetterJargsTask extends Task {

   private File inputxml=null;
   private File outputdirectory=null;
   private List<File> inputfiles=null;
   @Override
   public void execute() throws BuildException {
      try {
         com.betterjargs.BetterJargs.buildArguments(new BetterJargsArguments(
            inputxml,
            outputdirectory,
            inputfiles
         ));
      } catch (Exception exc) {
         throw new BuildException(exc.getMessage());
      }
   }

   public void setInputxml(File inputxml){
      this.inputxml=inputxml;
   }
   public void setOutputdirectory(File outputdirectory){
      this.outputdirectory=outputdirectory;
   }
   public void addConfigured(FileSet files){
      Iterator<FileResource> iterator = files.iterator();
      while(iterator.hasNext()){
         if(inputfiles==null){
            inputfiles= new ArrayList<File>();
         }
         File next = iterator.next().getFile();
         inputfiles.add(next);
      }
   }
}
