package com.example.myspring.board.controller;

import com.example.myspring.board.dto.Board;
import com.example.myspring.board.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping
    @Operation(summary = "게시글 목록 조회", description = "페이지네이션된 게시글 목록을 조회합니다.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "정상 조회")
    })
    public ResponseEntity<?> getAll(
        @RequestParam(required = false, defaultValue = "0") int page,
        @RequestParam(required = false, defaultValue = "10") int size
    ) {
        return new ResponseEntity<>(boardService.getAll(page, size), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Board> getById(@PathVariable int id) {
        return new ResponseEntity<>(boardService.getById(id), HttpStatus.OK);
    }

    @PostMapping
    public void create(@RequestBody Board board) {
        boardService.create(board);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable int id, @RequestBody Board board) {
        board.setId(id);
        boardService.update(board);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        boardService.delete(id);
    }
}
