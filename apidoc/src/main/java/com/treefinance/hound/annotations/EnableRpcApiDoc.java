package com.treefinance.hound.annotations;

import com.treefinance.hound.processor.AnnotationFilteredClassPathScanner;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.core.annotation.AliasFor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengtong
 * @date 2019-09-07 21:59
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AnnotationScan
@AppInfo
@ComponentScan(basePackages = "com.treefinance.hound.processor.swagger",includeFilters =
        {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = AnnotationParser.class)})
public @interface EnableRpcApiDoc {

    /**
     * Base packages to scan for annotated @Service classes.
     * <p>
     * Use {@link #scanBasePackageClasses()} for a type-safe alternative to String-based
     * package names.
     *
     * @return the base packages to scan
     * @see AnnotationScan#basePackages()
     */
    @AliasFor(annotation = AnnotationScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    /**
     * Type-safe alternative to {@link #scanBasePackages()} for specifying the packages to
     * scan. The package of each class specified will be scanned.
     *
     * @return classes from the base packages to scan
     * @see AnnotationScan#basePackageClasses
     */
    @AliasFor(annotation = AnnotationScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    /**
     * a short basic description of this application, will displayed on a web page
     *
     */
    @AliasFor(annotation = AppInfo.class, attribute = "title")
    String title() default "";
    /**
     * the name of this application, will displayed on a web page
     *
     */
    @AliasFor(annotation = AppInfo.class, attribute = "name")
    String name() default "";

    /**
     * the owner of this application
     * */
    @AliasFor(annotation = AppInfo.class, attribute = "owner")
    String owner() default "";
    /**
     * the owner of this application
     * */
    @AliasFor(annotation = AppInfo.class, attribute = "zkAddress")
    String zkAddress() default "";
}
