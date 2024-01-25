package com.haveit.account.repository;

import com.haveit.account.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findByMemberId(Long memberId);
    Optional<Account> findByEmail(String email);
}
