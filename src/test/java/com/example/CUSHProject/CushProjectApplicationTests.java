package com.example.CUSHProject;

import com.example.CUSHProject.dto.BoardDto;
import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.repository.BoardRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@WebMvcTest
public class CushProjectApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void _테스트() {

		//Given
		//테스트들을 위해 데이터들을 준비하는 과정
		//given
		String title = "테스트 제목";
		String content = "테스트 본문";
		String writer = "테스트 계정";

		BoardDto boardDto = BoardDto.builder()
				.title(title)
				.content(content)
				.writer(writer)
				.build();

		BoardEntity boardEntity = boardDto.toEntity();


		Long savedId = boardEntity.getId();
		String url = "http://localhost:8080/api/notice/delete?id="+savedId;

		HttpEntity<BoardEntity> savedEntity = new HttpEntity<>(boardEntity);
		//When
		//실제로 용청을 하거나 싱행을 해보는 과정
		ResponseEntity<Long> responseEntity = restTemplate.exchange(url, HttpMethod.DELETE, savedEntity, Long.class);

		//Then
		//실행한 결과를 검증하는 과정
		assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
		assertThat(responseEntity.getBody()).isGreaterThan(0L);

		List<BoardEntity> deleted = boardRepository.findAll();
		assertThat(deleted).isEmpty();
	}

}
