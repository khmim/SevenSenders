package com.SevenSenders.demo.controller;

import com.SevenSenders.demo.service.domain.ComicDto;
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
    public StripsDto getLastComic() {
        String comicUri = "https://xkcd.com/info.0.json";
        RestTemplate restTemplate = new RestTemplate();
        ComicDto comic = restTemplate.getForObject(comicUri, ComicDto.class);
        StripsDto stripsDto=getLastComics(comic.getNum());
        return stripsDto;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLastComics(@PathVariable int comicNum){
        List<StripDto> strips = new ArrayList<>();
        for(int i =0 ; i<9;i++){
            comicNum--;
            String comicUri = "https://xkcd.com/{comicNum}/info.0.json";
            Map<String, String> comicParams = new HashMap<String, String>();
            comicParams.put("comicNum", String.valueOf(comicNum));
            RestTemplate restTemplate = new RestTemplate();
            ComicDto comic = restTemplate.getForObject(comicUri, ComicDto.class, comicParams);
            StripDto stripDto = modelMapper.map(comic, StripDto.class);
            strips.toArray(new StripDto[]{stripDto});
        }
        stripsDto.setStrips(strips);
        return stripsDto;
    }

}
