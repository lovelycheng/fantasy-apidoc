package tech.fantasy.apidoc.processor.swagger;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocMethodModel;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.model.ApiDocParamModel;
import tech.fantasy.apidoc.processor.AbstractParser;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.LinkedList;

/**
 * @author chengtong
 * @date 2019-09-12 11:41
 */
@AnnotationParser(type = ApiImplicitParams.class,name = "apiImplicitParamsParser")
public class ApiImplicitParamsParser extends AbstractParser {

    protected ApiImplicitParamsParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Resource()
    @Qualifier("apiImplicitParamParser")
    ApiImplicitParamParser apiImplicitParamParser;


    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if (annotation instanceof ApiImplicitParams) {
            ApiImplicitParam[] implicitParams = ((ApiImplicitParams) annotation).value();
            ApiDocMethodModel apiDocMethodModel = (ApiDocMethodModel) model;

            LinkedList<ApiDocParamModel> linkedList = apiDocMethodModel.getApiDocParamModels();
            if(implicitParams.length!= linkedList.size()){
                return;
            }
            for(int c = 0;c<linkedList.size();c++){

                ApiImplicitParam apiImplicitParam = implicitParams[c];
                ApiDocParamModel apiDocParamModel = linkedList.get(c);
                apiImplicitParamParser.parser(apiDocParamModel, apiImplicitParam);
                apiDocParamModel.setDescription(apiImplicitParam.value());
                apiDocParamModel.setNullAble( !apiImplicitParam.required());

            }
        }
    }
}
