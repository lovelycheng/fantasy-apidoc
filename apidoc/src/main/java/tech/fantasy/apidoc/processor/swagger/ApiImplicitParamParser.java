package tech.fantasy.apidoc.processor.swagger;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.model.ApiDocParamModel;
import tech.fantasy.apidoc.processor.AbstractParser;
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
