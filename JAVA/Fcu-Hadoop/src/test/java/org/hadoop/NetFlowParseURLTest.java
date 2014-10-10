package org.hadoop;

import org.junit.Test;
import org.unitTest.SupportHBaseTest;

public class NetFlowParseURLTest extends SupportHBaseTest {

    @Test
    public void parse(){
        String url = "http://www.sciencedirect.com/science/referenceResolution/ajaxRefResol?";
        url = url.replaceAll("http://", "");
        url = url.replaceAll("[/.]", "@");
        String[] xxx = url.split("@");
        System.out.println(url);
        
    }
}
