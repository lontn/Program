package com.fcu.gtml.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fcu.gtml.domain.StudentLog;

public interface StudentLogMapper {

    public void batchInsert(StudentLog log);

    public List<StudentLog> listStudentLogByEventType(@Param("eventType") String eventType);
}
