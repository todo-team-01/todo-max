package org.codesquad.todo.domain.history;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

@Repository
public class HistoryRepository {
	private final NamedParameterJdbcTemplate jdbcTemplate;

	public HistoryRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(jdbcTemplate);
	}

	public Long save(History history) {
		String sql = "INSERT INTO history (content, created_at, is_deleted) VALUES (:content, :createdAt, :isDeleted)";
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(sql, new BeanPropertySqlParameterSource(history), keyHolder);
		return keyHolder.getKey().longValue();
	}

	public List<History> findAll() {
		String sql = "SELECT id, content, created_at, is_deleted FROM history WHERE is_deleted = FALSE ORDER BY id DESC";
		return jdbcTemplate.query(sql, HISTORY_ROW_MAPPER);
	}

	public int deleteAll() {
		String sql = "UPDATE history SET is_deleted = TRUE";
		return jdbcTemplate.update(sql, Map.of());
	}

	private static final RowMapper<History> HISTORY_ROW_MAPPER = (rs, rowNum) ->
		new History(rs.getLong("id"), rs.getString("content"),
			rs.getTimestamp("created_at").toLocalDateTime(), rs.getBoolean("is_deleted"));
}
