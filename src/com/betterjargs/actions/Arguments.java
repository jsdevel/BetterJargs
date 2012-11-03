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
import java.util.*;
import java.util.Iterator;
import javax.xml.stream.events.*;

/**
 *
 * @author Joseph Spencer
 */
public class Arguments extends XMLEventStrategy {
   private ArgumentsElement arguments;

   public Arguments(XMLEventStrategy previous, ArgumentsElement arguments) {
      super(previous, "arguments");
      this.arguments=arguments;
      //programOutput=output;
   }

   private boolean programStarted;



   @Override
   public XMLEventStrategy handleElement(XMLEvent event) throws Exception {

      if(!programStarted){
         programStarted=true;


         handleAttributes(event);

         return this;

      }
      ArgumentElement argument = new ArgumentElement();
      arguments.addArgument(argument);

      Argument newAction = new Argument(this, argument);

      return newAction.handleElement(event);
   }

   @Override
   public void close(XMLEvent event) throws Exception {
      if(arguments.getClassName() == null){
         throw new Exception("You must provide a class name via the 'class' attribute on the arguments element.");
      }
   }

   @Override
   public void handleAttribute(String name, String value) throws Exception {
      switch(name) {
      case "menulength":
         arguments.setMenuLength(value);
         break;
      case "package":
         arguments.setPackage(value);
         break;
      case "indent":
         arguments.setIndnet(value);
         break;
      case "class":
         arguments.setClass(value);
         break;
      case "terminal":
         arguments.setTerminal(value);
         break;
      default:
         BetterJargs.out("Ignoring unknown attribute '"+name+"' on arguments.");
         break;
      }
   }
}
