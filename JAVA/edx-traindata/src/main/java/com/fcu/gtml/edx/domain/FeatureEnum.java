package com.fcu.gtml.edx.domain;

public enum FeatureEnum {
    SHOWTRANSCRIPT("show_transcript"),
    HIDETRANSCRIPT("hide_transcript"),
    LOADVIDEO("load_video")
    ,PLAYVIDEO("play_video")
    ,PAUSEVIDEO("pause_video")
    ,SEEKVIDEO("seek_video")
    ,STOPVIDEO("stop_video")
    ,SPEEDCHANGEVIDEO("speed_change_video")
    ,VIDEOHIDECCMENU("video_hide_cc_menu")
    ,VIDEOSHOWCCMENU("video_show_cc_menu")
    ,SEQGOTO("seq_goto"),SEQNEXT("seq_next"),SEQPREV("seq_prev"),PAGECLOSE("page_close");
    private String name;
    
    private FeatureEnum(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
