package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    @Override
    public int insertBoard(BoardVO bvo) {
        String sql = "insert into board(title, userid, contents) values (?,?,?)";

        Object[] params = new Object[]{
                bvo.getTitle(), bvo.getUserid(), bvo.getContents(),
        };

        return jdbcTemplate.update(sql, params);
    }
}
