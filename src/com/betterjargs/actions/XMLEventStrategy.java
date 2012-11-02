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

import com.betterjargs.BetterJargs;
import com.betterjargs.Output;
import java.util.*;
import javax.xml.stream.events.*;

/**
 *
 * @author Joseph Spencer
 */
public abstract class XMLEventStrategy {
   protected final XMLEventStrategy previousStrategy;
   protected final String localName;
   protected String indent="    ";

   //For Attributes
   private String[] init = {};
   private Set<String> handledAttributes = new HashSet<>(Arrays.asList(init));

   public XMLEventStrategy(XMLEventStrategy previous, String localName){
      this.previousStrategy=previous;
      this.localName=localName;
   }

   public abstract XMLEventStrategy handleElement(XMLEvent event) throws Exception;
   protected abstract void handleAttribute(String name, String value) throws Exception;

   public abstract void close(XMLEvent event) throws Exception;

   protected final void handleAttributes(XMLEvent event) throws Exception {
         StartElement startElement = event.asStartElement();

         Iterator<Attribute> attributes = startElement.getAttributes();

         while(attributes.hasNext()){
            Attribute attribute = attributes.next();
            String name = attribute.getName().getLocalPart();
            String value = attribute.getValue();

            if(handledAttributes.contains(name)){
               BetterJargs.out("Ignoring duplicate attribute "+name+" on arguments.");
            }
            handledAttributes.add(name);

            handleAttribute(name, value);
         }
   }

   public final XMLEventStrategy getPrevious(){
      if(previousStrategy!=null){
         return previousStrategy;
      }
      return this;
   }
}

