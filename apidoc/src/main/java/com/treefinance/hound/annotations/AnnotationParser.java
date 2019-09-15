package com.treefinance.hound.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * indicate a bean is a <b>custom</b> annotation parser
 * @author chengtong
 * @date 2019-09-11 21:08
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface AnnotationParser {

    Class type();

    String name();

}
