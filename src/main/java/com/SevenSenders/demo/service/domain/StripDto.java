package com.SevenSenders.demo.service.domain;

import lombok.Data;

import java.util.Comparator;
import java.util.Date;

@Data
public class StripDto implements Comparable<StripDto>{
    private int num;
    private String link;
    private String title;
    private String img;
    private String year;

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

