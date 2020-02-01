package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//컨트롤러를 JSON을 반환하는 컨트롤러로 만들어 준다.
//@ResponseBody를 각 메소드마다 선언한 것과 동일한 것.
@RestController
public class HelloController {

    //HTTP Method인 Get의 요청을 받을 수 있는 API를 만들어 준다.
    //@RequestMapping(method = RequestMethod.Get) 과 동일한 것.
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/hello/dto")
    //@RequestPatam은 외부에서 API로 넘긴 파라미터를 가지고 오는 어노테이션이다.
    //여기서는 외부에서 name이란 이름을 넘긴 파라미터를 메소드 파라미터 name(String name)으로 저장하게 된다.
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }

}