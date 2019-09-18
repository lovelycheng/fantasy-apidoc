package tech.fantasy.apidoc.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tech.fantasy.apidoc.annotations.EnableRpcApiDoc;

/**
 * @author chengtong
 * @date 2019-09-18 08:46
 */
@SpringBootApplication
@EnableRpcApiDoc(zkAddress = "192.168.5.241:2181",scanBasePackageClasses = {EnhancementExample.class,
        ReferenceExample.class})
public class Application {

    public static void main(String... args){
        SpringApplication.run(Application.class,args);
    }

}
