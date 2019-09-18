package tech.fantasy.apidoc.model;

import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Type;

/**
 * @author chengtong
 * @date 2019-09-09 19:13
 */
@Setter
@Getter
public class ApiDocParamModel extends AbstractApiDocModel {

    String description;


    Type[] types;

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
