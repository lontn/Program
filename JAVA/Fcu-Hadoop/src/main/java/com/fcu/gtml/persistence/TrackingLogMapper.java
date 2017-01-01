package com.fcu.gtml.persistence;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.fcu.gtml.domain.EnrollmentEventsLog;
import com.fcu.gtml.domain.NavigationEventsLog;
import com.fcu.gtml.domain.PDFOutlineToggled;
import com.fcu.gtml.domain.PDFPageScrolled;
import com.fcu.gtml.domain.PDFThumbnailNavigated;
import com.fcu.gtml.domain.PDFThumbnailsToggled;
import com.fcu.gtml.domain.TextBookByBook;
import com.fcu.gtml.domain.TrackingLog;
import com.fcu.gtml.domain.TrackingLogContext;
import com.fcu.gtml.domain.VideoByHideTranscript;
import com.fcu.gtml.domain.VideoByLoadVideo;
import com.fcu.gtml.domain.VideoByPauseVideo;
import com.fcu.gtml.domain.VideoByPlayVideo;
import com.fcu.gtml.domain.VideoBySeekVideo;
import com.fcu.gtml.domain.VideoByShowTranscript;
import com.fcu.gtml.domain.VideoBySpeedChangeVideo;
import com.fcu.gtml.domain.VideoByStopVideo;
import com.fcu.gtml.domain.PDFDisplayScaled;

public interface TrackingLogMapper {
    public void batchInsert(TrackingLog log);

    public int batchInsertBak(TrackingLog log);
    /**
     * Insert seek_video Log
     * @param seek
     */
    public void batchInsertBySeekVideo(VideoBySeekVideo seek);

    /**
     * Insert load_video Log
     * @param loadVideo
     */
    public void batchInsertByLoadVideo(VideoByLoadVideo loadVideo);

    /**
     * Insert play_video Log
     * @param playVideo
     */
    public void batchInsertByPlayVideo(VideoByPlayVideo playVideo);

    /**
     * Insert pause_video Log
     * @param pauseVideo
     */
    public void batchInsertByPauseVideo(VideoByPauseVideo pauseVideo);

    /**
     * Insert stop_video Log
     * @param stopVideo
     */
    public void batchInsertByStopVideo(VideoByStopVideo stopVideo);

    /**
     * Insert speed_change_video Log
     * @param speedChangeVideo
     */
    public void batchInsertBySpeedChangeVideo(VideoBySpeedChangeVideo speedChangeVideo);

    /**
     * Insert hide_transcript Log
     * @param hideTranscript
     */
    public void batchInsertByHideTranscript(VideoByHideTranscript hideTranscript);

    /**
     * Insert show_transcript Log
     * @param showTranscript
     */
    public void batchInsertByShowTranscript(VideoByShowTranscript showTranscript);

    /**
     * Insert EnrollmentEvents Log
     * @param enrollment
     */
    public void batchInsertByEnrollmentEventsLog(EnrollmentEventsLog enrollment);

    /**
     * Insert NavigationEvents Log
     * @param navigation
     */
    public void batchInsertByNavigationEventsLog(NavigationEventsLog navigation);

    /**
     * TextBookByBook
     * @param book
     */
    public void batchInsertByTextBookByBook(TextBookByBook book);

    /**
     * insert PDFThumbnailsToggled
     * @param pdf
     */
    public void batchInsertByPDFThumbnailsToggled(PDFThumbnailsToggled pdf);

    /**
     * insert PDFThumbnailNavigated
     * @param pdf
     */
    public void batchInsertByPDFThumbnailNavigated(PDFThumbnailNavigated pdf);

    /**
     * insert PDFPageScrolled
     * @param pdf
     */
    public void batchInsertByPDFPageScrolled(PDFPageScrolled pdf);

    public void batchInsertByTrackingLogContext(TrackingLogContext tContext);
    public List<TrackingLog> listTrackingLogByEventType(@Param("eventType") String eventType);

    public void batchInsertByPDFDisplayScaled(PDFDisplayScaled pdf);

    public void batchInsertByPDFOutlineToggled(PDFOutlineToggled pdf);
}
