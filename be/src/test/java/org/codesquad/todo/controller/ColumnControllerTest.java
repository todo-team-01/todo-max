package org.codesquad.todo.controller;

import static org.assertj.core.api.Assertions.*;
import static org.codesquad.todo.controller.util.ColumnFixture.*;

import java.util.List;

import javax.sql.DataSource;

import org.codesquad.todo.controller.dto.ColumnSaveRequestDTO;
import org.codesquad.todo.controller.dto.ColumnUpdateRequestDTO;
import org.codesquad.todo.util.DatabaseCleaner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;

import io.restassured.RestAssured;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ColumnControllerTest {

	@LocalServerPort
	private int port;

	@Autowired
	private DataSource dataSource;

	@BeforeEach
	void setUp() {
		RestAssured.port = port;
		DatabaseCleaner databaseCleaner = new DatabaseCleaner(dataSource);
		databaseCleaner.execute();
	}

	@DisplayName("모든 컬럼 및 카드들을 조회한다.")
	@Test
	void showColumnWithCards() {
		// given
		var 오늘_할일 = 컬럼_생성(new ColumnSaveRequestDTO("오늘 할일"));
		var 내일_할일 = 컬럼_생성(new ColumnSaveRequestDTO("내일 할일"));

		// when
		var response = 컬럼_카드_전체_조회_요청();

		// then
		List<String> names = response.jsonPath().getList("columnName", String.class);

		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(names).containsAnyOf(오늘_할일.getName(), 내일_할일.getName());
	}

	@DisplayName("컬럼을 저장한다.")
	@Test
	void saveColumn() {
		// given
		var 중요한_일 = new ColumnSaveRequestDTO("중요한 일");

		// when
		var response = 컬럼_생성_요청(중요한_일);

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
		assertThat(response.jsonPath().getString("name")).isEqualTo("중요한 일");
	}

	@DisplayName("컬럼을 수정한다.")
	@Test
	void updateColumn() {
		// given
		var 중요한_일 = 컬럼_생성(new ColumnSaveRequestDTO("중요한 일"));
		var 업데이트할_컬럼 = new ColumnUpdateRequestDTO("오늘 할일");

		// when
		var response = 컬럼_수정_요청(업데이트할_컬럼, 중요한_일.getId());

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}

	@DisplayName("컬럼을 삭제한다.")
	@Test
	void deleteColumn() {
		// given
		var 중요한_일 = 컬럼_생성(new ColumnSaveRequestDTO("중요한 일"));

		// when
		var response = 컬럼_삭제_요청(중요한_일.getId());

		// then
		assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value());
	}
}
