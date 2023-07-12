package com.elai.common.utils;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * list工具类
 *
 * @author elai
 */
public class ListUtil
{
    public static List<Integer> rangeList(List<Integer> list1,List<Integer> list2){
        //IntStrean.range(0, Math.min(list1.size(), list2.size()))
        List<Integer> result = IntStream.range(0, list1.size()) .map(i -> list1.get(i) + list2.get(i))
                .boxed() .collect(Collectors.toList());
        return result;
    }
}
