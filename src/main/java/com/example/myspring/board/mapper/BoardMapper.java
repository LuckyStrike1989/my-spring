package com.example.myspring.board.mapper;

import com.example.myspring.board.dto.Board;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    List<Board> findAll(@Param("offset") int offset, @Param("limit") int limit);
    int count();
    Optional<Board> findById(int id);
    void insert(Board board);
    void update(Board board);
    void delete(int id);
}
