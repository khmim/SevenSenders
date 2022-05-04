package com.SevenSenders.demo.service.domain;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.*;

@Service
public class StripService {

    private StripsDto stripsDto= new StripsDto();

    public StripsDto sortingByDate (StripsDto stripsDto){
        List<StripDto> strips = stripsDto.getStrips();
        Collections.sort(strips,StripDto.Comparators.Year);
        stripsDto.setStrips(strips);
        return stripsDto;
    }

    public StripsDto getFeed () throws FeedException, IOException {
        List<StripDto> strips = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL("http://feeds.feedburner.com/PoorlyDrawnLines")));
        System.out.println(feed);
        List<SyndEntry> comics= feed.getEntries();
        for(int i =0 ; i<9;i++){
            StripDto strip= new StripDto();
            strip.setYear(comics.get(i).getPublishedDate().toString());
            strip.setImg(comics.get(i).getUri());
            strip.setLink(comics.get(i).getLink());
            strip.setTitle(comics.get(i).getTitle());
            strips.add(strip);
        }
        stripsDto.setStrips(strips);
        return sortingByDate(stripsDto);
    }

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
        return sortingByDate(stripsDto);
    }
    public StripsDto getLaststrip() {
        String stripUri = "https://xkcd.com/info.0.json";
        RestTemplate restTemplate = new RestTemplate();
        StripDto strip = restTemplate.getForObject(stripUri, StripDto.class);
        StripsDto stripsDto=getLastStrips(strip.getNum());
        return stripsDto;
    }
}
