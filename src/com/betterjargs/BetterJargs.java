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

import com.betterjargs.output.CodeFormatter;
import com.betterjargs.elements.ArgumentsElement;
import com.betterjargs.actions.XMLEventAction;
import com.betterjargs.actions.*;
import com.betterjargs.arguments.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.stream.*;
import javax.xml.stream.events.XMLEvent;

/**
 *
 * @author Joseph Spencer
 */
public class BetterJargs {

   /**
    * @param args the command line arguments
    */
   public static void main(String[] args) {
      try {
         buildArguments(BetterJargsTerminal.getArguments(args));
      } catch (Exception exc){

      }
   }

   public static void buildArguments(BetterJargsArguments validatedArgs){
      try {
         ArgumentsElement arguments = getArgumentsElement(validatedArgs.getInputxml());

         String outputDir = validatedArgs.getOutputdirectory().getAbsolutePath() + "/";

         if(arguments.getTerminal()) {
            Object result = TerminalClassBuilder.buildTerminalArguments(arguments);

            MainUtil.putString(
               new File(outputDir + arguments.getTerminalClassName() + ".java"), 
               result.toString()
            );
         }
         if(arguments.hasAntCallback()){
            Object result = AntTaskBuilder.buildAntTask(arguments);
            MainUtil.putString(
               new File(outputDir + arguments.getAntClassName() + ".java"), 
               result.toString()
            );
         }
         MainUtil.putString(
            new File(outputDir + arguments.getHelpClassName() + ".java"), 
            HelpClassBuilder.buildHelpClass(arguments).toString()
         );
         MainUtil.putString(
            new File(outputDir + arguments.getArgumentsClassName() + ".java"), 
            ArgumentsClassBuilder.buildArgumentsFile(arguments).toString()
         );

      } catch(Exception exc) {
         out("\n");
         out("FAILED with the following message:\n");
         out(exc.getMessage());
         out("\n");
      }
   }

   public static ArgumentsElement getArgumentsElement(File xmlFile) throws Exception {
      ArgumentsElement arguments = new ArgumentsElement();

      XMLInputFactory inputFactory = XMLInputFactory.newInstance();

      InputStream in = new FileInputStream(xmlFile);
      XMLEventReader eventReader = inputFactory.createXMLEventReader(in);

      XMLEventAction currentStrategy = new ArgumentsAction(null, arguments);

      while(eventReader.hasNext()) {
         XMLEvent event = eventReader.nextEvent();

         if(event.isStartElement()) {
            currentStrategy = currentStrategy.handleElement(event);
         }
         if(event.isCharacters()) {
            if(!event.asCharacters().getData().matches("^\\s++$")) {
               throw new Exception("Text nodes are not allowed.");
            }
         }
         if(event.isEndElement()) {
            currentStrategy.close(event);
            currentStrategy = currentStrategy.getPrevious();
         }
      }

      return arguments;
   }

   public static void out(String msg) {
      System.out.println(msg);
   }

   public static CodeFormatter getPathMethod(String indent, int amount) {
      CodeFormatter out = new CodeFormatter(indent).addIndent(amount);

      out.
      addLine("public static final String getPath(String path){").
      addIndent().
      addLine("String pathToUse;").
      addLine("if(path.startsWith(\"/\")){").
      addIndent().
      addLine("pathToUse = path;").
      removeIndent().
      addLine("} else {").addIndent().
         addLine("pathToUse = System.getProperty(\"user.dir\")+\"/\"+path;").removeIndent().
      addLine("}").
      addLine("return pathToUse;").removeIndent().
      addLine("}");

      return out;
   }

   public static CodeFormatter getGetBooleanMethod(String indent, int amount){
      CodeFormatter out = new CodeFormatter(indent).addIndent(amount);

      out.addLine("public static final boolean getBoolean(String bool){").addIndent().
         addLine("if(bool != null){").addIndent().
            addLine("String s = bool.toLowerCase();").
            addLine("if(\"true\".equals(bool) || \"yes\".equals(bool) || \"1\".equals(bool)){").addIndent().
               addLine("return true;").removeIndent().
            addLine("}").removeIndent().
         addLine("}").
         addLine("return false;").removeIndent().
      addLine("}");
      return out;
   }
}
