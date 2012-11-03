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
public class ArgumentElement extends Element {
   private String name;
   private String description="";
   private String type;
   private String fieldName;
   private String fieldType;

   public void setName(String name) throws Exception {
      dup(this.name);
      empty(name);
      this.name=name;
      this.fieldName = name.toLowerCase().replaceAll("[^a-z]", "");
   }
   public String getName(){return name;}

   public void setDescription(String description) throws Exception {
      empty(description);
      this.description=description;
   }
   public String getDescription(){return description;}

   public void setType(String type) throws Exception {
      dup(this.type);
      empty(type);
      this.type=type;
      this.fieldType=convertType(type);
   }
   public String getType(){return type;}

   public String getFieldName(){return fieldName;}
   public String getFieldType(){return fieldType;}
}
