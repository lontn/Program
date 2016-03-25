package com.fcu.gtml.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.fcu.gtml.persistence.WebMetadataMapper;
import com.fcu.gtml.process.oer.domain.WebMetadata;

@Service
public class WebMetadataService {
    private static final Logger L = LogManager.getLogger();
    @Resource private WebMetadataMapper webMetadataMapper;

    public List<WebMetadata> listWebMetadata(int seq) {
        L.info("seq:{}", seq);
        Map<String, Object> params = new HashMap<>();
        params.put(WebMetadataMapper.BU_SEQ, seq);
        return webMetadataMapper.listWebMetadata(params);
    }
}
