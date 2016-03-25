package com.fcu.gtml.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface WordNetMapper {

    /**
     * 上義詞
     * @return
     */
    public List<String> listHypernymNouns(@Param("word") String word);

    /**
     * 下義詞
     * @return
     */
    public List<String> listHyponymNouns(@Param("word") String word);
}
