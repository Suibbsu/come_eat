package kr.or.comeeat.board.model.vo;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

@Component
public class BoardCommentRowMapper implements RowMapper<BoardComment>{

	@Override
	public BoardComment mapRow(ResultSet rs, int rowNum) throws SQLException {
		BoardComment bc = new BoardComment();
		bc.setBoardCommentContent(rs.getString("board_comment_content"));
		bc.setBoardCommentDate(rs.getString("board_comment_date"));
		bc.setBoardCommentNo(rs.getInt("board_comment_no"));
		bc.setBoardCommentRef(rs.getInt("board_comment_ref"));
		bc.setBoardCommentWriter(rs.getString("board_comment_writer"));
		bc.setBoardRef(rs.getInt("board_ref"));
		bc.setIsLike(rs.getInt("is_like"));
		bc.setLikeCount(rs.getInt("like_count"));
		return bc;
	}

}
