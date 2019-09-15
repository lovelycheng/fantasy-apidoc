package com.treefinance.hound.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chengtong
 * @date 2019-09-09 19:13
 */
@Setter
@Getter
public class ApiDocParamModel extends AbstractApiDocModel {

    String description;

    /**
     * 类型信息 genericParamType.actualTypeArguments
     */
    Class classType;


    Class[] actualType;
    /**
     * Name of the parameter.
     */
    String name;

    /**
     * nullAble
     */
    boolean nullAble = false;

    int index;

}
