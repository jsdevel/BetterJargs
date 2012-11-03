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

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Joseph Spencer
 */
public class ArgumentsElement extends Element {
   private String className;
   private String indent="";
   private String packageName="";
   private int menulength=80;
   private boolean terminal;

   private ArrayList<ArgumentElement> arguments = new ArrayList();

   public void setClass(String name) throws Exception {
      dup(className);
      empty(name);
      className=name;
   }
   public String getClassName(){return className;}

   public void setIndnet(String indent) throws Exception {
      empty(indent);
      this.indent=indent;
   }
   public String getIndent(){return indent;}

   public void setMenuLength(String menuLength) throws Exception {
      empty(menuLength);
      menulength=new Integer(menuLength);
   }
   public int getMenuLength(){return menulength;}

   public void setPackage(String packageName) throws Exception {
      empty(packageName);
      this.packageName=packageName;
   }
   public String getPackageName(){return packageName;}

   public void setTerminal(String terminal) throws Exception {
      empty(terminal);
      this.terminal=getBoolean(terminal);
   }
   public boolean getTerminal(){return terminal;}

   public Iterator<ArgumentElement> getArgumentIterator(){return arguments.iterator();}









   public void addArgument(ArgumentElement argument){
      arguments.add(argument);
   }
}
