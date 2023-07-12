package org.codesquad.todo.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

public class DatabaseCleaner {

	private JdbcTemplate jdbcTemplate;
	private List<String> tableNames;

	public DatabaseCleaner(DataSource dataSource) {
		init(dataSource);
	}

	private void init(DataSource dataSource) {
		try {
			tableNames = new ArrayList<>();
			jdbcTemplate = new JdbcTemplate(dataSource);
			ResultSet resultSet = dataSource.getConnection()
				.getMetaData()
				.getTables(null, "PUBLIC", null, new String[] {"TABLE"});

			while (resultSet.next()) {
				tableNames.add(resultSet.getString("TABLE_NAME"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void execute() {
		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY FALSE");
		tableNames.forEach(name -> {
			jdbcTemplate.execute(String.format("TRUNCATE TABLE %s", name));
			jdbcTemplate.execute(String.format("ALTER TABLE %s ALTER COLUMN ID RESTART WITH 1", name));
		});

		jdbcTemplate.execute("SET REFERENTIAL_INTEGRITY TRUE");
	}
}
