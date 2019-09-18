package tech.fantasy.apidoc.model;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengtong
 * @date 2019-09-09 19:21
 */
@Setter
@Getter
public abstract class AbstractApiDocModel implements ApiDocModelInterface{

    public static List<Class> NORMAL_CONTAINER = Lists.newArrayList(Map.class,List.class, Set.class);

    /**
     * 类名
     */
    String simpleName;

    /**
     * 类型名字
     */
    String typeName;

    /**
     * 所属包名
     * */
    String clazzPackage ;

    /**
     * 描述
     */
    String description;

}
