package com.SevenSenders.demo.controller;

import com.SevenSenders.demo.service.domain.StripDto;
import com.SevenSenders.demo.service.domain.StripDto;
import com.SevenSenders.demo.service.domain.StripsDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping()
public class StripController {

    @Autowired
    private ModelMapper modelMapper;

    private StripsDto stripsDto= new StripsDto();


    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLaststrip() {
        String stripUri = "https://xkcd.com/info.0.json";
        RestTemplate restTemplate = new RestTemplate();
        StripDto strip = restTemplate.getForObject(stripUri, StripDto.class);
        StripsDto stripsDto=getLaststrips(strip.getNum());
        return stripsDto;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLaststrips(@PathVariable int stripNum){
        List<StripDto> strips = new ArrayList<>();
        for(int i =0 ; i<9;i++){
            stripNum--;
            String stripUri = "https://xkcd.com/{stripNum}/info.0.json";
            Map<String, String> stripParams = new HashMap<String, String>();
            stripParams.put("stripNum", String.valueOf(stripNum));
            RestTemplate restTemplate = new RestTemplate();
            StripDto strip = restTemplate.getForObject(stripUri, StripDto.class, stripParams);
            strips.add(strip);
        }
        stripsDto.setStrips(strips);
        return stripsDto;
    }

}
