package com.fcu.gtml.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.junit.Test;

import com.fcu.gtml.SupportSpringTest;
import com.fcu.gtml.process.DataSyncer;
import com.fcu.gtml.process.SyncProcessor_OER_WebMetaData;
import com.fcu.gtml.process.Syncer_ProductData;
import com.fcu.gtml.process.oer.domain.OERClassification;
import com.fcu.gtml.process.oer.domain.WebMetadata;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class WebMetadataServiceTest extends SupportSpringTest {
    private static final Logger L = LogManager.getLogger();
    @Resource private WebMetadataService service;

    @Test
    public void test() {
        List<WebMetadata> list = service.listWebMetadata(0);
        System.out.println(list.size());
        
        if (OERClassification.TYPE.Humanities.getWord().contains("Human")) {
            System.out.println("YES");
        } else {
            System.out.println("NO");
        }
    }

    @Test
    public void propertyTest() {
        try {
            PropertiesConfiguration config = new PropertiesConfiguration("config/usergui.properties");
            String color = config.getString("colors.background");
            int width = config.getInt("window.width");
            System.out.println("width:"+width);
            config.setProperty("colors.foreground111", "#555555");
            config.save();
        } catch (ConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            System.out.println("e" + e);
        }
    }
    
}
