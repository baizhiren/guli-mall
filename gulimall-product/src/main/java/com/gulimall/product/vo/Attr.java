/**
  * Copyright 2019 bejson.com 
  */
package com.gulimall.product.vo;

import lombok.Data;

import java.lang.reflect.Array;
import java.util.*;

/**
 * Auto-generated: 2019-11-26 10:50:34
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
@Data
public class Attr {

    private Long attrId;
    private String attrName;
    private String attrValue;

    public static void main(String[] args) {
        HashSet<List<Integer>> set = new HashSet<>();
        set.add(Arrays.asList(1, 2));
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.sort(Comparator.naturalOrder());


        set.add(list);
        System.out.println(set.size());

    }

}