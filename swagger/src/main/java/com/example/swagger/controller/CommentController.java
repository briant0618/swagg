package com.example.swagger.controller;


import com.example.swagger.dto.CommentDto;
import com.example.swagger.entity.BoardEntity;
import com.example.swagger.entity.CommentEntity;
import com.example.swagger.service.BoardService;
import com.example.swagger.service.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "CommentCRUD", description = "댓글 API CRUD")
public class CommentController {
    @Autowired
    private CommentService commentService;


    // 댓글 Create
    @Operation(summary = "Comment Create", description = "댓글 작성 Form")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentEntity.class)))
    @PostMapping("/boardview/{boardId}/addComment")
    public ResponseEntity<CommentEntity> addComment(@RequestBody CommentDto commentDto,
                                                    @PathVariable("boardId") Integer boardId) {
        CommentEntity commentEntity = commentService.saveComment(commentDto, boardId);
        return ResponseEntity.ok(commentEntity);
    }

    // 댓글 Read
    @Operation(summary = "Comment Read", description = "댓글 조회")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentEntity.class)))
    @GetMapping("/comment/{commentId}")
    public ResponseEntity<CommentEntity> getComment(@PathVariable("commentId") Integer commentId) {
        CommentEntity comment = commentService.findCommentId(commentId);
        return ResponseEntity.ok(comment);
    }


    // 댓글 Update
    @Operation(summary = "Comment Update", description = "댓글 수정 Form")
    @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CommentEntity.class)))
    @PutMapping("/boardview/{boardId}/updateComment/{commentId}")
    public ResponseEntity<CommentEntity> updateComment(@RequestBody CommentDto updatedCommentDto,
                                                       @PathVariable("commentId") Integer commentId) {
        CommentEntity existingCommentEntity = commentService.updateComment(commentId, updatedCommentDto);
        if (existingCommentEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(existingCommentEntity);
    }


    // 댓글 Delete
    @Operation(summary = "Comment Delete", description = "댓글 삭제 Form")
    @ApiResponse(responseCode = "200", description = "Successful operation")
    @DeleteMapping("/boardview/{boardId}/deleteComment/{commentId}")
    public ResponseEntity<String> deleteComment(@PathVariable("commentId") Integer commentId){
        CommentEntity commentEntity = commentService.findCommentId(commentId);
        if (commentEntity == null) {
            return ResponseEntity.notFound().build();
        }

        commentService.deleteById(commentId);

        return ResponseEntity.ok("Delete Success");
    }


}