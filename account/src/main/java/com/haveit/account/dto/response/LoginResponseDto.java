package com.haveit.account.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginResponseDto {
    private Long memberId;
    private String nickName;
    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpirationTime;
}
