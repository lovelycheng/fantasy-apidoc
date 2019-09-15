package com.treefinance.hound.processor.swagger;

import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocMethodModel;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.model.ApiDocReturnModel;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.ApiResponse;
import javafx.util.Pair;

import java.lang.annotation.Annotation;

/**
 * @author chengtong
 * @date 2019-09-14 16:12
 */
@AnnotationParser(type = ApiResponse.class, name = "apiResponseParser")
public class ApiResponseParser extends AbstractParser {

    protected ApiResponseParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        if (annotation instanceof ApiResponse) {
            ApiResponse apiResponse = (ApiResponse) annotation;

            ApiDocMethodModel methodModel = (ApiDocMethodModel) model;

            ApiDocReturnModel returnModel = methodModel.getApiDocReturnModel();

            Integer code = apiResponse.code();
            String message = apiResponse.message();
            Pair<Integer, String> pair = new Pair<>(code, message);

            returnModel.getCodeMsgPairs().add(pair);
        }
    }
}
