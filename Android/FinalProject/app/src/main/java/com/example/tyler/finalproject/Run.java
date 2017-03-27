package com.example.tyler.finalproject;

import java.util.Date;

public class Run {

    protected int rank;
    protected String name;
    protected String time;
    protected Date submittedDate;

    protected String runLink;

    public Run(int rank, String name, long seconds, Date submittedDate, String runLink) {

        this.rank = rank;
        this.name = name;

        long hours = seconds / 3600;
        seconds -= hours * 3600;
        long minutes = seconds / 60;
        seconds -= minutes * 60;

        time = hours + ":";
        if (minutes < 10)
            time += "0" + minutes + ":";
        else
            time += minutes + ":";

        if (seconds < 10)
            time += "0" + seconds;
        else
            time += seconds;

        this.submittedDate = submittedDate;
        this.runLink = runLink;
    }
}
