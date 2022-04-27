package com.SevenSenders.demo.controller;

import com.SevenSenders.demo.service.domain.StripDto;
import com.SevenSenders.demo.service.domain.StripService;
import com.SevenSenders.demo.service.domain.StripsDto;
import com.rometools.rome.feed.atom.*;
import com.rometools.rome.feed.rss.Channel;
import com.rometools.rome.feed.rss.Description;
import com.rometools.rome.feed.rss.Image;
import com.rometools.rome.feed.rss.Item;
import com.rometools.rome.feed.synd.*;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping()
public class StripController {

    @Autowired
    StripService stripService;

    private StripsDto stripsDto= new StripsDto();


    @GetMapping("/start")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLaststrip() {
        String stripUri = "https://xkcd.com/info.0.json";
        RestTemplate restTemplate = new RestTemplate();
        StripDto strip = restTemplate.getForObject(stripUri, StripDto.class);
        StripsDto stripsDto=getLastStrips(strip.getNum());
        return stripsDto;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public StripsDto getLastStrips(@PathVariable int stripNum){
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
        return stripService.sortingByDate(stripsDto);
    }

    @GetMapping(path = "/rss")
    @ResponseBody
    public String getFeed () throws FeedException, IOException {
        SyndFeedInput input = new SyndFeedInput();
        URL feedSource = new URL("http://feeds.feedburner.com/PoorlyDrawnLines");
        SyndFeed feed = input.build(new XmlReader(feedSource));
        return new SyndFeedOutput().outputString(feed);
    }
}
