package com.treefinance.hound.processor;


import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.treefinance.hound.model.ApiDocClassModel;
import com.treefinance.hound.model.ApiDocMethodModel;
import com.treefinance.hound.model.ApiDocParamModel;
import com.treefinance.hound.model.ApiDocReturnModel;
import io.swagger.annotations.*;
import javafx.util.Pair;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.util.ClassUtils;
import sun.reflect.generics.reflectiveObjects.ParameterizedTypeImpl;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author chengtong
 * @date 2019-09-09 09:52
 */
@Slf4j
public class ApiDocParser {
    private String title;
    private String owner;
    private String address;

    String EMPTYSTRING = "";

    static final ArrayList ignore_Method = Lists.newArrayList("equals", "toString", "hashCode");

    static final HashMap<Class, AbstractParser> REGISTERED_PARSER = Maps.newHashMap();

    static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    private String resourcePattern = DEFAULT_RESOURCE_PATTERN;

    private Set<String> resolvedPackagesToScan;

    public ApiDocParser(Set<String> resolvedPackagesToScan) {
        this.resolvedPackagesToScan = resolvedPackagesToScan;
    }

    public List<ApiDocClassModel> parse() {

        List<ApiDocClassModel> apiDocClassModelList = Lists.newArrayList();

        for (String path : resolvedPackagesToScan) {
            Set<BeanDefinition> beanDefinitions = find(path);
            for (BeanDefinition beanDefinition : beanDefinitions) {
                Class clazz;
                try {
                    clazz = Class.forName(beanDefinition.getBeanClassName());
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage());
                    continue;
                }

                ApiDocClassModel classModel = new ApiDocClassModel();
                Annotation[] annotationsFromClasses = clazz.getAnnotations();
                for (Annotation annotation : annotationsFromClasses) {
                    AbstractParser abstractParser = REGISTERED_PARSER.get(annotation.getClass());
                    if (abstractParser == null) {
                        continue;
                    }
                    abstractParser.parser(classModel, annotation);
                }
                classMetaDataProcess(clazz, classModel);

                // methods
                Method[] methods = clazz.getDeclaredMethods();
                List<ApiDocMethodModel> apiDocMethodModelList = new ArrayList<>(methods.length);
                for (Method method : methods) {
                    methodProcess(apiDocMethodModelList, method);
                }
                classModel.setMethodModels(apiDocMethodModelList);
                apiDocClassModelList.add(classModel);
            }
        }
        return apiDocClassModelList;
    }

    private void classMetaDataProcess(Class clazz, ApiDocClassModel classModel) {
        String name = clazz.getSimpleName();
        String typeName = clazz.getTypeName();
        String clazzPackage = clazz.getPackage().getName();

        classModel.setTypeName(typeName);
        classModel.setClazzPackage(clazzPackage);
        classModel.setSimpleName(name);
    }

    private void methodProcess(List<ApiDocMethodModel> apiDocMethodModelList, Method method) {
        ApiDocMethodModel methodModel = new ApiDocMethodModel();
        ApiDocReturnModel apiDocReturnModel = new ApiDocReturnModel();
        Type genericReturnType = method.getGenericReturnType();
        methodModel.setApiDocReturnModel(apiDocReturnModel);

        Class<?>[] exceptionTypes = method.getExceptionTypes();
        String methodName = method.getName();
        methodModel.setMethodName(methodName);
        methodModel.setExceptionTypes(exceptionTypes);
        // mateData first
        LinkedList<ApiDocParamModel> paramModels = methodModel.getApiDocParamModels();
        Parameter[] parameters = method.getParameters();
        Type[] genericParameterTypes = method.getGenericParameterTypes();


        for (int c= 0;c<parameters.length;c++) {
            ApiDocParamModel apiDocParamModel = new ApiDocParamModel();
            Parameter parameter = parameters[c];

            if(parameter.isNamePresent()){
                apiDocParamModel.setName(parameter.getName());
            }

            Type type = genericParameterTypes[c];

            if(type instanceof ParameterizedTypeImpl){
                apiDocParamModel.setClassType(((ParameterizedTypeImpl) type).getRawType());
                apiDocParamModel.setActualType(((ParameterizedTypeImpl) type).getActualTypeArguments());
            }

            apiDocParamModel.setIndex(c);
            apiDocParamModel.setClassType(parameter.getClass());

            apiDocParamModel.setTypeName(parameter.getType().getTypeName());
            apiDocParamModel.setSimpleName(parameter.getType().getSimpleName());
            apiDocParamModel.setClazzPackage(parameter.getType().getPackage().getName());

            c++;
            paramModels.add(apiDocParamModel);
        }


        Annotation[] annotations = method.getDeclaredAnnotations();
        for (Annotation annotation : annotations) {
            AbstractParser abstractParser = REGISTERED_PARSER.get(annotation.getClass());
            if (abstractParser != null) {
                abstractParser.parser(methodModel, annotation);
            }
        }


        apiDocMethodModelList.add(methodModel);
    }


    private Set<BeanDefinition> find(String basePackage) {
        Set<BeanDefinition> candidates = new LinkedHashSet<>();
        try {
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    resolveBasePackage(basePackage) + '/' + this.resourcePattern;
            Resource[] resources = new PathMatchingResourcePatternResolver().getResources(packageSearchPath);
            for (Resource resource : resources) {
                if (resource.isReadable()) {
                    try {
                        MetadataReader metadataReader = new CachingMetadataReaderFactory().getMetadataReader(resource);
                        ScannedGenericBeanDefinition sbd = new ScannedGenericBeanDefinition(metadataReader);
                        sbd.setResource(resource);
                        sbd.setSource(resource);
                        candidates.add(sbd);
                    } catch (Throwable ex) {
                        throw new BeanDefinitionStoreException(
                                "Failed to read candidate component class: " + resource, ex);
                    }
                }
            }
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("I/O failure during classpath scanning", ex);
        }
        return candidates;
    }

    static String resolveBasePackage(String basePackage) {
        return ClassUtils.convertClassNameToResourcePath(new StandardEnvironment().resolveRequiredPlaceholders(basePackage));
    }

}
