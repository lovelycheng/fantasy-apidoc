package tech.fantasy.apidoc.processor;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.env.Environment;
import org.springframework.core.type.filter.AnnotationTypeFilter;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-12 09:26
 */
public class AnnotationFilteredClassPathScanner extends ClassPathBeanDefinitionScanner {

    public AnnotationFilteredClassPathScanner(BeanDefinitionRegistry registry,
                                              Class<? extends Annotation> annotationType, Environment environment) {
        super(registry,true,environment);
        addIncludeFilter(new AnnotationTypeFilter(annotationType));
    }


}
