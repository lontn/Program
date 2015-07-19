package com.fcu.gtml.persistence;

public interface StudentEventMapper {

    public <T> void batchInsert(T log);
}
