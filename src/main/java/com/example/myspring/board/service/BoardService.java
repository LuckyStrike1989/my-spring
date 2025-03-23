package com.example.myspring.board.service;

import com.example.myspring.board.dto.Board;
import com.example.myspring.board.mapper.BoardMapper;
import com.example.servlet.exception.BoardNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class BoardService {
    @Autowired
    private BoardMapper boardMapper;

    @Cacheable(cacheNames = "getAll", key = "'boards:page:' + #page + ':size:' + #size", cacheManager = "boardCacheManager")
    public Map<String, Object> getAll(int page, int size) {
        log.info("getAll() called");
        int offset = page * size;
        List<Board> list = boardMapper.findAll(offset, size);
        int total = boardMapper.count();

        Map<String, Object> result = new HashMap<>();
        result.put("content", list);
        result.put("page", page);
        result.put("size", size);
        result.put("totalElements", total);
        result.put("totalPages", (int) Math.ceil((double) total / size));

        return result;
    }

    public Board getById(int id) {
        log.info("getById() called");
        return boardMapper.findById(id).orElseThrow(() -> new BoardNotFoundException("해당 게시글을 찾을 수 없습니다. ID = " + id));
    }

    public void create(Board board) {
        log.info("create() called");
        boardMapper.insert(board);
    }

    public void update(Board board) {
        log.info("update() called");
        boardMapper.findById(board.getId()).orElseThrow(() -> new BoardNotFoundException("수정할 게시글이 없습니다. ID = " + board.getId()));
        boardMapper.update(board);
    }

    public void delete(int id) {
        log.info("delete() called");
        boardMapper.findById(id).orElseThrow(() -> new BoardNotFoundException("삭제할 게시글이 없습니다. ID = " + id));
        boardMapper.delete(id);
    }
}
