package com.example.swagger.controller;

import com.example.swagger.entity.UserEntity;
import com.example.swagger.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/users")
@Tag(name = "Login API", description = "로그인 API 구현")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @Operation(summary = "User Create", description = "아이디 작성 Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "생성 성공"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
    })
    public ResponseEntity<UserEntity> registerUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String email,
            @RequestParam UserEntity.RoleType role
    ) {
        UserEntity user = userService.registerUser(username, password, email, role);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", description = "로그인 Form + Session")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그인 성공"),
            @ApiResponse(responseCode = "401", description = "로그인 실패")
    })
    public ResponseEntity<UserEntity> loginUser(
            @RequestParam String username,
            @RequestParam String password,
            HttpServletResponse response
    ) {
        UserEntity user = userService.loginUser(username, password);
        if (user != null) {
            // 로그인 성공 시 쿠키에 로그인 정보 저장
            Cookie loginCookie = new Cookie("sessionId", String.valueOf(user.getId()));
            loginCookie.setMaxAge(7 * 24 * 60 * 60); // 쿠키 유효기간 설정 (7일)
            loginCookie.setPath("/"); // 쿠키 경로 설정
            response.addCookie(loginCookie); // 쿠키 추가
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PostMapping("/logout")
    @Operation(summary = "Logout", description = "로그아웃 Form")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공"),
            @ApiResponse(responseCode = "500", description = "로그아웃 실패")
    })
    public ResponseEntity<Void> logoutUser(HttpServletRequest request) {
        // 세션에서 사용자 정보를 제거합니다.
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate(); // 세션 무효화
            return ResponseEntity.ok().build();
        } else { // Session 없을때는 안되
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



    @Operation(summary = "User Read", description = "아이디 조회 Form")
    @GetMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "조회 완료"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
            @ApiResponse(responseCode = "404", description = "조회 아이디 없음")
    })
    public ResponseEntity<UserEntity> getUserById(@PathVariable int userId) {
        UserEntity user = userService.getUserById(userId);
        if (user != null) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "User Update", description = "아이디 수정 Form")
    @PutMapping("/{userId}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "수정 완료"),
            @ApiResponse(responseCode = "400", description = "이상한 요청"),
            @ApiResponse(responseCode = "404", description = "수정 할 아이디 없음")
    })
    public ResponseEntity<UserEntity> updateUser(
            @PathVariable int userId,
            @RequestBody UserEntity userEntity
    ) {
        UserEntity existingUser = userService.getUserById(userId);
        if (existingUser != null) {
            userEntity.setId(userId);
            UserEntity updatedUser = userService.updateUser(userEntity);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/onlyAdmin")
    @PreAuthorize("hasRole('ADMIN') and !hasRole('USER')")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "접속 성공"),
            @ApiResponse(responseCode = "403", description = "접근 거부")
    })
    public String adminOnlyAccess() {
        return "여기는 ADMIN만 접속 가능";
    }


}