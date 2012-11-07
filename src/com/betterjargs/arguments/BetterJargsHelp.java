package com.betterjargs.arguments;

public class BetterJargsHelp {
   public static String getHelpMenu(){
return "TITLE\n   BetterJargs \n\nCOPYRIGHT\n   © 2012 Joseph Spencer \n\nLICENSE\n   http://www.apache.org/licenses/LICENSE-2.0 \n\nDESCRIPTION\n   BetterJargs is a code generator for jar argument \n   validation. \n\n   It is very easy to use, and allows you to focus on coding \n   the important parts of your program. \n\nARGUMENTS\n   REQUIRED\n      --input-xml\n         The xml file that describes the input arguments. \n\n   OPTIONAL\n      --output-directory\n         The directory to output the built files. \n\n      inputfiles\n         [AS NESTED ANT TASK]\n         Accepts the input arguments.xml files. \n\n";   }
}
