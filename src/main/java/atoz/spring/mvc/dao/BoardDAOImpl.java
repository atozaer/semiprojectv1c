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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    // 동적질의문
    // 조건에 따라 실행할 질의문의 형태가 바뀌는 것
    // 제목으로 검색 : select * from board where title = ?
    // 작성자로 검색 : select * from board where userid = ?
    // 본문으로 검색 : select * from board where contents = ?
    // => select * from ? where ? = ? (실행 X)
    // 테이블명, 컬럼명은 매개변수화 할 수 없음
    @Override
    public List<BoardVO> selectBoard(int snum, String fkey, String fval) {
        StringBuilder sql = new StringBuilder();
        sql.append(" select bno,title,userid,regdate,views from board ");
        if (fkey.equals("title")) {
            sql.append(" where title = :fval ");
        } else if (fkey.equals("userid")) {
            sql.append(" where userid = :fval ");
        } else if (fkey.equals("contents")) {
            sql.append(" where contents = :fval ");
        }
        sql.append(" order by bno desc limit :snum, 25 ");

        // :로 들어갈 변수 지정가능 namedParameterJdbcTemplate기능

        Map<String, Object> params = new HashMap<>();
        params.put("snum", snum);
        params.put("fval", fval);

        return namedParameterJdbcTemplate.query(sql.toString(), params, boardVORowMapper);
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

    @Override
    public int selectCountBoard(String fkey, String fval) {
        String sql = "select ceil(count(bno)/25) pages from board";

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }
}
