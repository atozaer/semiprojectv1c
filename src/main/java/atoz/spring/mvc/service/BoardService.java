package atoz.spring.mvc.service;

import atoz.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardService {
    boolean newWrite(BoardVO bvo);

    List<BoardVO> readBoard(int snum, String fkey, String fval);

    BoardVO readOneBoard(String bno);

    int deleteBoard(String bno);

    int updateBoard(String bno, String title, String contents);

    int readCountBoard(String fkey, String fval);
}
