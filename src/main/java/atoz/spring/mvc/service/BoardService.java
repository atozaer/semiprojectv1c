package atoz.spring.mvc.service;

import atoz.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardService {
    boolean newWrite(BoardVO bvo);

    List<BoardVO> readBoard();
}
