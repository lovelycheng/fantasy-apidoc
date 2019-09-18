package tech.fantasy.apidoc.processor.swagger;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.processor.AbstractParser;
import io.swagger.annotations.ApiParam;

import java.lang.annotation.Annotation;

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
