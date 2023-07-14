package com.example.swagger.service;

import com.example.swagger.dto.CommentDto;
import com.example.swagger.entity.BoardEntity;
import com.example.swagger.entity.CommentEntity;
import com.example.swagger.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private BoardService boardService;


    // 댓글ID찾기
    public CommentEntity findCommentId(Integer commentId){
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. 댓글을 다시 확인해주세요. commentId=" + commentId));
    }

    // 댓글 저장 + DTO를 통한 게시글 쓰기
    public CommentEntity saveComment(CommentDto commentDto, Integer boardId) {
        BoardEntity boardEntity = boardService.boardView(boardId);

        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setName(commentDto.getName());
        commentEntity.setContent(commentDto.getContent());
        commentEntity.setBoard(boardEntity);
        commentEntity.setDateSaver(LocalDateTime.now());

        return commentRepository.save(commentEntity);
    }


    // 댓글 수정
    public CommentEntity updateComment(Integer commentId, CommentDto commentDto) {
        CommentEntity existingCommentEntity = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 댓글이 없습니다. 댓글을 다시 확인해주세요. commentId=" + commentId));

        existingCommentEntity.setName(commentDto.getName());
        existingCommentEntity.setContent(commentDto.getContent());
        return commentRepository.save(existingCommentEntity);
    }


    // 댓글 삭제
    public void deleteById(Integer id) {
        commentRepository.deleteById(id);
    }
}