package com.fcu.gtml.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fcu.gtml.domain.StudentPlayVideoTrainData;
import com.fcu.gtml.domain.StudentVideoPlayVideo;

public interface StudentTrainDataMapper {
    public List<StudentVideoPlayVideo> listStudentPlayVideoUser();
    
    public List<StudentVideoPlayVideo> listStudentPlayVideoByKey(@Param("userName") String userName, @Param("code") String code);
    
    public int getOpenResponseCount(@Param("userName") String userName);
    
    public void batchInsertByPlayVideoTrainData(StudentPlayVideoTrainData data);
    
    public List<StudentPlayVideoTrainData> listStudentPlayVideoTrainData();
}
