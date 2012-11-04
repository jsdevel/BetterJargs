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

import com.betterjargs.elements.ArgumentsElement;
import com.betterjargs.elements.ArgumentElement;
import com.betterjargs.*;
import java.util.*;
import java.util.Iterator;
import javax.xml.stream.events.*;

/**
 *
 * @author Joseph Spencer
 */
public class ArgumentsAction extends XMLEventAction {
   private ArgumentsElement arguments;

   public ArgumentsAction(XMLEventAction previous, ArgumentsElement arguments) {
      super(previous, "arguments");
      this.arguments=arguments;
      //programOutput=output;
   }

   private boolean programStarted;



   @Override
   public XMLEventAction handleElement(XMLEvent event) throws Exception {

      if(!programStarted){
         programStarted=true;


         handleAttributes(event);

         return this;

      }
      ArgumentElement argument = new ArgumentElement();
      arguments.addArgument(argument);

      ArgumentAction newAction = new ArgumentAction(this, argument);

      return newAction.handleElement(event);
   }

   @Override
   public void close(XMLEvent event) throws Exception {
      if(arguments.getClassName() == null){
         throw new Exception("You must provide a class name via the 'class' attribute on the arguments element.");
      }
      //BetterJargs.out("Closing Arguments.");
   }

   @Override
   public void handleAttribute(String name, String value) throws Exception {
      switch(name) {
      case "class":
         arguments.setClass(value);
         break;
      case "copyright":
         arguments.setCopyright(value);
         break;
      case "description":
         arguments.setDescription(value);
         break;
      case "example":
         arguments.setExample(value);
         break;
      case "help":
         arguments.setHelp(value);
         break;
      case "indent":
         arguments.setIndnet(value);
         break;
      case "license":
         arguments.setLicense(value);
         break;
      case "menulength":
         arguments.setMenuLength(value);
         break;
      case "package":
         arguments.setPackage(value);
         break;
      case "terminal":
         arguments.setTerminal(value);
         break;
      case "title":
         arguments.setTitle(value);
         break;
      default:
         BetterJargs.out("Ignoring unknown attribute '"+name+"' on arguments.");
         break;
      }
   }
}
