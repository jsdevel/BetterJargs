BetterJargs
===========

BetterJargs will output .java files to handle incoming arguments to your jar files.  It differs from other tools in that you don't need to include BetterJargs into your project.  All you need to do is define an xml file that represents the arguments that your jar will except, and place the outputted files in your project accordingly.  BetterJargs can create three types of input validation:  terminal, ant, and maven.

The following is an example xml file that is used to generate a terminal arguments validation class (note the terminal="true" attribute):

<pre>
&lt;arguments 
   class="BetterJargsArguments" 
   indent="   "
   menulength="80"
   package="com.betterjargs.arguments" 
   terminal="true"
&gt;
   &lt;argument 
      name="--input-xml" 
      description="The xml file that describes the input arguments." 
      overwrite="true"
      required="true"
      type="file"/&gt;    

   &lt;argument 
      name="--output-directory" 
      description="The directory to output the built files." 
      overwrite="true"
      required="true"
      type="directory"/&gt;    
&lt;/arguments&gt;
</pre>

By specifying `terminal="true"`, 'Terminal' was appended to the name of the output file.  Similar behavior is applied when `ant="true"` or `maven="true"` is applied.  If you navigate to the package specified in the sample xml here on github, you will see the output from BetterJargs as 'BetterJargsArgumentsTerminal.java'.