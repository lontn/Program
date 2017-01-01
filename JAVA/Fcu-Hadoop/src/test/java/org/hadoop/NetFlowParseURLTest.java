package org.hadoop;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
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

    @Test
    public void StringTokenizer() throws IOException {
        String value = "{\"username\": \"*hidden*\", \"host\": \"energyedu.openedu.tw\"} \n"
                + "{\"username\": \"*hidden*\", \"event_type\": \"page_close\"}";
        FileReader fr = new FileReader("D:/ResourceDataTest/edx/privacy_tracking.log-20150831-1441030621");
        BufferedReader br = new BufferedReader(fr);
        StringTokenizer itr = new StringTokenizer(br.readLine(), "\n");
        while(itr.hasMoreTokens()) {
            System.out.println("itr:" + itr.nextToken());
        }
    }
    @Test
    public void LongTime() {
        DateTime dt = new DateTime("2015-08-31 20:11:42.907", DateTimeZone.UTC);
        System.out.println("aaa:" + dt.toDate().getTime());
    }
}
