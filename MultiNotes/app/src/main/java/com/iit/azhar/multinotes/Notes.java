package com.iit.azhar.multinotes;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;

/**
 * Created by Azhar on 10-02-2018.
 */

public class Notes implements Serializable {

    private String lastUpdated;
    private String noteTitle;
    private String noteText;

    public Notes () {

    }

    public Notes(String lastUpdated, String noteTitle, String noteText) {
        this.lastUpdated = lastUpdated;
        this.noteTitle = noteTitle;
        this.noteText = noteText;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(String lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteText() {
        return noteText;
    }

    public void setNoteText(String noteText) {
        this.noteText = noteText;
    }

    @Override
    public String toString() {
        return "Notes{" +
                "lastUpdated='" + lastUpdated + '\'' +
                ", noteTitle='" + noteTitle + '\'' +
                ", noteText='" + noteText + '\'' +
                '}';
    }


}
