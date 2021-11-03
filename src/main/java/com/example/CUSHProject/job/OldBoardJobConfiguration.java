package com.example.CUSHProject.job;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Status;
import com.example.CUSHProject.repository.BoardQueryRepository;
import com.example.CUSHProject.repository.BoardRepository;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Slf4j
@AllArgsConstructor
@Configuration
public class OldBoardJobConfiguration {
    private final BoardRepository boardRepository;
    private final BoardQueryRepository boardQueryRepository;
    private final EntityManagerFactory entityManagerFactory;

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private static final int chunkSize = 10;

    @Bean
    public Job oldBoardJob() {
        log.info("**********this is oldBoardJob");
        return jobBuilderFactory.get("oldBoardJob")
                .start(oldBoardJobStep())
                .build();
    }

    @Bean
    public Step oldBoardJobStep() {
        log.info("**********this is oldBoardJobStep");
        return stepBuilderFactory.get("oldBoardJobStep")
                .<BoardEntity, BoardEntity> chunk(chunkSize)
                .reader(oldBoardReader()) //구현해야함 게시물을 올린지 1주일 이상된 보드를 읽어온다
                .processor(oldBoardProcessor()) //구현해야함 판매완료가 아닐경우 ~~~을 수행한다
                .writer(oldBoardWriter()) //구현해야함 처리된 게시물을 DB에 저장한다
                .build();
    }



    @Bean
    @StepScope
    public JpaPagingItemReader<BoardEntity> oldBoardReader() {
        log.info("********** This is oldBoardReader");

        /*//chunksize만큼 update가 되면 데이터 번호가 바뀔수 있으므로 paging을 0으로 고정
        JpaPagingItemReader<BoardEntity> reader = new JpaPagingItemReader<BoardEntity>() {
            @Override
            public int getPage() {
                return 0;
            }
        };

        reader.setQueryString("SELECT b FROM board b WHERE b.updateddate < (SELECT to_char(SYSDATE-7,'YYYY-MM-DD') FROM DUAL) AND b.status = 'sell' ORDER BY updateddate ASC"); //order조건 필수
        reader.setPageSize(chunkSize);
        reader.setEntityManagerFactory(entityManagerFactory);
        reader.setName("oldBoardReader");

        return reader;*/
        return new JpaPagingItemReaderBuilder<BoardEntity>()
                .name("oldBoardReader")
                .entityManagerFactory(entityManagerFactory)
                .pageSize(chunkSize)
                .queryString("SELECT * FROM board b WHERE b.updateddate < (SELECT to_char(SYSDATE-7,'YYYY-MM-DD') FROM DUAL) AND b.status = 'sell' ORDER BY updateddate ASC") //order조건 필수
                .build();
    }

     @Bean
    public ItemProcessor<BoardEntity, BoardEntity> oldBoardProcessor() {
        log.info("********** This is oldBoardProcessor");

        return new ItemProcessor<BoardEntity, BoardEntity>() {
            @Override
            public BoardEntity process(BoardEntity boardEntity) throws Exception {
                log.info("********** This is oldBoardProcessor2");
                return boardEntity.setStatus(Status.old);
            }
        };
    }

    @Bean
    public JpaItemWriter<BoardEntity> oldBoardWriter() {
        log.info("********** This is oldBoardWriter");
        JpaItemWriter<BoardEntity> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }

    /*@Bean
    @StepScope // step 주기에 따라 새로운 빈을 생성
    public ListItemReader<BoardEntity> oldBoardReader() {
        List<BoardEntity> boardEntityList = boardQueryRepository.getOldBoard();
        return new ListItemReader<>(boardEntityList);
    }


    @Bean
    public ItemProcessor<BoardEntity, BoardEntity> oldBoardProcessor() {
        log.info("********** This is oldBoardProcessor");

        return new ItemProcessor<BoardEntity, BoardEntity>() {
            @Override
            public BoardEntity process(BoardEntity boardEntity) throws Exception {
                log.info("********** This is oldBoardProcessor2");
                return boardEntity.setStatus(Status.old);
            }
        };
    }

    @Bean
    public JpaItemWriter<BoardEntity> oldBoardWriter() {
        log.info("********** This is oldBoardWriter");
        JpaItemWriter<BoardEntity> itemWriter = new JpaItemWriter<>();
        itemWriter.setEntityManagerFactory(entityManagerFactory);
        return itemWriter;
    }*/


}
