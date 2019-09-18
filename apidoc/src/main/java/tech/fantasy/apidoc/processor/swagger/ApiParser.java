package tech.fantasy.apidoc.processor.swagger;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import tech.fantasy.apidoc.model.ApiDocClassModel;
import tech.fantasy.apidoc.model.ApiDocModelInterface;
import tech.fantasy.apidoc.processor.AbstractParser;
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
