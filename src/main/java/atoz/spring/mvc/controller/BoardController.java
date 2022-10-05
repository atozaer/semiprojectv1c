package atoz.spring.mvc.controller;

import atoz.spring.mvc.service.BoardService;
import atoz.spring.mvc.vo.BoardVO;
import jdk.internal.util.xml.impl.Parser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@Controller
public class BoardController {

    // bean 클래스로 정의한 경우 @Autowired 어노테이션 생략 가능
    @Autowired
    private BoardService bsrv;

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
    public String getList(Model model, HttpSession session, String cpg) {
        LOGGER.info("getList 호출!!");

        int perpage = 25;
        if (cpg == null || cpg.equals("")) {
            cpg = "1";
        }
        int cpage = Integer.parseInt(cpg);
        if (cpage < 0) {
            cpage = 1;
        }
        int snum = (cpage - 1) * perpage;
        int stpgn = ((cpage - 1) / 10) * 10 + 1;

        model.addAttribute("pages", bsrv.readCountBoard());
        model.addAttribute("boardList", bsrv.readBoard(snum));
        model.addAttribute("stpgn", stpgn);
        model.addAttribute("cpg", Integer.parseInt(cpg));

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

    @PostMapping("/write")
    public String postWrite(BoardVO bvo, HttpSession session) {
        LOGGER.info("postWrite 호출!!");

        bsrv.newWrite(bvo);

        return "redirect:/list?cpg=1";
    }

    @GetMapping("/del")
    public ModelAndView getDelete(ModelAndView mv, String bno) {

        bsrv.deleteBoard(bno);
        mv.setViewName("redirect:/list");

        return mv;
    }

    @GetMapping("/upd")
    public ModelAndView getUpdate(ModelAndView mv, String bno) {

        bsrv.updateBoard(bno);
        mv.setViewName("redirect:/list");

        return mv;
    }
}
