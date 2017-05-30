package com.fcu.gtml.edx.domain;

public class FeatureData extends TheData {
    private int userId;
    private int showTranscript;
    private int hideTranscript;
    private int loadVideo;
    private int playVideo;
    private int pauseVideo;
    private int seekVideo;
    private int stopVideo;
    private int speedChangeVideo;
    private int videoHideCCMenu;
    private int videoShowCCMenu;
    private int seqGoto;
    private int seqNext;
    private int seqPrev;
    private int pageClose;
    private int label;

    public FeatureData() {}

    public FeatureData(int userId, int showTranscript, int hideTranscript, int loadVideo, int playVideo, int pauseVideo,
            int seekVideo, int stopVideo, int speedChangeVideo, int videoHideCCMenu, int videoShowCCMenu, int seqGoto,
            int seqNext, int seqPrev, int pageClose, int label) {
        this.userId = userId;
        this.showTranscript = showTranscript;
        this.hideTranscript = hideTranscript;
        this.loadVideo = loadVideo;
        this.playVideo = playVideo;
        this.pauseVideo = pauseVideo;
        this.seekVideo = seekVideo;
        this.stopVideo = stopVideo;
        this.speedChangeVideo = speedChangeVideo;
        this.videoHideCCMenu = videoHideCCMenu;
        this.videoShowCCMenu = videoShowCCMenu;
        this.seqGoto = seqGoto;
        this.seqNext = seqNext;
        this.seqPrev = seqPrev;
        this.pageClose = pageClose;
        this.label = label;
    }



    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getShowTranscript() {
        return showTranscript;
    }
    public void setShowTranscript(int showTranscript) {
        this.showTranscript = showTranscript;
    }
    public int getHideTranscript() {
        return hideTranscript;
    }
    public void setHideTranscript(int hideTranscript) {
        this.hideTranscript = hideTranscript;
    }
    public int getLoadVideo() {
        return loadVideo;
    }
    public void setLoadVideo(int loadVideo) {
        this.loadVideo = loadVideo;
    }
    public int getPlayVideo() {
        return playVideo;
    }
    public void setPlayVideo(int playVideo) {
        this.playVideo = playVideo;
    }
    public int getPauseVideo() {
        return pauseVideo;
    }
    public void setPauseVideo(int pauseVideo) {
        this.pauseVideo = pauseVideo;
    }
    public int getSeekVideo() {
        return seekVideo;
    }
    public void setSeekVideo(int seekVideo) {
        this.seekVideo = seekVideo;
    }
    public int getStopVideo() {
        return stopVideo;
    }
    public void setStopVideo(int stopVideo) {
        this.stopVideo = stopVideo;
    }
    public int getSpeedChangeVideo() {
        return speedChangeVideo;
    }
    public void setSpeedChangeVideo(int speedChangeVideo) {
        this.speedChangeVideo = speedChangeVideo;
    }
    public int getVideoHideCCMenu() {
        return videoHideCCMenu;
    }
    public void setVideoHideCCMenu(int videoHideCCMenu) {
        this.videoHideCCMenu = videoHideCCMenu;
    }
    public int getVideoShowCCMenu() {
        return videoShowCCMenu;
    }
    public void setVideoShowCCMenu(int videoShowCCMenu) {
        this.videoShowCCMenu = videoShowCCMenu;
    }
    public int getSeqGoto() {
        return seqGoto;
    }
    public void setSeqGoto(int seqGoto) {
        this.seqGoto = seqGoto;
    }
    public int getSeqNext() {
        return seqNext;
    }
    public void setSeqNext(int seqNext) {
        this.seqNext = seqNext;
    }
    public int getSeqPrev() {
        return seqPrev;
    }
    public void setSeqPrev(int seqPrev) {
        this.seqPrev = seqPrev;
    }
    public int getPageClose() {
        return pageClose;
    }
    public void setPageClose(int pageClose) {
        this.pageClose = pageClose;
    }
    public int getLabel() {
        return label;
    }
    public void setLabel(int label) {
        this.label = label;
    }
    
}
