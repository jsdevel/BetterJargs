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
import java.io.*;
import javax.xml.parsers.*;
import javax.xml.stream.*;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import org.xml.sax.*;

/**
 *
 * @author Joseph Spencer
 */
public class BetterJargs {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException, XMLStreamException, FileNotFoundException, Exception {
      String pathToXml = MainUtil.getPathToUse(args[0]);
      Output parseXml = parseXml(pathToXml);
      out(parseXml.toString());
   }

   public static Output parseXml(String pathToXml) throws FileNotFoundException, XMLStreamException, Exception{
      File xmlFile = new File(pathToXml);

      XMLInputFactory inputFactory = XMLInputFactory.newInstance();
      InputStream in = new FileInputStream(xmlFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

      Output programOutput = new Output();
      XMLEventStrategy currentStrategy = new Arguments(null, programOutput);

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

      return programOutput;
   }

   public static void out(String msg){
      System.out.println(msg);
   }
}
