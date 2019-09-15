package com.treefinance.hound.processor.swagger;

import com.treefinance.hound.annotations.AnnotationParser;
import com.treefinance.hound.model.ApiDocClassModel;
import com.treefinance.hound.model.ApiDocModelInterface;
import com.treefinance.hound.processor.AbstractParser;
import io.swagger.annotations.Api;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * @author chengtong
 * @date 2019-09-12 08:21
 */
@AnnotationParser(type = Api.class,name = "apiParser")
public class ApiParser extends AbstractParser {

    public ApiParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {

        ApiDocClassModel classModel = (ApiDocClassModel) model;
        Api api = (Api) annotation;

        Map<String, Object> attributeMap = AnnotationUtils.getAnnotationAttributes(api);

        String value = (String) attributeMap.get("value");
        String[] tags = (String[]) attributeMap.getOrDefault("tages",new String[0]);

        classModel.setDescription(tags.length == 0 ? value : StringUtils.arrayToCommaDelimitedString(tags));


    }
}
