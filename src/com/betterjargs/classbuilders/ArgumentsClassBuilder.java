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
import java.util.Iterator;

/**
 *
 * @author Joseph Spencer
 */
public class ArgumentsClassBuilder {
   public static Object buildArgumentsFile(ArgumentsElement args){
      String indent = args.getIndent();
      BetterJargs.out("Building "+args.getArgumentsClassName()+".java");

      CodeFormatter output = new CodeFormatter(indent);
      ImportOutput importOutput = new ImportOutput();
      CodeFormatter privateFieldOutput = new CodeFormatter(indent).addIndent(1);
      CodeFormatter paramOutput = new CodeFormatter(indent).addIndent(2);
      CodeFormatter testOutput = new CodeFormatter(indent).addIndent(2);
      CodeFormatter verifyRequiredOutput = new CodeFormatter(indent).addIndent(2);
      CodeFormatter assignmentOutput = new CodeFormatter(indent).addIndent(2);
      CodeFormatter getterOutput = new CodeFormatter(indent).addIndent(1);

      FileBuilderUtilities.buildPackage(output, args);

      output.
         add(importOutput).
         addLine("public class "+args.getArgumentsClassName()+" {").addIndent().
            addLine().
            add(privateFieldOutput).
            addLine().
            addLine("public "+args.getArgumentsClassName()+"(").
               add(paramOutput).
            addLine(") throws Throwable {").addIndent().
               add(testOutput).
               add(verifyRequiredOutput).
               add(assignmentOutput).
            removeIndent().
            addLine("}").removeIndent().
            addLine().
            add(getterOutput).
         addLine("}");


      //build regular properties first
      Iterator<NestedElement> arguments = args.getElements();
      while(arguments.hasNext()){
         NestedElement arg = arguments.next();
         String fieldName = arg.getFieldName();

         FileBuilderUtilities.buildField(privateFieldOutput, arg);
         FileBuilderUtilities.buildImport(importOutput, arg);
         FileBuilderUtilities.buildParam(paramOutput, arg, "final ", arguments.hasNext()?",":"");
         buildArgTest(testOutput, arg);
         FileBuilderUtilities.buildGetMethod(getterOutput, arg);
         FileBuilderUtilities.buildHasMethod(getterOutput, arg);
         FileBuilderUtilities.buildRequiredFieldValidation(verifyRequiredOutput, arg);
         assignmentOutput.addLine("this."+fieldName+"="+fieldName+";");
      }

      return output.toString();
   }   

   public static void buildArgTest(CodeFormatter out, NestedElement arg){
      String fieldName = arg.getFieldName();

      switch(arg.getType()){
      case "directory":
         out.
            addLine("if("+fieldName+" != null){").addIndent().
               addLine("if(!"+fieldName+".exists() || !"+fieldName+".isDirectory()){").addIndent().
                  addLine(fieldName+".mkdirs();").removeIndent().
               addLine("}").
               addLine("if(!("+fieldName+".exists() && "+fieldName+".isDirectory())){").addIndent().
                  addLine("throw new IllegalArgumentException(\"Directory doesn't exist :'\"+"+fieldName+"+\"'.  Given by argument '"+fieldName+"'.\");").removeIndent().
               addLine("}").removeIndent().
            addLine("}");
         break;
      case "file":
         if(arg.getOverwrite()){
            out.
            addLine("if("+fieldName+"!=null && "+fieldName+".exists() && !"+fieldName+".canWrite()) {").
            addIndent().
               addLine("throw new IllegalArgumentException(\"The following file may not be overwritten to: '"+fieldName+"'.\");").
            removeIndent().
            addLine("}");
         }
         break;
      }
   }
}
