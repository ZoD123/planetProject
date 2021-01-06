package spielwiese;

import org.junit.platform.commons.util.ReflectionUtils;


import java.util.HashMap;

public class main {



    public static void main(String[] args) {
        HashMap<Test, Integer> testMap = new HashMap<Test, Integer>();
        String hallo = "123";
        Object Inkognito = hallo;
        Class classString = String.class;
        Class inKognitoClass = Inkognito.getClass();


        boolean b = ReflectionUtils.isAssignableTo(String.class ,Inkognito.getClass());
        if (inKognitoClass.isAssignableFrom(String.class)) {

            String hallo2 = (String) Inkognito;
        }

        var test1 = new Test();
        var test2 = new Test();

        test1.value = 5;
        test2.value = 6;

        testMap.put(test1,2);
        testMap.put(test2,3);
        testMap.put(test1,4);

        System.out.println("blubb");

    }

}
