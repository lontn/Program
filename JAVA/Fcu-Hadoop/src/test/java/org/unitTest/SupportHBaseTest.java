package org.unitTest;

import java.util.Properties;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;

abstract public class SupportHBaseTest {
    protected static Properties prop = new Properties();
    protected static Configuration conf = null;

    static {
        conf = HBaseConfiguration.create();
    }
}
