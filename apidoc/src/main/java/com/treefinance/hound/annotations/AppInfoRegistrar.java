package com.treefinance.hound.annotations;

import com.treefinance.hound.processor.ApiDocBeanPostProcessor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Set;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * @author chengtong
 * @date 2019-09-09 11:46
 */
public class AppInfoRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        registerServiceApiDocBeanPostProcessor(registry,"apidocProcessor",importingClassMetadata);

    }

    private void registerServiceApiDocBeanPostProcessor(BeanDefinitionRegistry registry,String beanName, AnnotationMetadata importingClassMetadata){

        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(AppInfo.class.getName()));

        String name = attributes.getString("name");
        String title = attributes.getString("title");
        String owner = attributes.getString("owner");
        String zkAddress = attributes.getString("zkAddress");


        if(!registry.containsBeanDefinition(beanName)){
            BeanDefinitionBuilder beanDefinitionBuilder = rootBeanDefinition(ApiDocBeanPostProcessor.class);
            beanDefinitionBuilder.addConstructorArgValue(name);
            beanDefinitionBuilder.addConstructorArgValue(title);
            beanDefinitionBuilder.addConstructorArgValue(owner);
            beanDefinitionBuilder.addConstructorArgValue(zkAddress);
            beanDefinitionBuilder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
            registry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        }
    }

}
