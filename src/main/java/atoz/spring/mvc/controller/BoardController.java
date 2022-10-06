package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.BoardService;
import atoz.spring.mvc.utils.RecaptchaUtils;
import atoz.spring.mvc.vo.BoardVO;
import atoz.spring.mvc.vo.MemberVO;
import org.apache.hc.core5.http.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.http.HttpSession;
import java.io.IOException;

@Controller
public class BoardController {

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능
//    @Autowired
//    private BoardService bsrv;
//
//    @Autowired
//    private RecaptchaUtils grcp;
    private BoardService bsrv;
    private RecaptchaUtils grcp;

    @Autowired
    public BoardController(BoardService bsrv, RecaptchaUtils grcp) {
        this.bsrv = bsrv;
        this.grcp = grcp;
    }

    protected Logger LOGGER = LoggerFactory.getLogger(getClass());

    /* 페이징 처리
     * 페이지당 게시물 수 perPage : 25
     * 총 페이지 수 : 전체게시물수 / 페이지당 게시물 수
     * 총 페이지 수 : ceil(getTotalPage / perPage);
     *
     * 페이지별 읽어올 게시글 범위
     * 총 게시글이 55건이라 할때
     * 1page : 1번쨰~25번째, 2page : 26~50, 3page : 51~75, ...
     * ipage : m번째 ~ n번째 게시글 읽어옴
     * m = (i -1) * 25
     *  */

    /*
     * 현재 페이지에 따라 보여줄 페이지 블럭 결정
     * ex) 총 페이지수가 27일때
     * cpg = 1 : 1 2 3 4 5 6 7 8 9 10
     * cpg = 5 : 1 2 3 4 5 6 7 8 9 10
     * cpg = 9 : 1 2 3 4 5 6 7 8 9 10
     * cpg = 11 : 11 12 13 14 15 16 17 18 19 20
     * cpg = 23 : 21 22 23 24 25 26 27 28 29 30
     *
     * cpg = n : ? ?+1 ?+2 ?+3 ... ?+9
     * stpgn = ((cpg - 1) / 10) * 10 + 1
     *
     *  */

    @GetMapping("/list")
    public String getList(Model model, HttpSession session, String cpg, String fkey, String fval) {
        LOGGER.info("getList 호출!!");

        int perpage = 25;
        if (cpg == null || cpg.equals("")) {
            cpg = "1";
        }
        if (fkey == null) {
            fkey = "";
        }
        int cpage = Integer.parseInt(cpg);
        if (cpage < 0) {
            cpage = 1;
        }
        int snum = (cpage - 1) * perpage;
        int stpgn = ((cpage - 1) / 10) * 10 + 1;

        model.addAttribute("pages", bsrv.readCountBoard(fkey, fval));
        model.addAttribute("boardList", bsrv.readBoard(snum, fkey, fval));
        model.addAttribute("stpgn", stpgn);
        model.addAttribute("fqry", "&fkey=" + fkey + "&fval=" + fval);
//        model.addAttribute("cpg", cpage);

        return "board/list";
    }

    @GetMapping("/view")
    public ModelAndView getView(ModelAndView mv, String bno) {
        LOGGER.info("getView 호출!!");

        mv.setViewName("board/view");
        mv.addObject("bvo", bsrv.readOneBoard(bno));

        return mv;
    }

    // 로그인 O -> board/write
    // 로그인 X -> join/login
    @GetMapping("/write")
    public String getWrite(HttpSession session) {
        LOGGER.info("getWrite 호출!!");

        String returnPage = "join/login";

        if (session.getAttribute("mvo") != null) {
            returnPage = "board/write";
        }
        return returnPage;
    }

    // captcha 작동원리
    // captcha 사용시 클라이언트가 생성한 키와
    // 서버에 설정해 둔 (비밀)키등을
    // google의 siteverify에서 비교해서 인증에 성공하면
    // list로 redirect하고, 그렇치 않으면 다시 write로 return함
    // VERIFY_URL = 'https://www.google.com/recaptcha/api/siteverify'
    // 질의를 위한 질의문자열을 작성
    // ?secret=비밀키&response=클라이언트응답키

    @PostMapping("/write")
    public String postWrite(BoardVO bvo, String gcaptcha, RedirectAttributes rda) throws IOException, ParseException {
        String returnPage = "redirect:/write";

        if (grcp.checkCaptcha(gcaptcha)) {
            bsrv.newWrite(bvo);
            returnPage = "redirect:/list?cpg=1";
        } else {
            rda.addFlashAttribute("bvo", bvo);
            rda.addFlashAttribute("msg", "자동가입방지 확인이 실패했어요!");
        }

        return returnPage;
    }

    @GetMapping("/del")
    public String getDelete(HttpSession session, String bno) {
        String returnPage = "redirect:/list?cpg=1";

        if (session.getAttribute("mvo") != null) {
            bsrv.deleteBoard(bno);
        }

        return returnPage;
    }

    @GetMapping("/upd")
    public String getUpdate(HttpSession session, String bno, Model model) {
        String returnPage = "board/update";

        if (session.getAttribute("mvo") == null) {
            returnPage = "redirect:/login";
        } else {
            model.addAttribute("bvo", bsrv.readOneBoard(bno));
        }

        return returnPage;
    }


    @PostMapping("/upd")
    public String postUpdate(BoardVO bvo, HttpSession session) {
        String returnPage = "redirect:/view?bno=" + bvo.getBno();

        if (session.getAttribute("mvo") == null) {
            returnPage = "redirect:/login";
        } else {
            bsrv.modifyBoard(bvo);
        }

        return returnPage;
    }


}
