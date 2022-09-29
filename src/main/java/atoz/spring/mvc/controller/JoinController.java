package atoz.spring.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JoinController {

    @GetMapping("/join")
    public String getJoin(){

        return "join/join";
    }

    @GetMapping("/login")
    public String getLogin(){

        return "join/login";
    }

    @GetMapping("/myinfo")
    public String getMyInfo(){

        return "join/myinfo";
    }
}
