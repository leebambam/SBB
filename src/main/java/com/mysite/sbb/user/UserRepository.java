package com.mysite.sbb.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<SiteUser, Long> {

    // 사용자 ID를 조회하는 기능
    Optional<SiteUser> findByusername(String username);

    // 이메일(email)로 조회
    Optional<SiteUser> findByEmail(String email);

    Optional<SiteUser> findByUsernameAndEmail(String username, String email);
}