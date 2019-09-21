# fantasy-apidoc

一个分布式的接口文档管理系统。

我个人非常反感后端来手写文档、但凡是能自动化的东西就应该自动化，程序员本身产出就很高，花时间在手写文档上无异于慢性自杀。

如果你苦于上游不写注释、不写返回信息、不写错误信息、疯狂给你xxxException、null、""。。。em..有点夸张这个样可能先得看看how to design api之类的东西。
无论如何希望你能获得帮助。


## usage

@EnableRpcApiDoc 在程序入口类上加上这个注解

它提供了几个字段:

 1. 一个是扫描路径或者扫描的类，一般来说，是程序需要对外提供服务的类所在的包。也可以是自定义的注解解析器所在的包。
 2. zkAddress zookeeper的地址
 3. 本身默认会注册自己的对swagger2的注解支持；

```java

/**
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@AnnotationScan
@AppInfo
@EnableSwagger2
@ComponentScan(basePackages = "tech.fantasy.apidoc.processor.swagger",includeFilters =
        {@ComponentScan.Filter(type = FilterType.ANNOTATION,value = AnnotationParser.class)})
public @interface EnableRpcApiDoc {

    /**
    * 需要解析的包
     */
    @AliasFor(annotation = AnnotationScan.class, attribute = "basePackages")
    String[] scanBasePackages() default {};

    /**
    * 需要解析的类
     */
    @AliasFor(annotation = AnnotationScan.class, attribute = "basePackageClasses")
    Class<?>[] scanBasePackageClasses() default {};

    /**
     * a short basic description of this application, will displayed on a web page
     * 对程序的简短的介绍
     */
    @AliasFor(annotation = AppInfo.class, attribute = "title")
    String title() default "";
    /**
     * the name of this application, will displayed on a web page
     * 服务所在程序的名字
     */
    @AliasFor(annotation = AppInfo.class, attribute = "name")
    String name() default "";

    /**
     * the owner of this application
     * 所有者
     * */
    @AliasFor(annotation = AppInfo.class, attribute = "owner")
    String owner() default "";
    /**
     * the owner of this application
     * zookeeper的地址
     * */
    @AliasFor(annotation = AppInfo.class, attribute = "zkAddress")
    String zkAddress() default "";
}
```

@AnnotationParser 注解解析器，如果你想自己扩展自己的注解和解析器，可以用这个
```java
@AnnotationParser(type = ApiParam.class,name = "apiImplicitParamParser")
public class ApiImplicitParamParser extends AbstractParser {

    protected ApiImplicitParamParser(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    @Override
    protected void parser(ApiDocModelInterface model, Annotation annotation) {
        // 最终的解析出来的数据还是对 java 类或者方法（参数、返回值）的描述。所以这里是提供了一个的抽象，这个抽象可以是任何值，
        //比方说你的注解是注解在类上的，那么这里的 model instanceof  ApiDocModel 如果是在方法上 model instanceof  ApiDocMethodModel,
    }
}
```

如果说你不想用本身提供的几个抽象类的实现类，你也可以自己写，不过你需要一个前端给你写json数据格式的解析。


