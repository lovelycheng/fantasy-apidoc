package tech.fantasy.apidoc.annotations;

import org.springframework.context.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author chengtong
 * @date 2019-09-08 11:01
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(AppInfoRegistrar.class)
public @interface AppInfo {

    String title() default "";

    String owner() default "";

    String name() default "";

    String zkAddress() default "";


}
