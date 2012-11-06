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

import com.betterjargs.elements.*;
import com.betterjargs.output.*;

/**
 *
 * @author Joseph Spencer
 */
public class FileBuilderUtilities {
   public static void buildGetMethod(CodeFormatter out, ArgumentElement arg){
      String name = arg.getFieldName();
      String firstChar = Character.toString(name.charAt(0));
      out.
         addLine("public "+arg.getFieldType()+" get"+name.replaceFirst(firstChar, firstChar.toUpperCase())+"(){").addIndent().
            addLine("return "+name+";").removeIndent().
         addLine("}");
   }

   public static void buildField(CodeFormatter out, ArgumentElement arg){
         buildVariable(out, arg, "private ", ";");
   }

   public static void buildParam(CodeFormatter out, ArgumentElement arg, String modifier, String closer){
      String mod = modifier == null ? "" : modifier;
      String close = closer == null ? "" : closer;

      out.addLine(mod + arg.getFieldType() + " " + arg.getFieldName() + close);
   }

   public static void buildVariable(CodeFormatter out, ArgumentElement arg, String modifier, String closer){
         String defaultValue;
         if(arg.getDefault() != null){
            defaultValue = "="+arg.getDefault();
         } else {
            switch(arg.getType()){
            case "boolean":
               defaultValue = "=false";
               break;
            default:
               defaultValue = "=null";
               break;
            }
         }
         buildParam(out, arg, modifier, defaultValue + closer);
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

   public static void buildPackage(CodeFormatter output, ArgumentsElement arguments){
      if(!"".equals(arguments.getPackageName())){
         output.add("package ").add(arguments.getPackageName()).add(";\n\n");
      }
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
}
