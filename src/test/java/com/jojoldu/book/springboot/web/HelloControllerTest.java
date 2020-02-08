package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.SecurityConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//테스트를 진행할 때 JUnit에 내장된 실행자 외에 다른 실행자를 실행시킨다.
//스프링 부트 테스트와 JUnit 사이에 연결자 역할을 한다.
@RunWith(SpringRunner.class)
//여러 스프링 테스트 어노테이션 중 Web(Spring MVC)에 집중 할 수 있는 어노테이션이다.
//선언할 경우 @Controller, @ControllerAdvice 등을 사용 할 수 있다.
//단 @Service, @Component, @Repository등은 사용할 수 없다.
//여기서는 컨트롤러만 사용하기에 선언을 하였다.
//WebMvcTest Annotation 붙이시면 대응되는 Controller class도 넣어주셔야 합니다.
@WebMvcTest(controllers = HelloController.class,
            excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
        classes = SecurityConfig.class)
})
public class HelloControllerTest {

    //스프링이 관리하는 Bean을 주입 받는다.
    @Autowired
    //웹 API를 테스트할 떄 사용한다. 스프링 MVC 테스트의 시작점이다.
    //이 클래스를 통해 HTTP GET, POST등에 대한 API 테스트를 할 수 있다.
    private MockMvc mvc;

    @WithMockUser(roles = "USER")
    @Test
    public void hello가_리턴한다() throws Exception {
        String hello = "hello";

        //MockMvc를 통해 /hello 주소로 HTTP API 테스트를 진행 할 수 있다.
        //체이닝이 지원되어 아래와 같이 여러 검증 기능을 이어서 선언할 수 있다.
        mvc.perform(get("/hello"))
                //mvc.perform의 HTTP Header의 Status를 검증한다.
                .andExpect(status().isOk())
                //mvc.perform 응답 본문의 내용을 검증한다.
                .andExpect(content().string(hello));
    }

    @WithMockUser(roles = "USER")
    @Test
    public void helloDto가_리턴한다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(
                get("/hello/dto")
                        //param API 테스트할 떄 사용될 요청 파라미터를 설정한다.
                        //단 값은 String 만 허용이 된다.
                        // 그래서 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경을 해야 한다.
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                        .andExpect(status().isOk())
                        //JsonPath JSON응답값을 필드별로 검증 할 수 있는 메소드이다.
                        //$를 기준으로 필드명을 명시한다.
                        //여기서는 name, amount를 검증한다.
                        .andExpect(jsonPath("$.name", is(name)))
                        .andExpect(jsonPath("$.amount", is(amount)));
    }
}
