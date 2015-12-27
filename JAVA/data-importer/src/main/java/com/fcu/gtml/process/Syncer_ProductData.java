package com.fcu.gtml.process;

import java.io.File;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.apache.hadoop.conf.Configuration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fcu.gtml.process.oer.domain.OpenEdxLog;

public class Syncer_ProductData {
    private static final Logger L = LogManager.getLogger();
    private Properties prop;
    private Configuration conf;
    private Set<SyncProcessor> processors = new LinkedHashSet<>();
    private Set<Field> processedField = new HashSet<>(); // 處理過的key

    enum Field {
        EDX
    }

    
    Syncer_ProductData(Properties prop, Configuration conf) {
        this.prop = prop;
        this.conf = conf;
    }

    public Properties getProperties() {
        return this.prop;
    }

    public Configuration getConfiguration() {
        return this.conf;
    }

    // process
    public void doSyncProcess() {
        for(SyncProcessor processor : processors) {
            processor.process(this);
        }
    }

    public Syncer_ProductData addSyncProcessor(SyncProcessor... processors) {
        //Arrays.stream(processors).forEach(this.processors::add);
        List<SyncProcessor> listSyncProcessor = Arrays.asList(processors);
        for (SyncProcessor syncProcessor : listSyncProcessor) {
            this.processors.add(syncProcessor);
        }
        return this;
    }

    public void processed(Field... field) {
        List<Field> listField = Arrays.asList(field);
        for (Field f : listField) {
            processedField.add(f);
        }
        //Arrays.stream(field).forEach(processedField::add);
    }

    public interface SyncProcessor {
        void process(Syncer_ProductData syncer);
        List<OpenEdxLog> LoadFile(File file);
    }
}
