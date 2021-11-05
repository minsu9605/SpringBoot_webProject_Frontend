package com.example.CUSHProject.job;

import com.example.CUSHProject.entity.BoardEntity;
import com.example.CUSHProject.enums.Status;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BoardItemPreparedStatementSetter implements ItemPreparedStatementSetter<BoardEntity> {
    @Override
    public void setValues(BoardEntity status, PreparedStatement ps) throws SQLException {
        ps.setString(1, status.getStatus().getValue());
    }
}
