BetterJargs
===========

BetterJargs will output .java files to handle incoming arguments to your jar files.  It differs from other tools in that you don't need to include BetterJargs into your project.  All you need to do is define an xml file that represents the arguments that your jar will except, and place the outputted files in your project accordingly.

The following is an example xml file that is used to generate a terminal arguments validation class:

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

If you navigate to the package here on github, you will see the output from BetterJargs.