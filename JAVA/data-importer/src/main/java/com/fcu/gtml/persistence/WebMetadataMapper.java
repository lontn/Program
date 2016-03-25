package com.fcu.gtml.persistence;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.fcu.gtml.process.oer.domain.WebMetadata;

public interface WebMetadataMapper {
    public static final String BU_SEQ = "seq";

    public List<WebMetadata> listWebMetadata(Map<String, Object> params);
}
