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
    public List<BoardVO> readBoard(int snum, String fkey, String fval) {


        return bdao.selectAllBoard(snum,fkey,fval);
    }

    @Override
    public BoardVO readOneBoard(String bno) {

        return bdao.selectOneBoard(bno);
    }

    @Override
    public int deleteBoard(String bno) {

        return bdao.deleteOneBoard(bno);
    }



    @Override
    public int readCountBoard(String fkey, String fval) {

        return bdao.selectCountBoard(fkey,fval);
    }

    @Override
    public boolean modifyBoard(BoardVO bvo) {
        boolean isUpdate = false;

        if (bdao.updateBoard(bvo)>0) {
            isUpdate = true;
        }

        return isUpdate;
    }
}
