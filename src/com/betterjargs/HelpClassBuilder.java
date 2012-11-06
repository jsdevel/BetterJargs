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
package com.betterjargs;

import com.betterjargs.elements.*;
import com.betterjargs.output.*;
import java.util.*;

/**
 *
 * @author Joseph Spencer
 */
public class HelpClassBuilder {

   public static Object buildHelpClass(ArgumentsElement args) {
      CodeFormatter out = new CodeFormatter(args.getIndent());
      CodeFormatter menu = new CodeFormatter(args.getIndent());
      int maxlength = args.getMenuLength();
      String className = args.getHelpClassName();
      BetterJargs.out("Building "+className+".java");

      if(args.hasPackage()) {
         out.addLine("package " + args.getPackageName() + ";").addLine();
      }
      out.
      addLine("public class " + className + " {").addIndent().
      addLine("public static String getHelpMenu(){").addIndent().
      add("return \"").add(menu).add("\";").removeIndent().
      addLine("}").removeIndent().
      addLine("}");

      if(args.hasTitle()){
         menu.
         add("TITLE\\n").addIndent();
         formatParagraph(args.getTitle(), menu, maxlength);
         menu.removeIndent();
      }

      if(args.hasCopyright()){
         menu.
         add("COPYRIGHT\\n").addIndent();
         formatParagraph("\u00A9 "+args.getCopyright(), menu, maxlength);
         menu.removeIndent();
      }

      if(args.hasLicense()){
         menu.
         add("LICENSE\\n").addIndent();
         formatParagraph(args.getLicense(), menu, maxlength);
         menu.removeIndent();
      }

      if(args.hasDescription()) {
         menu.
         add("DESCRIPTION\\n").addIndent();
         formatParagraph(args.getDescription(), menu, maxlength);
         menu.removeIndent();
      }

      if(args.hasExample()) {
         menu.
         add("EXAMPLE\\n").addIndent();
         formatParagraph(args.getExapmle(), menu, maxlength);
         menu.removeIndent();
      }


      if(args.hasArguments()) {
         CodeFormatter required = new CodeFormatter(args.getIndent()).addIndent(1);
         CodeFormatter optional = new CodeFormatter(args.getIndent()).addIndent(1);
         boolean requiredHeaderSet=false;
         boolean optionalHeaderSet=false;

         menu.
         add("ARGUMENTS\\n").add(required).add(optional);

         Iterator<ArgumentElement> elements = args.getArgumentIterator();
         while(elements.hasNext()) {
            ArgumentElement next = elements.next();
            String description = next.getDescription();
            String name = next.getName();
            CodeFormatter formatterToUse;

            if(next.getRequired()) {
               if(!requiredHeaderSet){
                  requiredHeaderSet=true;
                  required.doIndent("REQUIRED\\n").addIndent(1);
               }
               formatterToUse=required;
            } else {
               if(!optionalHeaderSet){
                  optionalHeaderSet=true;
                  optional.doIndent("OPTIONAL\\n").addIndent(1);
               }
               formatterToUse=optional;
            }

            formatterToUse.doIndent(name+"\\n").addIndent(1);

            formatParagraph(description, formatterToUse, maxlength);
            formatterToUse.removeIndent();
         }
      }

      return out;
   }

   private static void formatParagraph(String input, CodeFormatter copy, int maxlength) {
      String[] paragraphs = input.split("\\\\n");

      for(String paragraphString: paragraphs){
         ArrayList<String> words = new ArrayList(
            Arrays.asList(paragraphString.replaceAll("^\\s+|\\s$","").replaceAll("\\r?\\n|\\s+", " ").split("\\s"))
         );

         int proposedLength=0;
         Iterator<String> iter = words.iterator();
         boolean start=true;
         while(iter.hasNext()){
            String word = iter.next();
            int wordLength = word.length();
            if(wordLength + 1 + proposedLength > maxlength){
               
               copy.add("\\n").doIndent(word).add(" ");
               proposedLength=wordLength+1;
            } else {
               if(start){
                  start=false;
                  copy.doIndent();
               }
               copy.add(word).add(" ");
               proposedLength+=wordLength+1;
            }
         }
         start=true;
         copy.add("\\n\\n");

      }
   }
}
