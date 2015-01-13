package org.hadoop;

import java.io.IOException;
import java.net.URL;

//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

public class PerformanceTest {
    private static final Logger L = LoggerFactory.getLogger(PerformanceTest.class);

    @Test
    public void test() {
        logMemoryStatus();
    }
    private void logMemoryStatus() {
        Runtime runtime = Runtime.getRuntime();
        String maxMemory = String.format("%,d M", runtime.maxMemory() / 1000000);
        String totalMemory = String.format("%,d M", runtime.totalMemory() / 1000000);
        String freeMemory = String.format("%,d M", runtime.freeMemory() / 1000000);

        L.info("Memory: [Max : " + maxMemory + "]" + " [Total : " + totalMemory + "]" + " [Free : " + freeMemory + "]");
    }

}
