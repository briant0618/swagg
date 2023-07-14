package com.example.swagger.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user")
public class UserEntity {
    public enum RoleType {
        USER, ADMIN
    }
    // 기본키에 대응하는 식별자 변수
    @Id
    // 1부터 시작하여 자동으로 1씩 증가하도록 증가 전략 설정
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int id;             // 회원 일련번호

    @Column(nullable=false, length=50, unique=true)
    private String username;    // 로그인 아이디

    @Column(length=100)
    private String password;    // 비밀번호

    @Column(nullable=false, length=100)
    private String email;       // 이메일

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @CreationTimestamp // 현재 시간이 기본값으로 등록되도록 설정
    private Timestamp createDate;
}

