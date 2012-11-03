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

/**
 *
 * @author Joseph Spencer
 */
public abstract class Element {
   public final void dup(String name) throws Exception {
      if(name != null){
         throw new Exception(name+" has already been defined.");
      }
   }

   public final void empty(String name) throws Exception {
      if("".equals(name)){
         throw new Exception(name+" may not be empty.");
      }
   }


   public final boolean getBoolean(String bool){
      if(bool != null){
         String s = bool.toLowerCase();
         if("true".equals(bool) || "yes".equals(bool) || "1".equals(bool)){
            return true;
         }
      }
      return false;
   }

   public final String convertType(String type){
      String acceptableType = type.toLowerCase();
      switch(acceptableType){
      case "string":
         return "String";
      case "boolean":
         return "boolean";
      case "directory":
      case "file":
         return "File";
      }
      return type;
   }
}
