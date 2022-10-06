package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardDAO {
    int insertBoard(BoardVO bvo);

    List<BoardVO> selectBoard(int snum, String fkey, String fval);

    BoardVO selectOneBoard(String bno);

    int deleteOneBoard(String bno);

    int selectCountBoard(String fkey, String fval);

    int updateBoard(BoardVO bvo);
}
