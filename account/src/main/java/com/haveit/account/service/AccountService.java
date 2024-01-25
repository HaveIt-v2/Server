package com.haveit.account.service;

import com.haveit.account.dto.request.LoginRequestDto;
import com.haveit.account.dto.request.SignupRequestDto;
import com.haveit.account.dto.response.TokenResponseDto;
import com.haveit.account.entity.Account;
import com.haveit.account.enums.AccountType;
import com.haveit.account.enums.MemberState;
import com.haveit.account.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final AccountRepository accountRepository;
    public Account signup(SignupRequestDto signupRequestDto){
        //이미 존재할 경우 예외 처리

        //새 계정 생성
        Account account = Account.builder()
                .email(signupRequestDto.getEmail())
                .password(bCryptPasswordEncoder.encode(signupRequestDto.getPassword())) // 비밀번호는 DB에서도 암호화해서 저장
                .nickName(signupRequestDto.getNickName())
                .accountType(AccountType.NORMAL.name())
                .build();

        account = accountRepository.save(account);
        return account;
    }

    public Account findMember(Object value){
        Optional<Account> optionalAccount;

        if(value instanceof Long){  //id값으로 확인
            optionalAccount = accountRepository.findByMemberId((Long) value);
        } else {  //email로 확인
            optionalAccount = accountRepository.findByEmail((String) value);
        }

        if (optionalAccount.isEmpty()) {
            throw new RuntimeException("존재하지 않는 회원입니다.");
        } else if (optionalAccount.get().getState().equals(MemberState.DELETE.name())) {  //회원상태 확인 => delete 인지 확인
            throw new RuntimeException("탈퇴한 회원입니다.");
        }

        return optionalAccount.get();
    }

    public TokenResponseDto login(LoginRequestDto loginRequestDto){
        //회원이 존재하는지 확인
        Account account = findMember(loginRequestDto.getEmail());
        return createToken(account);
    }

    
}
