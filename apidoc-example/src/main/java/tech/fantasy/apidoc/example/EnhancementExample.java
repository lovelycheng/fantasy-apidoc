package tech.fantasy.apidoc.example;


import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author chengtong
 * @date 2019-09-18 08:32
 */
public interface EnhancementExample {

    /**
     * List 、 Map 、Set的返回值；
     *
     * @param id
     * @return
     */
    List<Integer> testJavaMethod(String id);

    /**
     * List 、 Map 、Set的返回值；
     * list的参数
     *
     * @param ids
     * @return
     */
    Map<Integer, String> testJavaMethod(List<String> ids);


    /**
     * 复杂的参数和返回值
     *
     * @param map
     * @param ids
     * @param index
     * @return
     */
    Set<Integer> testJavaMethod(Map<String, Integer> map, Set<String> ids, List<Integer> index);

}
