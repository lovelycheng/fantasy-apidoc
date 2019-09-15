package com.treefinance.hound.model;


import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.LinkedList;

/**
 * @author chengtong
 * @date 2019-09-09 19:12
 */
@Setter
@Getter
public class ApiDocMethodModel extends AbstractApiDocModel {

    /**
     * 返回值信息
     */
    private ApiDocReturnModel apiDocReturnModel;

    /**
     * 参数信息
     */
    private LinkedList<ApiDocParamModel> apiDocParamModels = Lists.newLinkedList();

    /**
     * 方法名字
     */
    private String methodName;

    /**
     * 描述
     */
    private String description;

    /**
     * 异常信息
     */
    private Class<?>[] exceptionTypes;


}
