package com.SevenSenders.demo.service;


import com.SevenSenders.demo.controller.StripController;
import com.SevenSenders.demo.service.model.StripDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StripController.class)
public class StripServiceTest {

    @Autowired
    private MockMvc mvc;

    StripDto RECORD_1 =new StripDto("","Woodpecker","https://imgs.xkcd.com/comics/woodpecker.png","2009");
    StripDto RECORD_2 =new StripDto("https://poorlydrawnlines.com/comic/try-something/","Try Something","https://poorlydrawnlines.com/?p=8445","Fri Apr 08 22:46:11 IRDT 2022");


    private static String feedUrl="http://feeds.feedburner.com/PoorlyDrawnLines";
    private static String stripUri = "https://xkcd.com/{stripNum}/info.0.json";
    private static String sUri = "https://xkcd.com/info.0.json";

    @Test
    public void getStrips_callUrl_withSuccess() throws Exception {
        mvc.perform(
                        get("http://feeds.feedburner.com/PoorlyDrawnLines")
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk());
    }

}
