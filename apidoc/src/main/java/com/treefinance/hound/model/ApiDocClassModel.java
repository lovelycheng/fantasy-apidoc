package com.treefinance.hound.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chengtong
 * @date 2019-09-09 19:12
 */
@Setter
@Getter
public class ApiDocClassModel extends AbstractApiDocModel{

    /**
     * 方法信息
     */
    List<ApiDocMethodModel> methodModels;

}
