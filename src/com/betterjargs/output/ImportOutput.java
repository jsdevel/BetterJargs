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

package com.betterjargs.output;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Joseph Spencer
 */
public class ImportOutput extends Output {
   private Set<String> imports = new HashSet(Arrays.asList(new String[]{}));
   public ImportOutput add(String type){
      if(!imports.contains(type)){
         imports.add(type);

         String path=type;
         if(importTable.containsKey(type)){
            path=importTable.get(type);
         }

         items.add("import "+path+";\n");
      }
      return this;
   }

   private static final Map<String, String> importTable = new HashMap<>();
   static {
      importTable.put("String", "java.lang.String");
      importTable.put("File", "java.io.File");
      importTable.put("List<File>", "java.util.List");
   }
}
