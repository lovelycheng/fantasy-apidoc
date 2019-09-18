package tech.fantasy.apidoc;

import tech.fantasy.apidoc.annotations.AnnotationParser;

/**
 * @author chengtong
 * @date 2019-09-11 21:22
 */
public class ClassMain {




    public static void main(String... ms) throws NoSuchMethodException {

        final String annotationName = "com.treefinance.hound.annotations.AnnotationParser";
        System.err.println(AnnotationParser.class.getName());
    }

}
