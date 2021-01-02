package com.eisenglatz;

import java.util.regex.Pattern;

public class ClassNameExtractor {

    /**
     * tooling class - extract the name for a given Class Object
     */
    public ClassNameExtractor(){

    }

    /**
     * extract the name for a given Class Object
     * @param type the Class object
     * @return the name of the Class behind the class object.
     */
    public String getExtractedClassName(Class type){
        String classpath;
        String[] classes;
        Integer lastIndex;

        classpath = type.toString();
        classes = classpath.split( Pattern.quote( "." ));
        lastIndex = classes.length - 1;
        return classes[lastIndex];
    }

}
