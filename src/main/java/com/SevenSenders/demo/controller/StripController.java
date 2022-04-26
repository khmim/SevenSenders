package com.SevenSenders.demo.controller;

import com.SevenSenders.demo.model.Strip;
import com.SevenSenders.demo.service.domain.StripDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping()
public class StripController {

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public StripDto getStrips(@PathVariable("carName") String carName) {
        return carTypeService.getCarType(carName);
    }

}
