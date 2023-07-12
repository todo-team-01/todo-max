package org.codesquad.todo.domain.card;

import java.util.Map;

import javax.sql.DataSource;

import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
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

	public int delete(Long id) {
		return jdbcTemplate.update(DELETE_CARD_SQL, new MapSqlParameterSource(Map.of(CARD_ID_COLUMN, id)));
	}

	public Card save(Card card) {
		String sql = "INSERT INTO card(title, content, column_id, member_id, prev_card_id) VALUES(:title, :content, :columnId, :memberId, :prevCardId)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		SqlParameterSource parameters = new BeanPropertySqlParameterSource(card);
		jdbcTemplate.update(sql, parameters, keyHolder);
		return card.createInstanceWithId(keyHolder.getKey().longValue());
	}

}

