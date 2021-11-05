package com.example.CUSHProject.job;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Status;
import com.example.CUSHProject.repository.BoardQueryRepository;
import com.example.CUSHProject.repository.BoardRepository;
import lombok.AllArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.*;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.batch.item.support.ListItemReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@AllArgsConstructor
@Configuration
public class OldBoardJobConfiguration {
    private final DataSource dataSource;

    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private static final int chunkSize = 10;

    @Bean
    public Job oldBoardJob() throws Exception {
        log.info("**********this is oldBoardJob");
        return jobBuilderFactory.get("oldBoardJob")
                .start(oldBoardJobStep())
                .build();
    }

    @Bean
    @JobScope
    public Step oldBoardJobStep() throws Exception {
        log.info("**********this is oldBoardJobStep");
        return stepBuilderFactory.get("oldBoardJobStep")
                .<BoardEntity, BoardEntity>chunk(chunkSize)
                .reader(oldBoardReader()) //구현해야함 게시물을 올린지 1주일 이상된 보드를 읽어온다
//                .processor(oldBoardProcessor(null)) //구현해야함 판매완료가 아닐경우 ~~~을 수행한다
                .writer(oldBoardWriter()) //구현해야함 처리된 게시물을 DB에 저장한다
                .build();
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<BoardEntity> oldBoardReader() throws Exception {

        LocalDateTime nowDate = LocalDateTime.now();
        LocalDateTime weeksAgo = nowDate.minusWeeks(1);
        log.info("***weeksAgo: "+ weeksAgo);
        Map<String, Object> parameterValues = new HashMap<>();

        parameterValues.put("weeksAgo", weeksAgo);
        parameterValues.put("sellStatus", Status.sell.getValue());

        return new JdbcPagingItemReaderBuilder<BoardEntity>()
                .fetchSize(chunkSize)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(BoardEntity.class))
                .queryProvider(createQueryProvider())
                .parameterValues(parameterValues)
                .name("oldBoardReader")
                .build();
    }

    @Bean
    @StepScope
    public JdbcBatchItemWriter<BoardEntity> oldBoardWriter(){
        return new JdbcBatchItemWriterBuilder<BoardEntity>()
                .dataSource(dataSource)
                .itemPreparedStatementSetter((item, ps) -> {
                    ps.setString(1,Status.old.getValue());
                })
                .sql("UPDATE board SET status = ?")
                .build();
    }

    public PagingQueryProvider createQueryProvider() throws Exception {
        SqlPagingQueryProviderFactoryBean queryProvider = new SqlPagingQueryProviderFactoryBean();
        queryProvider.setDataSource(dataSource); // Database에 맞는 PagingQueryProvider를 선택하기 위해
        queryProvider.setSelectClause("board_id, updateddate, board_writer, status");
        queryProvider.setFromClause("FROM board");
        queryProvider.setWhereClause("WHERE updateddate <= :weeksAgo and status = 'sell'"); // 쿼리 정상 동작

        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("board_id", Order.ASCENDING);

        queryProvider.setSortKeys(sortKeys);

        return queryProvider.getObject();
    }

}


