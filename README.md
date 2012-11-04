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

# How to run
To produce 'BetterJargsTerminal.java', BetterJargs was run with the following arguments: 
`--input-xml src/com/betterjargs/arguments.xml --output-directory src/com/betterjargs/arguments`.

# Elements
Only two elements may appear in an arguments.xml file:

* `arguments` (root element)
* `argument`



## `arguments`
### Attributes

* `class`  Alpha numberic characters.  This will be the base name of all produced files  I.E. `class="MyClass" terminal="true"` produces `MyClassTerminal.java MyClassHelp.java`.

* `copyright`  Content to appear under the `COPYRIGHT` header in the help menu.

* `description` Content to appear under the `DESCRIPTION` header in the help menu.

* `help`  Value of `true` or `false` (`true` by default).  When enabled, any attempt to run your jar without
arguments will cause the help menu to display in stdout, and will exit your jar with a 0 status.
You can disable this by giving `false` as the value.

* `indent`  Spaces to use for indentation, bot in the output files and in the help menu.

* `license` Content to appear under the `LICENSE` header in the help menu.

* `menulength` Integer amount.  This is the length of all paragraphs in the help menu.

* `package`  The name of the package where the output files will reside.

* `title`  Content to appear under the `TITLE` header in the help menu.

* `terminal` Value of `true` or `false` (true by default).  Used to control whether or not a
terminal file is produced.

## `argument`
### Attributes

* `name`  The name of a valid argument.  Must be unique.  Required.

* `default` The default value of the argument.  `file` and `directory` are set to null when default is supplied in conjunction with this attribute.

* `description` Content to appear under the argument in the help menu. 

* `required` Value of `true` or `false` (`false` by default).  If set to `true`, then the absence of this argument when your jar is run will result in an Exception being thrown.

* `type` Value of `boolean`, `file`, or `directory` (more to come).  When set to `directory`, a check will be made to ensure that the directory exists.  If it does now exist, then an exception will be thrown.  When set to `boolean`, the following values will be considered true:  `true yes 1`.  All other values will be considered to be false.

* `overwrite`  Value of `true` or `false`.  Ignored when the type is not one of: `directory` or `file`.  When a valid type is used in conjuntion with a value of `true`, then a check will be made to ensure thathe resource may be written to, if not then an exception will be thrown.