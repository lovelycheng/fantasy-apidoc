package com.treefinance.hound.processor.swagger;

import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.model.ApiDocParamModel;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.ApiParam;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-12 17:25
 */

@AnnotationParser(type = ApiParam.class,name = "apiImplicitParamParser")
public class ApiImplicitParamParser extends AbstractParser {

    protected ApiImplicitParamParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if (annotation instanceof ApiParam) {
            ApiParam apiParam = (ApiParam) annotation;
            ApiDocParamModel apiDocParamModel = new ApiDocParamModel();


        }
    }
}
