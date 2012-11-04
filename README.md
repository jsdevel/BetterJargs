BetterJargs
===========

BetterJargs will output .java files to handle incoming arguments to your jar files.  It differs from other tools in that you don't need to include BetterJargs into your project.  All you need to do is define an xml file that represents the arguments that your jar will except, and place the outputted files that BetterJargs produces in your project accordingly.  BetterJargs can generate code for the following environments:  terminal, ant, and maven.  A help class is generated as well, which formats all of the information in your arguments.xml file very nicely.

The following is an example xml file that is used to generate a terminal arguments validation class (note the terminal="true" attribute):

<pre>
&lt;?xml version="1.0" encoding="UTF-8"?&gt;
&lt;!--
Copyright 2012 Joseph Spencer.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
--&gt;
&lt;arguments 
   class="BetterJargs" 
   copyright="2012 Joseph Spencer"
   description="
      BetterJargs is a code generator for jar argument validation.\n

      It is very easy to use, and allows you to focus on coding
      the important parts of your program.
      "
   help="true"
   indent="   "
   license="http://www.apache.org/licenses/LICENSE-2.0"
   menulength="60"
   package="com.betterjargs.arguments" 
   title="BetterJargs"
   terminal="true"
&gt;
   &lt;argument 
      name="--input-xml" 
      description="The xml file that describes the input arguments." 
      required="true"
      type="file"/&gt;    

   &lt;argument 
      name="--output-directory" 
      description="The directory to output the built files." 
      overwrite="true"
      type="directory"/&gt;    
&lt;/arguments&gt;
</pre>

By specifying `terminal="true"`, 'Terminal' was appended to the name of the output java file.  Similar behavior is applied when `ant="true"` or `maven="true"` is applied.  If you navigate to the package specified in the sample xml here on github, you will see the output from BetterJargs as 'BetterJargsTerminal.java'.  

To produce 'BetterJargsTerminal.java', BetterJargs was run with the following arguments: `--input-xml src/com/betterjargs/arguments.xml --output-directory src/com/betterjargs/arguments`.