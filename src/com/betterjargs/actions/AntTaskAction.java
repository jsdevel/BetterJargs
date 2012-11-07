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

package com.betterjargs.actions;

import com.betterjargs.*;
import com.betterjargs.elements.*;
import com.betterjargs.elements.NestedElement;
import javax.xml.stream.events.*;

/**
 *
 * @author Joseph Spencer
 */
public class AntTaskAction extends XMLEventAction {
   private NestedElement element;
   private boolean isOpen;
   public AntTaskAction(XMLEventAction previous, NestedElement element) {
      super(previous, "anttask");
      this.element=element;
      element.setIsAntTask(true);
   }

   @Override
   public XMLEventAction handleElement(XMLEvent event) throws Exception {
      if(!isOpen){
         isOpen=true;
         handleAttributes(event);
         return this;
      } else {
         throw new Exception("Arguments do not accept children.");
      }
   }

   @Override
   protected void handleAttribute(String name, String value) throws Exception {
      switch(name) {
      case "description":
         element.setDescription(value);
         break;
      case "name":
         element.setName(value);
         break;
      case "type":
         switch(value){
         case "files":
            element.setType(value);
            break;
         }
         break;
      default:
         BetterJargs.out("Ignoring unknown attribute '"+name+"' on argument.");
         break;
      }
   }

   @Override
   public void close(XMLEvent event) throws Exception {
      if(!element.hasName() || !element.hasType()){
         throw new IllegalArgumentException("anttask elements need both a type and a name.");
      }
      //BetterJargs.out("Closing AntTaskAction");
   }

}
