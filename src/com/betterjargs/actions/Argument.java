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
import javax.xml.stream.events.*;

/**
 *
 * @author Joseph Spencer
 */
public class Argument extends XMLEventStrategy {
   private Output fieldOutput;
   private boolean isOpen;

   public Argument(XMLEventStrategy previous, Output output) {
      super(previous, "argument");
   }

   @Override
   public void close(XMLEvent event) throws Exception {
      BetterJargs.out("Closing Argument");
   }

   @Override
   public XMLEventStrategy handleElement(XMLEvent event) throws Exception {
      if(!isOpen){
         isOpen=true;
         handleAttributes(event);
         return this;
      } else {
         throw new Exception("Arguments do not accept children.");
      }
   }

   @Override
   public void handleAttribute(String name, String value) throws Exception {
   }

}
