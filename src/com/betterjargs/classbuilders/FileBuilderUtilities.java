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

import com.betterjargs.elements.*;
import com.betterjargs.output.*;

/**
 *
 * @author Joseph Spencer
 */
public class FileBuilderUtilities {
   public static void buildGetMethod(CodeFormatter out, NestedElement arg){
      String name = arg.getFieldName();
      String firstChar = Character.toString(name.charAt(0));
      out.
         addLine("public "+arg.getFieldType()+" get"+name.replaceFirst(firstChar, firstChar.toUpperCase())+"(){").addIndent().
            addLine("return "+name+";").removeIndent().
         addLine("}");
   }
   public static void buildHasMethod(CodeFormatter out, NestedElement arg){
      String fieldName = arg.getFieldName();
      String type = arg.getFieldType();
      String test;
      switch(type){
      case "boolean":
         test=fieldName;
         break;
      case "int":
         test=fieldName + "!=0";
         break;
      default:
         test = fieldName+"!=null";
      }

      String firstChar = Character.toString(fieldName.charAt(0));
      out.
         addLine("public boolean has"+fieldName.replaceFirst(firstChar, firstChar.toUpperCase())+"(){").addIndent().
            addLine("return "+test+";").removeIndent().
         addLine("}");
   }
   public static void buildSetMethod(CodeFormatter out, NestedElement arg){
      String name = arg.getFieldName();
      String firstChar = Character.toString(name.charAt(0));
      out.
         addLine("public void set"+name.replaceFirst(firstChar, firstChar.toUpperCase())+"("+arg.getFieldType() + " " + name + "){").addIndent().
            addLine("this."+name+"="+name+";").removeIndent().
         addLine("}");
   }

   public static void buildField(CodeFormatter out, NestedElement arg){
         buildVariable(out, arg, "private ", ";");
   }

   public static void buildParam(CodeFormatter out, NestedElement arg, String modifier, String closer){
      String mod = modifier == null ? "" : modifier;
      String close = closer == null ? "" : closer;

      out.addLine(mod + arg.getFieldType() + " " + arg.getFieldName() + close);
   }

   public static void buildVariable(CodeFormatter out, NestedElement arg, String modifier, String closer){
      String type = arg.getType();
      String argDefault = arg.getDefault();
      String defaultValue;
      if(argDefault != null){
         defaultValue = "="+arg.getDefault();
      } else {
         switch(type){
         case "int":
            defaultValue = "=0";
            break;
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

   public static void buildImport(ImportOutput out, NestedElement arg){
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

   public static void buildRequiredFieldValidation(CodeFormatter out, NestedElement arg){
      if(arg.getRequired()){
         String fieldName = arg.getFieldName();
         String name = arg.getName();
         out.
         addLine("if("+fieldName+"==null) {").addIndent().
         addLine("throw new IllegalArgumentException(\"The following argument is required: '"+name+"'.\");").removeIndent().
         addLine("}");
      }
   }


   public static CodeFormatter getPathMethod(String indent, int amount) {
      CodeFormatter out = new CodeFormatter(indent).addIndent(amount);

      out.
      addLine("public static final String getPath(String path){").
      addIndent().
      addLine("String pathToUse;").
      addLine("if(path.startsWith(\"/\")){").
      addIndent().
      addLine("pathToUse = path;").
      removeIndent().
      addLine("} else {").addIndent().
         addLine("pathToUse = System.getProperty(\"user.dir\")+\"/\"+path;").removeIndent().
      addLine("}").
      addLine("return pathToUse;").removeIndent().
      addLine("}");

      return out;
   }

   public static CodeFormatter getGetBooleanMethod(String indent, int amount){
      CodeFormatter out = new CodeFormatter(indent).addIndent(amount);

      out.addLine("public static final boolean getBoolean(String bool){").addIndent().
         addLine("if(bool != null){").addIndent().
            addLine("String s = bool.toLowerCase();").
            addLine("if(\"true\".equals(bool) || \"yes\".equals(bool) || \"1\".equals(bool)){").addIndent().
               addLine("return true;").removeIndent().
            addLine("}").removeIndent().
         addLine("}").
         addLine("return false;").removeIndent().
      addLine("}");
      return out;
   }

   public static CodeFormatter getGetIntMethod(String indent, int amount){
      CodeFormatter out = new CodeFormatter(indent).addIndent(amount);

      out.addLine("public static final int getInt(String in){").addIndent().
         addLine("if(in != null && !in.isEmpty()){").addIndent().
            addLine("return Integer.parseInt(in);").removeIndent().
         addLine("}").
         addLine("return 0;").removeIndent().
      addLine("}");
      return out;
   }

}
