package com.fcu.gtml.process.utils;

import java.util.List;


import com.fcu.gtml.service.WordNetService;

public class WordNetTools {

    public static final List<String> listWordNet(WordNetService wordNetService, String word) {
        List<String> wordNetHyponym = wordNetService.listHyponymNouns(word);
        List<String> wordNetHypernym = wordNetService.listHypernymNouns(word);
        wordNetHyponym.addAll(wordNetHypernym);
        return wordNetHyponym;
    }
}
