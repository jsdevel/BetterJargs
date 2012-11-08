package com.betterjargs.arguments;

import org.apache.tools.ant.*;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.tools.ant.types.FileSet;
import org.apache.tools.ant.types.resources.FileResource;
import java.io.File;

public class BetterJargsTask extends Task {

   private File inputxml=null;
   private File outputdirectory=null;
   @Override
   public void execute() throws BuildException {
      try {
         com.betterjargs.BetterJargs.buildArguments(new BetterJargsArguments(
            inputxml,
            outputdirectory
         ));
      } catch (Throwable exc) {
         throw new BuildException(exc.getMessage());
      }
   }

   public void setInputxml(File inputxml){
      this.inputxml=inputxml;
   }
   public void setOutputdirectory(File outputdirectory){
      this.outputdirectory=outputdirectory;
   }
}
