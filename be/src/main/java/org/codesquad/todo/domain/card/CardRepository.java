package org.codesquad.todo.domain.card;

import java.util.Map;
import java.util.Optional;

import javax.sql.DataSource;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class CardRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public static final String DELETE_CARD_SQL = "DELETE FROM card WHERE id = :id";
	public static final String CARD_ID_COLUMN = "id";

	public CardRepository(DataSource dataSource) {
		jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public Card save(Card card) {
		String sql = "INSERT INTO card(title, content, column_id, member_id) VALUES(:title, :content, :columnId, :memberId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(card);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return card.createInstanceWithId(keyHolder.getKey().longValue());
	}

	public Boolean isExist(Long id) {
		String sql = "SELECT EXISTS(SELECT 1 FROM card WHERE id = :id)";
		return jdbcTemplate.queryForObject(sql, Map.of("id", id), Boolean.class);
	}

	public Optional<Card> findById(Long id) {
		String sql = "SELECT id, title, content, column_id, member_id, prev_card_id FROM card WHERE id = :id";
		return Optional.ofNullable(
			DataAccessUtils.singleResult(jdbcTemplate.query(sql, Map.of("id", id), CARD_ROW_MAPPER)));
	}

	public int update(Card card) {
		String sql = "UPDATE card "
			+ "SET title = :title, "
			+ "content = :content, "
			+ "column_id = :columnId "
			+ "member_id = :memberId "
			+ "prev_card_id = :prevCardId "
			+ "WHERE id = :id";
		return jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(card));
	}

	public int delete(Long id) {
		return jdbcTemplate.update(DELETE_CARD_SQL, Map.of(CARD_ID_COLUMN, id));
	}

	private static final RowMapper<Card> CARD_ROW_MAPPER = (rs, rowNum) ->
		new Card(rs.getLong("id"), rs.getString("title"), rs.getString("content"),
			rs.getLong("column_id"), rs.getLong("member_id"), rs.getLong("prev_card_id"));
}

