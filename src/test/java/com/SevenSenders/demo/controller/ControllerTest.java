package com.SevenSenders.demo.controller;


import com.SevenSenders.demo.controller.StripController;
import com.SevenSenders.demo.service.domain.StripDto;
import com.SevenSenders.demo.service.domain.StripsDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.hamcrest.core.Is.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest(value = StripController.class)
public class ControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private StripController stripController;

    StripDto RECORD_1 =new StripDto("","Woodpecker","https://imgs.xkcd.com/comics/woodpecker.png","2009");
    StripDto RECORD_2 =new StripDto("https://poorlydrawnlines.com/comic/try-something/","Try Something","https://poorlydrawnlines.com/?p=8445","Fri Apr 08 22:46:11 IRDT 2022");

    @Test
    public void givenStrips_whenGetStrips_thenStatus200() throws Exception {
        List<StripDto> records = new ArrayList<>(Arrays.asList(RECORD_1, RECORD_2));
        given(stripController.getLastComics()).willReturn(records);
        mvc.perform(
                        get("/")
                        .contentType(APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andExpect(jsonPath("$", hasSize(2)))
                        .andExpect(jsonPath("$[0].year", is("2009")));
    }

}
