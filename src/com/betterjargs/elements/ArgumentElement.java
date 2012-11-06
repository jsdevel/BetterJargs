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

package com.betterjargs.elements;

import com.betterjargs.*;
import java.util.*;

/**
 *
 * @author Joseph Spencer
 */
public class ArgumentElement extends Element {

   private String name;
   private String defaultValue;
   private String description="";
   private String type;
   private String fieldName;
   private String fieldType;
   private boolean overwrite;
   private boolean required;

   public void setName(String name) throws Exception {
      dup(this.name);
      empty(name);
      uniqueName(name);
      this.name=name;
      this.fieldName = name.toLowerCase().replaceAll("[^a-z]", "");
   }
   public String getName(){return name;}

   public void setDefault(final String defaultVal) throws Exception {
      if(defaultVal != null){
         addQueuedAttribute(new QueuedAttribute() {
            @Override
            public void handle() {
               if(type != null){
                  switch(type){
                  case "boolean":
                     if(getBoolean(defaultVal)){
                        defaultValue = "true";
                     }
                     break;
                  default:
                     defaultValue = "null";
                     break;
                  }
               }
            }
         });
      }
   }
   public String getDefault(){return defaultValue;}
   public boolean hasDefault(){return defaultValue != null;}

   public void setDescription(String description) throws Exception {
      empty(description);
      this.description=description;
   }
   public String getDescription(){return description;}

   public void setOverwrite(String type) throws Exception {
      this.overwrite=getBoolean(type);
   }
   public boolean getOverwrite(){return overwrite;}

   public void setRequired(String required) throws Exception {
      this.required=getBoolean(required);
   }
   public boolean getRequired(){return required;}


   public void setType(String type) throws Exception {
      dup(this.type);
      empty(type);
      this.type=type.toLowerCase().replaceAll("\\s+", "");
      this.fieldType=convertType(type);
   }
   public String getType(){return type;}

   public String getFieldName(){return fieldName;}
   public String getFieldType(){return fieldType;}
}
