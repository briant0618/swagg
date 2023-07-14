package com.example.swagger.controller;

import com.example.swagger.dto.BoardDto;
import com.example.swagger.entity.BoardEntity;
import com.example.swagger.entity.CommentEntity;
import com.example.swagger.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;




@RestController
@Tag(name = "BoardCRUD", description = "게시물 API CRUD")
@RequestMapping("/api")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }


    // 게시글 쓰기
    @Operation(summary = "BoardCreate" ,description = "게시글 작성 Form")
    @PostMapping("/board/writepro")

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 작성 성공"),
            @ApiResponse(responseCode = "400", description = "이상한 요청")
    })
    public ResponseEntity<String> writeBoard(@RequestBody BoardDto boardDto) {
        BoardEntity createdBoard = boardService.create(boardDto);
        return ResponseEntity.ok("게시글 작성 완료. ID: " + createdBoard.getId());
    }

    // 게시글 보기
    @Operation(summary = "BoardRead" ,description = "게시글 보기")
    @GetMapping("/boardview/{id}")
    @ResponseBody
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 조회 성공"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    public ResponseEntity<BoardEntity> viewMove(@PathVariable("id") Integer id,
                                                Model model) {
        BoardEntity boardEntity = boardService.boardView(id);
        model.addAttribute("comments", boardEntity.getCommentEntity());
        model.addAttribute("comment", new CommentEntity());
        model.addAttribute("boardEntity", boardEntity);
        return ResponseEntity.ok(boardEntity);
    }

    // 수정작업
    @PutMapping("/update/{id}")
    @Operation(summary = "BoardUpdate", description = "게시판 수정 Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 수정 성공"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    public ResponseEntity<BoardEntity> updateBoard(@PathVariable("id") Integer id, @RequestBody BoardDto boardDto) {
        BoardEntity existingBoard = boardService.update(id, boardDto);
        if (existingBoard == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingBoard);
    }

    // 삭제 작업
    @Operation(summary = "BoardDelete", description = "게시판 삭제 Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "게시글 삭제 성공"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
            @ApiResponse(responseCode = "404", description = "게시글 없음")
    })
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteBoard(@PathVariable("id") Integer id) {
        boardService.boardDelete(id);
        return ResponseEntity.ok("Delete Success");
    }

}

