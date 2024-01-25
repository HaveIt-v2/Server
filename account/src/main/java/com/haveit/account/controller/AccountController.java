package com.haveit.account.controller;

import com.haveit.account.dto.request.LoginRequestDto;
import com.haveit.account.dto.request.SignupRequestDto;
import com.haveit.account.dto.response.SignupResponseDto;
import com.haveit.account.dto.response.TokenResponseDto;
import com.haveit.account.entity.Account;
import com.haveit.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j //로깅
@RestController //RESTful 컨트롤러 클래스
@RequiredArgsConstructor //필드 초기화하는 생성자 자동 생성
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;

    @PostMapping("/signup")
    //일반회원가입
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto){
        Account account = accountService.signup(signupRequestDto);
        SignupResponseDto response = new SignupResponseDto(account.getMemberId(),"회원가입 성공");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/login")
    //일반회원가입
    public ResponseEntity<TokenResponseDto> login(@RequestBody LoginRequestDto loginRequestDto){
        TokenResponseDto response = accountService.login(loginRequestDto);
        return ResponseEntity.ok(response);
    }
}
