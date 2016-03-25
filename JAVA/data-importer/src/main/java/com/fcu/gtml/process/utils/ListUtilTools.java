package com.fcu.gtml.process.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ListUtilTools {

    public static final <T extends String> Map<String, T> listToMap(Map<String, T> map, List<T> items) {
        for (T t : items) {
            T objT = map.get(t);
            if (objT == null) {
                map.put(t, t);
            }
        }
        return map;
    }
}
