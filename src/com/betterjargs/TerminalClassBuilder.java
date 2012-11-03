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

import java.io.*;
import java.util.*;

/**
 *
 * @author Joseph Spencer
 */
public class TerminalClassBuilder {

   public static void buildTerminalArguments(ArgumentsElement arguments){
      BetterJargs.out("Building class for terminal.");

      String indent = arguments.getIndent();
      String className = arguments.getClassName()+"Terminal";

      Output output = new Output();
      ImportOutput importOutput = new ImportOutput();
      Output privateFieldOutput = new Output();
      Output loopOutput = new Output();
      Output testOutput = new Output();
      Output verifyOutput = new Output();
      Output getterOutput = new Output();

      if(!"".equals(arguments.getPackageName())){
         output.add("package ").add(arguments.getPackageName()).add(";\n\n");
      }

      output.
         add(importOutput).
         add("public class ").add(className).add(" {\n\n").
            add(privateFieldOutput).
            add("\n").
            add(indent).
            add("public ").add(className).add("(String[] args){\n").
            add(loopOutput).
            add(indent+"}\n\n").
            add(getterOutput).
            add(BetterJargs.getPathMethod(indent)).
         add("}");

      buildLoop(loopOutput, indent, testOutput, verifyOutput);

      Iterator<ArgumentElement> args = arguments.getArgumentIterator();
      while(args.hasNext()){
         ArgumentElement arg = args.next();
         String type = arg.getFieldType();
         String name = arg.getFieldName();

         importOutput.add(type);


         buildField(privateFieldOutput, type, indent, name);
         buildGetMethod(getterOutput, type, indent, name);


      }




      try {
         MainUtil.putString(new File(className+".java"), output.toString());
      } catch (IOException exc){
         BetterJargs.out(exc.getMessage());
      }
   }

   public static void buildField(Output out, String type, String indent, String name){
         out.add(indent).
            add("private "+type+" "+name+";\n");
   }

   public static void buildGetMethod(Output out, String type, String indent, String name){
      String firstChar = Character.toString(name.charAt(0));
      out.
         add(indent).
         add("public "+type+" get"+name.replaceFirst(firstChar, firstChar.toUpperCase())+"(){\n").
         add(indent).
         add(indent).
         add("return "+name+";\n").
         add(indent).
         add("}\n");
   }

   public static void buildLoop(Output loopOutput, String indent, Output testOutput, Output verifyOutput){
      loopOutput.
         add(indent).
         add(indent).
         add("int len = args.length;\n").
         add(indent).
         add(indent).
         add("int i=0;\n").
         add(indent).
         add(indent).
         add("for(;i+1<len;i+=2){\n").
         add(indent).
         add(indent).
         add(indent).
         add("String key = args[i];\n").
         add(indent).
         add(indent).
         add(indent).
         add("String val = args[i+1];\n").
         add(testOutput).
         add(indent).
         add(indent).
         add("}\n").
         add(indent).
         add(indent).
         add("if(3 % len > 0){\n").
         add(indent).
         add(indent).
         add(indent).
         add("throw new IllegalArgumentException(\"An even number of arguments must be given.\");\n").
         add(indent).
         add(indent).
         add("}\n").
         add(verifyOutput);
   }
}
