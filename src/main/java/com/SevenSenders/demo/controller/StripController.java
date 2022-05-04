package com.SevenSenders.demo.controller;

import com.SevenSenders.demo.service.domain.StripDto;
import com.SevenSenders.demo.service.domain.StripService;
import com.SevenSenders.demo.service.domain.StripsDto;
import com.rometools.rome.io.FeedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping()
public class StripController {

    @Autowired
    StripService stripService;


    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLastComics() throws FeedException, IOException {
        StripsDto stripsDto= new StripsDto();
        List<StripDto> strips = new ArrayList<StripDto>();
        strips.addAll((stripService.getFeed().getStrips()));
        strips.addAll(stripService.getLaststrip().getStrips());
        stripsDto.setStrips(strips);
        return stripsDto;
    }



}
