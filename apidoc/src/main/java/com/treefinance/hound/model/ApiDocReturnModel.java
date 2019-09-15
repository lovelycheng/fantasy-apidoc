package com.treefinance.hound.model;

import com.google.common.collect.Lists;
import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chengtong
 * @date 2019-09-09 19:13
 */
@Setter
@Getter
public class ApiDocReturnModel extends AbstractApiDocModel{

    List<Pair<Integer,String>> codeMsgPairs =  Lists.newArrayList(new Pair<>(0,"success"));

    Class<?> response;

    private Class returnType;

}
