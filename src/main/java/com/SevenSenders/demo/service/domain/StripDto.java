package com.SevenSenders.demo.service.domain;

import lombok.Data;

import java.util.Comparator;

@Data
public class StripDto implements Comparable<StripDto>{
    private int num;
    private String link;
    private String title;
    private String img;
    private String year;


    public StripDto() {
    }

    public StripDto(String link, String title, String img, String year) {
        this.num = num;
        this.link = link;
        this.title = title;
        this.img = img;
        this.year = year;
    }

    @Override
    public int compareTo(StripDto o) {
        return Comparators.Year.compare(this,o);
    }

    public static class Comparators {

        public static Comparator<StripDto> Year = new Comparator<StripDto>() {
            @Override
            public int compare(StripDto o1, StripDto o2) {
                return o1.year.compareTo(o2.year);
            }
        };
    }
}

