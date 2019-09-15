package com.treefinance.hound.processor.swagger;


import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.ApiModel;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-11 22:03
 */
@AnnotationParser(type = ApiModel.class,name = "apiModelParser")
@Slf4j
public class ApiModelParser extends AbstractParser {

    protected ApiModelParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        log.info("apiModelParser parser");



    }
}
