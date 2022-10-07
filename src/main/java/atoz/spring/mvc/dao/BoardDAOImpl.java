package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.BoardVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {

    @Autowired
    private SqlSession sqlSession;

    @Override
    public int insertBoard(BoardVO bvo) {

        return sqlSession.insert("board.insertBoard", bvo);
    }

    @Override
    public List<BoardVO> selectAllBoard(int snum, String fkey, String fval) {

        Map<String, Object> params = new HashMap<>();
        params.put("fkey", fkey);
        params.put("fval", "%" + fval + "%");
        params.put("snum", snum);

        return sqlSession.selectList("board.selectAllBoard", params);
    }

    @Override
    public BoardVO selectOneBoard(String bno) {

        sqlSession.update("board.updateViewsBoard", bno);

        return sqlSession.selectOne("board.selectOneBoard", bno);
    }

    @Override
    public int deleteOneBoard(String bno) {

        return sqlSession.delete("board.deleteOneBoard", bno);
    }

    @Override
    public int selectCountBoard(String fkey, String fval) {

        Map<String, Object> params = new HashMap<>();
        params.put("fkey", fkey);
        params.put("fval", "%" + fval + "%");

        return sqlSession.selectOne("board.selectCountBoard", params);
    }

    @Override
    public int updateBoard(BoardVO bvo) {

        return sqlSession.update("board.updateBoard", bvo);
    }

}
