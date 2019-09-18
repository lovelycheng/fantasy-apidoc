package tech.fantasy.apidoc.processor.swagger;

import com.google.common.collect.Lists;
import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocMethodModel;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.model.ApiDocReturnModel;
import tech.fantasy.apidoc.processor.AbstractParser;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javafx.util.Pair;

import java.lang.annotation.Annotation;
import java.util.List;

/**
 * @author chengtong
 * @date 2019-09-12 17:27
 */
@AnnotationParser(type = ApiResponses.class,name = "apiResponsesParser")
public class ApiResponsesParser extends AbstractParser {

    protected ApiResponsesParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if (annotation instanceof ApiResponses) {

            ApiDocMethodModel methodModel = (ApiDocMethodModel) model;

            ApiDocReturnModel returnModel = methodModel.getApiDocReturnModel();

            ApiResponse[] apiResponses = ((ApiResponses) annotation).value();
            List<Pair<Integer, String>> list = Lists.newArrayList();

            for (ApiResponse apiResponse : apiResponses) {
                Integer code = apiResponse.code();
                String message = apiResponse.message();
                Pair<Integer, String> pair = new Pair<>(code, message);
                list.add(pair);
            }

            returnModel.setCodeMsgPairs(list);
        }
    }
}
