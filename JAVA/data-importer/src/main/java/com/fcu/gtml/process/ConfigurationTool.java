package com.fcu.gtml.process;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConfigurationTool {
    private static final String CONFIGPATH = "config/";
    private static final String WEBMETADATA = "WebMetaData.properties";
    private static final String WEBMETADATA_PATH = CONFIGPATH + WEBMETADATA;
    
    static class WebMetaDataInfo {
        private static final Logger L = LogManager.getLogger(WebMetaDataInfo.class);
        private static final String CONFIG_SEQ = "webMetaData.seq";
        private static PropertiesConfiguration CONFIG;
        
        public static void save() {
            try {
                CONFIG.save(WEBMETADATA);
            } catch (ConfigurationException e) {
                L.error("Save ConfigurationException:{}", e);
            }
        }
        
        public static synchronized PropertiesConfiguration getConfig() {
            if (CONFIG == null) {
                try {
                    PropertiesConfiguration config = new PropertiesConfiguration(WEBMETADATA_PATH);
                    CONFIG = config;
                } catch (ConfigurationException e) {
                    L.error("getConfig ConfigurationException:{}", e);
                }
            }
            return CONFIG;
        }
        
        public static void setConfig(int seq) {
            getConfig().setProperty(CONFIG_SEQ, seq);
        }

        public static int getSeq() {
            return getConfig().getInt(CONFIG_SEQ);
        }
    }
}
