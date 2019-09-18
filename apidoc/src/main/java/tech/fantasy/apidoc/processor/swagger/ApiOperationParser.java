package tech.fantasy.apidoc.processor.swagger;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocMethodModel;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.processor.AbstractParser;
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
