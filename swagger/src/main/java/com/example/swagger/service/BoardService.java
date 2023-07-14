package com.example.swagger.service;

import com.example.swagger.dto.BoardDto;
import com.example.swagger.dto.CommentDto;
import com.example.swagger.entity.BoardEntity;
import com.example.swagger.entity.CommentEntity;
import com.example.swagger.repository.BoardRepository;
import com.example.swagger.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private CommentRepository commentRepository;

    // 게시판 쓰기 DTO
    public BoardEntity create(BoardDto boardDto) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setName(boardDto.getName());
        boardEntity.setPrice(boardDto.getPrice());
        boardEntity.setContent(boardDto.getContent());
        boardEntity.setDate(boardDto.getDate());

        return boardRepository.save(boardEntity);
    }

    // 게시판 상세페이지 이동
    public BoardEntity boardView(Integer id){
        return boardRepository.findById(id).get();
    }


    // 게시판 수정
    public BoardEntity update(Integer boardId, BoardDto boardDto) {
        BoardEntity existingBoard = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. 게시글을 다시 확인해주세요. boardId=" + boardId));

        // 업데이트할 필드 값을 DTO에서 가져와서 엔티티에 설정
        existingBoard.setName(boardDto.getName());
        existingBoard.setPrice(boardDto.getPrice());
        existingBoard.setContent(boardDto.getContent());
        return boardRepository.save(existingBoard);
    }

    // 게시판 삭제
    @Transactional
    public void boardDelete(Integer id) {
        BoardEntity board = boardRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시물을 찾을 수 없습니다. ID: " + id));

        commentRepository.deleteByBoardEntity(board);
        System.out.println("댓글이 먼저 삭제되었습니다.");

        boardRepository.delete(board);
        System.out.println("이후에 게시물도 같이 삭제되었습니다.");
    }

}