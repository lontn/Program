package com.fcu.gtml.service;

import java.util.List;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fcu.gtml.persistence.WordNetMapper;

@Service
public class WordNetService {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private WordNetMapper wordNetMapper;

    /**
     * 上義詞
     * @param word
     * @return
     */
    public List<String> listHypernymNouns(String word) {
        List<String> listHypernyms = wordNetMapper.listHypernymNouns(word);
        return listHypernyms;
    }

    /**
     * 下義詞
     * @param word
     * @return
     */
    public List<String> listHyponymNouns(String word) {
        List<String> listHyponyms = wordNetMapper.listHyponymNouns(word);
        return listHyponyms;
    }
}
