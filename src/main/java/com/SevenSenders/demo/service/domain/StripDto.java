package com.SevenSenders.demo.service.domain;

import lombok.Data;

import java.util.Date;

@Data
public class StripDto {
    private int num;
    private String link;
    private String title;
    private String img;
    private Date year;

}
