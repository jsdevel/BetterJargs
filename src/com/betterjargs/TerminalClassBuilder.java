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

package com.betterjargs;

import com.betterjargs.output.CodeFormatter;
import com.betterjargs.output.ImportOutput;
import com.betterjargs.output.Output;
import com.betterjargs.elements.ArgumentsElement;
import com.betterjargs.elements.ArgumentElement;
import java.util.Iterator;


/**
 *
 * @author Joseph Spencer
 */
public class TerminalClassBuilder {

   public static Object buildTerminalArguments(ArgumentsElement arguments){
      String indent = arguments.getIndent();
      String className = arguments.getClassName()+"Terminal";

      BetterJargs.out("Building "+className+".java");

      CodeFormatter output = new CodeFormatter(indent);
      ImportOutput importOutput = new ImportOutput();
      CodeFormatter privateFieldOutput = new CodeFormatter(indent).addIndent(1);
      CodeFormatter testOutput = new CodeFormatter(indent).addIndent(3);
      CodeFormatter verifyOutput = new CodeFormatter(indent).addIndent(4);
      CodeFormatter getterOutput = new CodeFormatter(indent).addIndent(1);

      if(!"".equals(arguments.getPackageName())){
         output.add("package ").add(arguments.getPackageName()).add(";\n\n");
      }

      output.
         add(importOutput).
         
         addLine("public class "+className+" {").
            add(privateFieldOutput).
            addLine().
            addIndent().
            addLine("public "+className+"(String[] args) throws IllegalArgumentException {").
            addIndent().
               addLine("super();").
               addLine("if(__showHelpOnNoArgs && args.length == 0){").addIndent().
                  addLine("System.out.print("+arguments.getClassName()+"Help.getHelpMenu());").
                  addLine("System.exit(0);").removeIndent().
               addLine("}").
               addLine("int len = args.length;").
               addLine("int i=0;").
               addLine("for(;i+1<len;i+=2){").
               addIndent().
                  addLine("String key = args[i];").
                  addLine("String val = args[i+1];").
               add(testOutput).
               removeIndent().
               addLine("}").
               addLine("if(i - len != 0){").
               addIndent().
                  addLine("throw new IllegalArgumentException(\"An even number of arguments must be given.\");").
               removeIndent().
               addLine("}").
               add(verifyOutput).
               removeIndent().
            addLine("}").
            add(getterOutput).
            add(BetterJargs.getPathMethod(indent, 1)).
            add(BetterJargs.getGetBooleanMethod(indent, 1)).
            removeIndent().
         addLine("}");

      if(arguments.getHelp()){
         privateFieldOutput.addLine("private boolean __showHelpOnNoArgs=true;");
      } else {
         privateFieldOutput.addLine("private boolean __showHelpOnNoArgs;");
      }

      Iterator<ArgumentElement> args = arguments.getArgumentIterator();
      while(args.hasNext()){
         ArgumentElement arg = args.next();
         String fieldType = arg.getFieldType();
         String name = arg.getFieldName();

         buildField(privateFieldOutput, fieldType, name, arg);
         buildFieldTest(testOutput, arg);
         buildImport(importOutput, arg);
         buildGetMethod(getterOutput, fieldType, name);
         buildRequiredFieldValidation(verifyOutput, arg);
      }
      return output;

   }

   public static void buildField(CodeFormatter out, String type, String name, ArgumentElement arg){
         String defaultValue = arg.hasDefault() ? "=" + arg.getDefault() : "" ;
         out.addLine("private "+type+" "+name+ defaultValue+ ";");
   }

   public static void buildFieldTest(CodeFormatter out, ArgumentElement arg){
      String fieldType = arg.getFieldType();
      String fieldName = arg.getFieldName();

      out.addLine("if(\""+arg.getName()+"\".equals(key)){").
               addIndent();
         switch(arg.getType()){
         case "directory":
         case "file":
            out.
               addLine("String newPath = getPath(val);").
               addLine(fieldName+" = new "+fieldType+"(newPath);");
               if("directory".equals(arg.getType())){
                  out.
                  addLine("if(!"+fieldName+".isDirectory()) {").
                  addIndent().
                  addLine("throw new IllegalArgumentException(\"Directory doesn't exist :'\"+val+\"'.  Given by argument '\"+key+\"'.\");").
                  removeIndent().
                  addLine("}");

               }
               if(arg.getOverwrite()){
                  out.
                  addLine("if("+fieldName+".exists() && !"+fieldName+".canWrite()) {").
                  addIndent().
                     addLine("throw new IllegalArgumentException(\"The following file may not be overwritten to: '\"+"+fieldName+"+\"'.\");").
                  removeIndent().
                  addLine("}");
               }
            break;
         case "boolean":
            out.addLine(fieldName+" = getBoolean(val);");
            break;
         }

      out.addLine("continue;").
         removeIndent().
         addLine("}");
   }


   public static void buildGetMethod(CodeFormatter out, String type, String name){
      String firstChar = Character.toString(name.charAt(0));
      out.
         addLine("public "+type+" get"+name.replaceFirst(firstChar, firstChar.toUpperCase())+"(){").addIndent().
            addLine("return "+name+";").removeIndent().
         addLine("}");
   }
   public static void buildImport(ImportOutput out, ArgumentElement arg){
      switch(arg.getType()){
      case "boolean":
      case "int":
      case "integer":
         return;
      }
      out.add(arg.getFieldType());
   }
   public static void buildRequiredFieldValidation(CodeFormatter out, ArgumentElement arg){
      if(arg.getRequired()){
         String fieldName = arg.getFieldName();
         String name = arg.getName();
         out.
         addLine("if("+fieldName+"==null) {").addIndent().
         addLine("throw new IllegalArgumentException(\"The following argument is required: '"+name+"'.\");").removeIndent().
         addLine("}");
      }
   }

   public static void buildLoop(Output loopOutput, String indent, Output testOutput, Output verifyOutput){

   }
}
