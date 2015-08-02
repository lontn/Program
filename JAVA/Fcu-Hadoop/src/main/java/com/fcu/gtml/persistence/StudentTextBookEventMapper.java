package com.fcu.gtml.persistence;

import com.fcu.gtml.domain.StudentTextBook;

public interface StudentTextBookEventMapper {

    /**
     *  Textbook Interaction Events By book
     * @param log
     */
    public void batchInsertByBook(StudentTextBook log);
}
