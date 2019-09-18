package tech.fantasy.apidoc.processor;

import tech.fantasy.apidoc.annotations.AnnotationParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.ConstructorArgumentValues;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author chengtong
 * @date 2019-09-08 10:14
 */
@Slf4j
public class ApiAnnotationBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor, EnvironmentAware {

    private Environment environment;
    static final String ANNOTATION_NAME = AnnotationParser.class.getName();
    private final Set<String> packagesToScan;

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public ApiAnnotationBeanDefinitionPostProcessor(Set<String> packagesToScan) {
        this.packagesToScan = packagesToScan;
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        Set<String> resolvedPackagesToScan = resolvePackagesToScan(packagesToScan);

        if (!CollectionUtils.isEmpty(resolvedPackagesToScan)) {

            registerAnnotatedParser(resolvedPackagesToScan,registry);

            BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.rootBeanDefinition(ApiDocParser.class);
            beanDefinitionBuilder.addConstructorArgValue(resolvedPackagesToScan);
            beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition("parser",beanDefinitionBuilder.getBeanDefinition());
        } else {
            log.warn("packagesToScan is empty");
        }
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
    }

    private Set<String> resolvePackagesToScan(Set<String> packagesToScan) {
        Set<String> resolvedPackagesToScan = new LinkedHashSet<String>(packagesToScan.size());
        for (String packageToScan : packagesToScan) {
            if (StringUtils.hasText(packageToScan)) {
                String resolvedPackageToScan = environment.resolvePlaceholders(packageToScan.trim());
                resolvedPackagesToScan.add(resolvedPackageToScan);
            }
        }
        return resolvedPackagesToScan;
    }

    private void registerAnnotatedParser(Set<String> paths,BeanDefinitionRegistry registry){
        paths.add("com.treefinance.hound.processor.swagger");
        AnnotationFilteredClassPathScanner scanner = new AnnotationFilteredClassPathScanner(registry,
                AnnotationParser.class,environment);
        for(String path:paths){
            if(!StringUtils.hasText(path)){
                continue;
            }

            String resolvedPath = ApiDocParser.resolveBasePackage(path);

            Set<BeanDefinition> beanDefinitions = scanner.findCandidateComponents(resolvedPath);

            if(beanDefinitions.isEmpty()){
                continue;
            }

            for(BeanDefinition beanDefinition :beanDefinitions){

                if(beanDefinition instanceof ScannedGenericBeanDefinition){

                    ScannedGenericBeanDefinition scannedGenericBeanDefinition =
                            (ScannedGenericBeanDefinition) beanDefinition;


                    Map<String,Object> annotationAttributes =
                            scannedGenericBeanDefinition.getMetadata().getAnnotationAttributes(ANNOTATION_NAME);

                    if (annotationAttributes == null){
                        continue;
                    }
                    String beanName = (String) annotationAttributes.get("name");
                    Class annotationClass = (Class) annotationAttributes.get("type");

                    ConstructorArgumentValues constructorArgumentValues =
                            scannedGenericBeanDefinition.getConstructorArgumentValues();

                    constructorArgumentValues.addIndexedArgumentValue(0,new ConstructorArgumentValues.ValueHolder(annotationClass,"Class", "annotationClass"));

                    registry.registerBeanDefinition(beanName,scannedGenericBeanDefinition);
                }


            }
        }


    }

}
