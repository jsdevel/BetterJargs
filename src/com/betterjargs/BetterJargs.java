/*
 * Copyright 2012 Joseph Spencer
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

import com.betterjargs.actions.XMLEventStrategy;
import com.betterjargs.actions.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Joseph Spencer
 */
public class BetterJargs {
   private static String userDir = System.getProperty("user.dir");

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      String pathToXml = MainUtil.getPathToUse(args[0]);
      ArgumentsElement arguments = getArgumentsElement(pathToXml);

      if(arguments.getTerminal()){
         TerminalClassBuilder.buildTerminalArguments(arguments);
      }
   }

   public static ArgumentsElement getArgumentsElement(String pathToXml) {
      File xmlFile = new File(pathToXml);
      ArgumentsElement arguments = new ArgumentsElement();

      XMLInputFactory inputFactory = XMLInputFactory.newInstance();

      try {
         InputStream in = new FileInputStream(xmlFile);
         XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

         XMLEventStrategy currentStrategy = new Arguments(null, arguments);

         while(eventReader.hasNext()){
            XMLEvent event = eventReader.nextEvent();

            if(event.isStartElement()){
               currentStrategy=currentStrategy.handleElement(event);
            }
            if(event.isCharacters()){
               if(!event.asCharacters().getData().matches("^\\s++$")) {
                  throw new Exception("Text nodes are not allowed.");
               }
            }
            if(event.isEndElement()){
               currentStrategy=currentStrategy.getPrevious();
            }
            if(event.isEndDocument()){
               currentStrategy.close(event);
            }
         }
      } catch(Exception exc){
         out(exc.getMessage());
         System.exit(1);
      }

      return arguments;
   }

   public static void out(String msg){
      System.out.println(msg);
   }

   public static String getPathMethod(String indent){
      String si=indent;
      String di=indent+indent;
      String ti=di+indent;

      return 
      si+"public String getPathToUse(String path){\n"+
         di+"String pathToUse;\n"+
         di+"if(path.startsWith(\"/\")){\n"+
            ti+"pathToUse = path;\n"+
         di+"} else {\n"+
            ti+"pathToUse = System.getProperty(\"user.dir\")+\"/\"+path;\n"+
         di+"}\n"+
         di+"return pathToUse;\n"+
      si+"}\n";
   }
}
