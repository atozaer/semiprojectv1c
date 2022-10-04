package atoz.spring.mvc.service;

import atoz.spring.mvc.dao.BoardDAO;
import atoz.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("bsrv")
public class BoardServiceImpl implements BoardService{

    @Autowired
    private BoardDAO bdao;

    @Override
    public boolean newWrite(BoardVO bvo) {
        boolean isInsert = false;

        if (bdao.insertBoard(bvo) > 0) {
            isInsert = true;
        }

        return isInsert;
    }

    @Override
    public List<BoardVO> readBoard(int snum) {

        return bdao.selectBoard(snum);
    }

    @Override
    public BoardVO readOneBoard(String bno) {

        return bdao.selectOneBoard(bno);
    }

    @Override
    public void deleteBoard(String bno) {

        bdao.deleteOneBoard(bno);
    }

    @Override
    public void updateBoard(String bno) {
        bdao.updateOneBoard(bno);
    }
}
