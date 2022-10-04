package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.BoardVO;

import java.util.List;

public interface BoardDAO {
    int insertBoard(BoardVO bvo);

    List<BoardVO> selectBoard(int snum);

    BoardVO selectOneBoard(String bno);

    void deleteOneBoard(String bno);

    void updateOneBoard(String bno);
}
