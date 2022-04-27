package com.SevenSenders.demo.service.domain;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class StripService {

    public StripsDto sortingByDate (StripsDto stripsDto){
        List<StripDto> strips = stripsDto.getStrips();
        Collections.sort(strips,StripDto.Comparators.Year);
        stripsDto.setStrips(strips);
        return stripsDto;
    }
}
