package org.domain;

public enum ProxyLog {
    LIBRARYINFO("LibraryInfo", 1), LABORATORYINFO("LaboratoryInfo", 2);
    private String familyName;
    private int index;
    public static enum Counters { ROWS, COLS, ERROR, VALID }
    private ProxyLog(String familyName, int index) {
        this.familyName = familyName;
        this.index = index;
    }

    public static String getColumnFamily(int index) {
        for (ProxyLog c : ProxyLog.values()) {
            if (c.getIndex() == index) {
                return c.familyName;
            }
        }
        return null;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
    
    
}
