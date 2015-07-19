package com.fcu.gtml.persistence;

import com.fcu.gtml.domain.StudentVideoHideTranscript;
import com.fcu.gtml.domain.StudentVideoLoadVideo;
import com.fcu.gtml.domain.StudentVideoPauseVideo;
import com.fcu.gtml.domain.StudentVideoPlayVideo;
import com.fcu.gtml.domain.StudentVideoSeekVideo;
import com.fcu.gtml.domain.StudentVideoShowTranscript;
import com.fcu.gtml.domain.StudentVideoSpeedChangeVideo;
import com.fcu.gtml.domain.StudentVideoStopVideo;

public interface StudentVideoEventMapper {

    /**
     * Student Event hide_transcript
     * @param student
     */
    public void batchInsertByHideTranscript(StudentVideoHideTranscript log);

    /**
     * Student Event load_video
     * @param log
     */
    public void batchInsertByLoadVideo(StudentVideoLoadVideo log);
    
    /**
     * Student Event pause_video
     * @param log
     */
    public void batchInsertByPauseVideo(StudentVideoPauseVideo log);
    
    /**
     * Student Event play_video
     * @param log
     */
    public void batchInsertByPlayVideo(StudentVideoPlayVideo log);
    
    /**
     * Student Event seek_video
     * @param log
     */
    public void batchInsertBySeekVideo(StudentVideoSeekVideo log);
    
    /**
     * Student Event show_transcript
     * @param log
     */
    public void batchInsertByShowTranscript(StudentVideoShowTranscript log);
    
    /**
     * Student Event speed_change_video
     * @param log
     */
    public void batchInsertBySpeedChangeVideo(StudentVideoSpeedChangeVideo log);
    
    /**
     * Student Event stop_video
     * @param log
     */
    public void batchInsertByStopVideo(StudentVideoStopVideo log);
}
