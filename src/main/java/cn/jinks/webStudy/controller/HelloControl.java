package cn.jinks.webStudy.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/hello")
public class HelloControl {

    @RequestMapping("home")
    public String sayHello(){
        return "hello";
    }
}
