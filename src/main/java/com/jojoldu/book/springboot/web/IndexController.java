package com.jojoldu.book.springboot.web;

import com.jojoldu.book.springboot.config.auth.dto.SessionUser;
import com.jojoldu.book.springboot.service.PostsService;
import com.jojoldu.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;
    private final HttpSession httpSession;

    @GetMapping("/")
    public String index(Model model) {
        //Model
        //서버 템플릿 엔진에서 사용할 수 있는 객체를 저장할 수 있다.
        //여기서는 postsService.findAllDesc()로 가져온 결과를 posts로 index.mustache에 전달한다.
        model.addAttribute("posts", postsService.findAllDesc());

        //CustomOAuth2UserService에서 로그인 성공시 세션에 SessionUser를 저장하도록 구성
        //로그인 성공시 httpSession.getAttribute("user")에서 값을 가져올 수 있다.
        SessionUser user = (SessionUser) httpSession.getAttribute("user");

        //세션에 저장된 값이 있을 때만 model에 userName으로 등록.
        //세션에 저장된 값이 없으면 model에 값이 없으니 로그인 버튼이 보이게 된다.
        if(user != null) {
            model.addAttribute("userName", user.getName());
        }
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);

        return "posts-update";
    }

}
