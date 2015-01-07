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

    @Test
    public void JsonTest() {
        String url = "http://sid.geo.com.tw/FUCGoogleMapWebAPI/api/FCU/Get";
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
        //mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); //ignore 未知的屬性
        try {
            FcuFood fcu = mapper.readValue(new URL(url), FcuFood.class);
            System.out.println(fcu);
            System.out.println("Name:"+fcu.getName());
            System.out.println("Address:"+fcu.getAddress());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
