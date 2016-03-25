package com.fcu.gtml.process.oer.domain;

public class OERClassification {
    public enum TYPE {
        Humanities(1, "Humanities"), Application(2, "Application"), History_geography(3,"History_geography")
        ,Scial(4, "Scial"), Natural(5, "Natural"), Medical(6, "Medical"), Other(7, "Other");
        private int value;
        private String word;
        
        private TYPE(int value, String word) {
            this.value = value;
            this.word = word;
        }
     
        public int getValue() {
            return this.value;
        }
        
        public String getWord() {
            return this.word;
        }
    }
}
