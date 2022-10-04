package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.MemberService;
import atoz.spring.mvc.vo.MemberVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class MemberController {

    @Autowired
    private MemberService msrv;

    //로그 유형 : trace, debug, info, warn, error
    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    @GetMapping("/join")
    public String getJoin() {

        return "join/join";
    }

    @PostMapping("/join")
    public String postJoin(MemberVO mvo) {
        LOGGER.info("postJoin 호출!! {}", mvo);

        // 회원정보 저장
        if (msrv.newMember(mvo)) {
            LOGGER.info("회원가입 성공!!");
        }


        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin() {
        LOGGER.info("getLogin 호출!!");

        return "join/login";
    }

    @PostMapping("/login") // 로그인 처리
    public String postLogin(MemberVO mvo, HttpSession session) {
        LOGGER.info("postLogin 호출!!");

        String returnPage = "join/lgnfail";

        if (msrv.checkLogin(mvo)) {
            session.setAttribute("m", mvo); // 회원정보를 세션에 저장

            returnPage = "redirect:/myinfo";
        }


        return returnPage;
    }

    @GetMapping("/logout")
    public String getLogout(HttpSession session) {
        session.invalidate();

        return "redirect:/";
    }


    @GetMapping("/myinfo")
    public String getMyInfo(Model model) {
        model.addAttribute("mvo", msrv.readOneMember());
        return "join/myinfo";
    }
}
