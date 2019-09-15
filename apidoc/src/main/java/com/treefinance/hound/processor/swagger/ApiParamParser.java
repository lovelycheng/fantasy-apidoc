package com.treefinance.hound.processor.swagger;

import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocMethodModel;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.model.ApiDocParamModel;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiParam;

import java.lang.annotation.Annotation;
import java.util.LinkedList;

/**
 * @author chengtong
 * @date 2019-09-12 17:27
 */
@AnnotationParser(type = ApiParam.class, name = "apiParamParser")
public class ApiParamParser extends AbstractParser {

    protected ApiParamParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if (annotation instanceof ApiParam) {
            return;
//            ApiParam apiParam = (ApiParam) annotation;
//
//            ApiDocMethodModel apiDocMethodModel = (ApiDocMethodModel) model;
//
//            LinkedList<ApiDocParamModel> linkedList = apiDocMethodModel.getApiDocParamModels();
//
//            ApiDocParamModel apiDocParamModel = new ApiDocParamModel();
//
//            String value = apiParam.value();
//
//            apiDocParamModel.setDescription(value);
//
//            linkedList.add(apiDocParamModel);

        }
    }
}
