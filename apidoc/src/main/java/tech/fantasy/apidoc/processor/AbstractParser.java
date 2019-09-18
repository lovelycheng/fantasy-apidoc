package tech.fantasy.apidoc.processor;

import tech.fantasy.apidoc.model.ApiDocModelInterface;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-11 21:54
 */
public abstract class AbstractParser {

    private Class annotationClass;

    private String beanName;

    protected abstract void parser(ApiDocModelInterface model, Annotation annotation);

    protected AbstractParser(Class<? extends Annotation> annotationClass){
       this.annotationClass = annotationClass;
    }

    public void setAnnotationClass(Class annotationClass){
        this.annotationClass = annotationClass;
    }


    public Class getAnnotationClass(){
        return this.annotationClass;
    }


    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }
}
