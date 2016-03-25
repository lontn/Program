package com.fcu.gtml.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.process.utils.ListUtilTools;

public class WordNetServiceTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource
    private WordNetService service;
    
    @Test
    public void HypernymNouns() {
        List<String> lists = service.listHypernymNouns("Humanities");
        for (String word : lists) {
            System.out.println(word);
        }
    }

    @Test
    public void HyponymNouns() {
        List<String> lists = service.listHyponymNouns("Humanities");
        String word = "Human Relations by Laura Portolese-Dias addresses all of the critical topics to obtain career success as they relate to professional relationships.";
        IKAnalyzer analyzer = new IKAnalyzer();
        List<String> iklist = new ArrayList<>();
        try {
            iklist = analyzer.split(word);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Map<String, String> ikMap = ListUtilTools.listToMap(new HashMap<String, String>(), iklist);
        for (String w : lists) {
            if (ikMap.containsKey(w)) {
                System.out.println(ikMap.get(w));
                System.out.println("YES");
                break;
            } else {
                System.out.println("NO");
            }
        }
    }
}
