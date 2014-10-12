package org.fcu.green.crawler.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class TextBookInfo {
    private String title;
    private String identifier;
    private String creator;
    private String publisher;
    private String language;
    private String subjectClassification;
    private String subjectOther;
    private String rightUri;
    private String syshyperlink;
    private String year; //date
    private String introduction; //abstract
    private String doi;

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getIdentifier() {
        return identifier;
    }
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public String getPublisher() {
        return publisher;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public String getLanguage() {
        return language;
    }
    public void setLanguage(String language) {
        this.language = language;
    }
    public String getSubjectClassification() {
        return subjectClassification;
    }
    public void setSubjectClassification(String subjectClassification) {
        this.subjectClassification = subjectClassification;
    }
    public String getSubjectOther() {
        return subjectOther;
    }
    public void setSubjectOther(String subjectOther) {
        this.subjectOther = subjectOther;
    }
    public String getRightUri() {
        return rightUri;
    }
    public void setRightUri(String rightUri) {
        this.rightUri = rightUri;
    }
    public String getSyshyperlink() {
        return syshyperlink;
    }
    public void setSyshyperlink(String syshyperlink) {
        this.syshyperlink = syshyperlink;
    }
    public String getYear() {
        return year;
    }
    public void setYear(String year) {
        this.year = year;
    }
    public String getIntroduction() {
        return introduction;
    }
    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }
    public String getDoi() {
        return doi;
    }
    public void setDoi(String doi) {
        this.doi = doi;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
    
    
}
