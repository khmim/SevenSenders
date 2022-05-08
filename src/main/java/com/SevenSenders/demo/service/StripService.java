package com.SevenSenders.demo.service;

import com.SevenSenders.demo.service.model.StripDto;
import com.SevenSenders.demo.service.model.StripsDto;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class StripService {

    private StripsDto stripsDto= new StripsDto();
    private static String feedUrl="http://feeds.feedburner.com/PoorlyDrawnLines";
    private static String stripUri = "https://xkcd.com/{stripNum}/info.0.json";
    private static String sUri = "https://xkcd.com/info.0.json";



    public StripsDto sortingByDate (StripsDto stripsDto){
        List<StripDto> strips = stripsDto.getStrips();
        Collections.sort(strips,StripDto.Comparators.Year);
        stripsDto.setStrips(strips);
        return stripsDto;
    }

    public StripsDto getFeed () throws FeedException, IOException {
        List<StripDto> strips = new ArrayList<>();
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feed = input.build(new XmlReader(new URL(feedUrl)));
        System.out.println(feed);
        List<SyndEntry> comics= feed.getEntries();
        strips=comics.stream().map((SyndEntry comic) -> new StripDto(comic.getLink(), comic.getTitle(), comic.getUri(), comic.getPublishedDate().toString()))
                .limit(10).collect(Collectors.toList());
        ;
/*        for(int i =0 ; i<10 ;i++){
            StripDto strip= new StripDto();
            strip.setYear(comics.get(i).getPublishedDate().toString());
            strip.setImg(comics.get(i).getUri());
            strip.setLink(comics.get(i).getLink());
            strip.setTitle(comics.get(i).getTitle());
            strips.add(strip);
        }*/

        stripsDto.setStrips(strips);
        return sortingByDate(stripsDto);
    }

    public StripsDto getStrips(int stripNum){
        List<StripDto> strips = new ArrayList<>();
        Map<String, String> stripParams = new HashMap<String, String>();
        RestTemplate restTemplate = new RestTemplate();

/*        Stream.iterate(0, i -> i + 1)
                .limit(10)
                .map(stripNum--)
                .forEach(restTemplate.getForObject(stripUri, StripDto.class,stripParams.put("stripNum", String.valueOf(stripNum))));*/

        for(int i =0 ; i<10 ;i++){
            stripNum--;
            stripParams.put("stripNum", String.valueOf(stripNum));
            StripDto strip = restTemplate.getForObject(stripUri, StripDto.class,stripParams.put("stripNum", String.valueOf(stripNum)))ک
            strips.add(strip);
        }
        stripsDto.setStrips(strips);
        return sortingByDate(stripsDto);
    }
    public StripsDto getLastStrip() {
        RestTemplate restTemplate = new RestTemplate();
        StripDto strip = restTemplate.getForObject(sUri, StripDto.class);
        StripsDto stripsDto= getStrips(strip.getNum());
        return stripsDto;
    }
}
