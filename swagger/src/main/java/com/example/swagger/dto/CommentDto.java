package com.example.swagger.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Integer id;
    private String name;
    private String content;
    private LocalDateTime dateSaver;
    private Integer boardId;

}