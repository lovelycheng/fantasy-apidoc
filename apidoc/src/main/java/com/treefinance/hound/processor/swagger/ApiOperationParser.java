package com.treefinance.hound.processor.swagger;

import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocMethodModel;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.ApiOperation;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-14 17:36
 */
@AnnotationParser(type = ApiOperation.class,name = "apiOperationParser")
public class ApiOperationParser extends AbstractParser {


    protected ApiOperationParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if(annotation instanceof ApiOperation){
            ApiDocMethodModel methodModel = (ApiDocMethodModel) model;

            methodModel.setDescription(((ApiOperation) annotation).value());

        }

    }
}
