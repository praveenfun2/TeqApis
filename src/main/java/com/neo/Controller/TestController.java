package com.neo.Controller;

import com.neo.Service.ImageService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static com.neo.Constants.FRONT_END;
import static com.neo.Constants.LOCALHOST;

@RestController
public class TestController {

    @RequestMapping("/admin")
    public String get(@RequestAttribute(required = false) String id) {
        return id;
    }


    @RequestMapping("/hi")
    public String get() {
        return "Hi......";
    }
}
