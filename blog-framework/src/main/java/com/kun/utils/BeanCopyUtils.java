package com.kun.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Desc:
 * User:Administrator
 * Date:2022-11-18 22:13
 */
public class BeanCopyUtils {

    public BeanCopyUtils() {
    }

    /**
     * 拷贝单个bean对象
     * @param source 要拷贝的对象
     * @param clazz  目标对象类型
     * @return
     * @param <V>
     */
    public static <V> V copyBean(Object source, Class<V> clazz) {
        V v = null;
        try {
            v = clazz.newInstance();
            BeanUtils.copyProperties(source,v);
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return v;
    }

    /**
     * 拷贝多个存于list中的bean对象
     * @param list 要拷贝的list
     * @param clazz 目标对象类型
     * @return
     * @param <O>
     * @param <V>
     */
    public static <O, V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }
}
