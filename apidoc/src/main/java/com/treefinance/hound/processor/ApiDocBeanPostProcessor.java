package com.treefinance.hound.processor;

import com.alibaba.fastjson.JSON;
import com.treefinance.hound.model.ApiDocClassModel;
import com.treefinance.hound.zookeeper.ZookeeperClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.util.List;


/**
 * @author chengtong
 * @date 2019-09-08 12:07
 */
@Slf4j
public class ApiDocBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter implements
        ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static final String PREFIX_PATH = "/apidoc";

    private Integer count = 0;

    private String name;
    private String title;
    private String owner;
    private String zkAddress;

    public ApiDocBeanPostProcessor(String name,String title,String owner,String zkAddress){
        this.name =name;
        this.title =title;
        this.owner =owner;
        this.zkAddress =zkAddress;
    }



    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {

        log.info(beanName,count);

        if(bean instanceof AbstractParser) {

            AbstractParser parser = (AbstractParser) bean;

            log.debug("register parser ,{}", parser.getAnnotationClass());

            ApiDocParser.REGISTERED_PARSER.put(parser.getAnnotationClass(), parser);
        }
        count++;
        return super.postProcessAfterInstantiation(bean, beanName);
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {

        if(bean instanceof ApiDocParser){

            ApiDocParser apidocParser = (ApiDocParser) bean;

            List<ApiDocClassModel> apiDocClassModelList = apidocParser.parse();

            log.info(name);
            log.info(title);
            log.info(owner);
            log.info(zkAddress);

            ZookeeperClient.CLIENT.init(zkAddress);

            // TODO: 2019-09-09 /apidoc/com.treefinance.hound.accountdata.apidoc.${version}/${package}/${class}

            try {

                for(ApiDocClassModel apiDocClassModelModel:apiDocClassModelList){
                    String fullName = apiDocClassModelModel.getTypeName();
                    //File.pathSeparator :
                    String path = PREFIX_PATH + "/" + fullName;

                    ZookeeperClient.CLIENT.create(path, JSON.toJSONString(apiDocClassModelModel));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }finally {
                ZookeeperClient.CLIENT.destory();
            }
        }
        return super.postProcessAfterInitialization(bean, beanName);
    }
}
