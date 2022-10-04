package atoz.spring.mvc.dao;

import atoz.spring.mvc.vo.BoardVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;

@Repository("bdao")
public class BoardDAOImpl implements BoardDAO {

    @Autowired  //bean 태그에 정의한 경우 생략가능
    private JdbcTemplate jdbcTemplate;

    private SimpleJdbcInsert simpleJdbcInsert;

    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<BoardVO> boardVORowMapper = BeanPropertyRowMapper.newInstance(BoardVO.class);

    public BoardDAOImpl(DataSource dataSource) {
        simpleJdbcInsert = new SimpleJdbcInsert(dataSource)
                .withTableName("board")
                .usingColumns("title", "userid", "contents");

        namedParameterJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public int insertBoard(BoardVO bvo) {
        SqlParameterSource params = new BeanPropertySqlParameterSource(bvo);

        return simpleJdbcInsert.execute(params);
    }

    @Override
    public List<BoardVO> selectBoard() {
        String sql = " select bno,title,userid,regdate,views from board order by bno desc ";

        return namedParameterJdbcTemplate.query(sql, Collections.emptyMap(), boardVORowMapper);
    }

    @Override
    public BoardVO selectOneBoard(String bno) {
        //본문글에 대한 조회수 증가시키기
        String sql = " update board set views = views + 1 where bno = ? ";
        Object[] param = {bno};
        jdbcTemplate.update(sql, param);

        //본문글 가져오기
        sql = " select * from board where bno = ? ";

        return jdbcTemplate.queryForObject(sql, param, boardVORowMapper);
    }

    @Override
    public void deleteOneBoard(String bno) {
        String sql = " delete from board where bno = ?";

        Object[] param = {
                bno
        };

        jdbcTemplate.update(sql, param);
    }

    @Override
    public void updateOneBoard(String bno) {

    }
}
