package com.fcu.gtml.process.oer.domain;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class WebMetadata {
    private int seq;
    private int parentSeq;
    private String title;
    private String text;
    private String uri;
    private Date createTime;
    private String type;

    public WebMetadata() {
    }

    public WebMetadata(int seq, int parentSeq, String title, String text,
            String uri, Date createTime) {
        this.seq = seq;
        this.parentSeq = parentSeq;
        this.title = title;
        this.text = text;
        this.uri = uri;
        this.createTime = createTime;
    }

    public int getSeq() {
        return seq;
    }
    public void setSeq(int seq) {
        this.seq = seq;
    }
    public int getParentSeq() {
        return parentSeq;
    }
    public void setParentSeq(int parentSeq) {
        this.parentSeq = parentSeq;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }
}
