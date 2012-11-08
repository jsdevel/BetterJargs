/*
 * Copyright 2012 Joseph Spencer.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.betterjargs.classbuilders;

import com.betterjargs.BetterJargs;
import com.betterjargs.elements.*;
import com.betterjargs.output.*;
import java.util.*;

/**
 *
 * @author Joseph Spencer
 */
public class AntTaskBuilder {
   public static Object buildAntTask(ArgumentsElement arguments){
      String indent = arguments.getIndent();
      BetterJargs.out("Building "+arguments.getAntClassName()+".java");



      CodeFormatter output = new CodeFormatter(indent);
      ImportOutput importOutput = new ImportOutput();
      CodeFormatter privateFieldOutput = new CodeFormatter(indent).addIndent(1);
      CodeFormatter paramsOutput = new CodeFormatter(indent).addIndent(4);
      CodeFormatter setterOutput = new CodeFormatter(indent).addIndent(1);


      FileBuilderUtilities.buildPackage(output, arguments);
      importOutput.add("org.apache.tools.ant.*");

      output.
         add(importOutput).
         addLine().
         addLine("public class "+arguments.getAntClassName()+" extends Task {").addIndent().
            addLine().
            add(privateFieldOutput).
            addLine("@Override").
            addLine("public void execute() throws BuildException {").addIndent().
               addLine("try {").addIndent().
                  addLine(arguments.getAntCallback()+"(new "+arguments.getArgumentsClassName()+"(").
                     add(paramsOutput).
                  addLine("));").removeIndent().
               addLine("} catch (Throwable exc) {").addIndent().
                  addLine("throw new BuildException(exc.getMessage());").removeIndent().
               addLine("}").
            removeIndent().
            addLine("}").removeIndent().
            addLine().
            add(setterOutput).
         addLine("}");

      importOutput.
         add("java.util.ArrayList").
         add("java.util.Iterator").
         add("org.apache.tools.ant.types.FileSet").
         add("org.apache.tools.ant.types.resources.FileResource");

      Iterator<NestedElement> args = arguments.getElements();
      while(args.hasNext()){
         NestedElement arg = args.next();
         String fieldName = arg.getFieldName();

         FileBuilderUtilities.buildField(privateFieldOutput, arg);
         FileBuilderUtilities.buildImport(importOutput, arg);
         paramsOutput.addLine(fieldName + (args.hasNext() ? "," : ""));
         if(arg.getIsAntTask()){
            buildAddConfiguredMethod(setterOutput, fieldName);
         } else {
            FileBuilderUtilities.buildSetMethod(setterOutput, arg);
         }
      }



      return output;
   }

   public static void buildAddConfiguredMethod(CodeFormatter out, String fieldName){
         out.
         addLine("public void addConfigured(FileSet files){").addIndent().
            addLine("Iterator<FileResource> iterator = files.iterator();").
            addLine("while(iterator.hasNext()){").addIndent().
               addLine("if("+fieldName+"==null){").addIndent().
                  addLine(fieldName+"= new ArrayList<File>();").removeIndent().
               addLine("}").
               addLine("File next = iterator.next().getFile();").
               addLine(fieldName+".add(next);").removeIndent().
            addLine("}").removeIndent().
         addLine("}");
   }
}
