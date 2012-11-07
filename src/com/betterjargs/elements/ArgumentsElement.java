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

package com.betterjargs.elements;

import java.util.ArrayList;
import java.util.Iterator;

/**
 *
 * @author Joseph Spencer
 */
public class ArgumentsElement extends Element {
   private String antCallback;
   private boolean hasAntTasks;
   private String className;
   private String copyright;
   private String example;
   private boolean help=true;
   private String indent;
   private String license;
   private int menulength;
   private String packageName;
   private boolean terminal;
   private String title;

   private ArrayList<NestedElement> elements = new ArrayList();

   //ANTCALLBACK
   public void setAntCallback(String callback) throws Exception {
      dup(antCallback);
      empty(callback);
      antCallback=callback;
   }
   public String getAntCallback(){return antCallback;}
   public boolean hasAntCallback(){return antCallback != null;}

   //HASANTTASKS
   public void setHasAntTasks(String flag) throws Exception {
      empty(flag);
      if(!hasAntTasks){
         hasAntTasks=getBoolean(flag);
      }
   }
   public boolean hasAntTasks(){return hasAntTasks;}

   //CLASS
   public void setClass(String name) throws Exception {
      dup(this.className);
      empty(name);
      className=name;
   }
   public String getClassName(){return className;}
   public boolean hasClassName(){return className != null;}

   //COPYRIGHT
   public void setCopyright(String copyright) throws Exception {
      dup(this.copyright);
      empty(copyright);
      this.copyright=copyright;
   }
   public String getCopyright(){return copyright;}
   public boolean hasCopyright(){return copyright != null;}

   //EXAMPLE
   public void setExample(String example) throws Exception {
      dup(this.example);
      empty(example);
      this.example=example;
   }
   public String getExapmle(){return example;}
   public boolean hasExample(){return example!=null;}

   //HELP
   public void setHelp(String help) throws Exception {
      empty(help);
      this.help=getBoolean(help);
   }
   public boolean getHelp(){return help;}

   //INDENT
   public void setIndnet(String indent) throws Exception {
      dup(this.indent);
      empty(indent);
      this.indent=indent;
   }
   public String getIndent(){return indent;}
   public boolean hasIndent(){return indent!=null;}

   //LICENSE
   public void setLicense(String license) throws Exception {
      dup(this.license);
      empty(license);
      this.license=license;
   }
   public String getLicense(){return license;}
   public boolean hasLicense(){return license!=null;}

   //MENULENGTH
   public void setMenuLength(String menuLength) throws Exception {
      empty(menuLength);
      menulength=new Integer(menuLength);
   }
   public int getMenuLength(){return menulength;}
   public boolean hasMenuLength(){return menulength>0;}

   //PACKAGE
   public void setPackage(String packageName) throws Exception {
      dup(this.packageName);
      empty(packageName);
      this.packageName=packageName;
   }
   public String getPackageName(){return packageName;}
   public boolean hasPackage(){return packageName!=null;}

   //TERMINAL
   public void setTerminal(String terminal) throws Exception {
      empty(terminal);
      this.terminal=getBoolean(terminal);
   }
   public boolean getTerminal(){return terminal;}

   //TITLE
   public void setTitle(String title) throws Exception {
      dup(this.title);
      empty(title);
      this.title=title;
   }
   public String getTitle(){return title;}
   public boolean hasTitle(){return title!=null;}

   //ELEMENTS
   public Iterator<NestedElement> getElements(){return elements.iterator();}
   public void addElement(NestedElement element){
      elements.add(element);
   }
   public boolean hasElements(){return elements.size() > 0;}

   //ClassNames
   public String getTerminalClassName(){
      return className+"Terminal";
   }
   public String getHelpClassName(){
      return className+"Help";
   }
   public String getAntClassName(){
      return className+"Task";
   }
   public String getArgumentsClassName(){
      return className+"Arguments";
   }
}
