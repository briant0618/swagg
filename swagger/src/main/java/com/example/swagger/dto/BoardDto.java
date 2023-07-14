package com.example.swagger.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class BoardDto {
    private Integer id;
    private String name;
    private int price;
    private String content;
    private LocalDateTime date;
    private Integer dateView;
    private List<CommentDto> commentEntity;

}
