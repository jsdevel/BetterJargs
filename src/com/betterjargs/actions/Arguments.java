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
   private Output programOutput;

   public Arguments(XMLEventStrategy previous, Output output) {
      super(previous, "arguments");
      programOutput=output;
   }

   private boolean programStarted;

   private Output packageOutput = new Output();
   private Output privateFieldOutput = new Output();
   private Output classOutput = new Output();
   private Output constructorOutput = new Output();

   private boolean isClassNameSupplied;



   @Override
   public XMLEventStrategy handleElement(XMLEvent event) throws Exception {

      if(!programStarted){
         programStarted=true;
         programOutput.
            add(packageOutput).
            add("public class ").
               add(classOutput).
            add(" {\n\n").
               add(privateFieldOutput).
            add("}");

         handleAttributes(event);

         //placing this here so indent may be initialized.
         privateFieldOutput.
            add(indent+"private final Map<String, Argument> arguments = new HashMap<String, Argument>();\n\n").
            add(indent+"public ").add(classOutput).add("(){\n\n").
               add(constructorOutput).add(indent+"}\n\n");

      }
      Argument newAction = new Argument(this, constructorOutput);
      return newAction;
   }

   @Override
   public void close(XMLEvent event) throws Exception {
      if(!isClassNameSupplied){
         throw new Exception("You must provide a class name via the 'class' attribute on the arguments element.");
      }
   }

   @Override
   public void handleAttribute(String name, String value) throws Exception {
      switch(name) {
      case "menulength":
         privateFieldOutput.add(indent+"private int menulength="+value+";\n\n");
         break;
      case "package":
         packageOutput.add("package "+value+";\n\n");
         break;
      case "indent":
         indent=value;
         break;
      case "class":
         isClassNameSupplied=true;
         classOutput.add(value);
         break;
      default:
         BetterJargs.out("Ignoring unknown attribute '"+name+"' on arguments.");
         break;
      }
   }
}
