package com.fcu.gtml.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

class TheData {

    static String trim(String str) {
        return str != null ? str.trim() : null;
    }

    static String[] trim(String[] strs) {
        if (strs == null || strs.length == 0)
            return strs;
        for (int i = 0; i < strs.length; i++) {
            strs[i] = trim(strs[i]);
        }
        return strs;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

}
