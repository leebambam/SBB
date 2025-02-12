package com.mysite.sbb.question;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Page; // 페이징을 위한 클래스
import org.springframework.data.domain.Pageable; // 페이징을 처리하는 인터페이스
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

/*
엔티티만으로는 테이블의 데이터를 저장, 조회, 수정, 삭제 등을 할 수 없다.
이와 같이 데이터를 관리하려면 데이터베이스와 연동하는 JPA 리포지터리가 반드시 필요하다.

엔티티가 데이터베이스 테이블을 생성했다면,
리포지터리는 이와 같이 생성된 데이터베이스 테이블의 데이터들을

저장, 조회, 수정, 삭제 등을 할 수 있도록 도와주는 인터페이스이다.

이때 리포지터리는 테이블에 접근하고, 데이터를 관리하는 메서드(예를 들어 findAll, save 등)를 제공한다.

 */
public interface QuestionRepository extends JpaRepository<Question, Integer> {
    // QuestionRepository 인터페이스를 리포지터리로 만들기 위해 JpaRepository 인터페이스를 상속

    Question findBySubject(String subject);

    Question findBySubjectAndContent(String subject, String content);

    List<Question> findBySubjectLike(String subject);

    Page<Question> findAll(Pageable pageable);

    // Specification과 Pageable 객체를 사용하여 DB에서 Question 엔티티를 조회한 결과를 페이징
    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    Page<Question> findByCreateDateBetween(LocalDateTime localDateTime, LocalDateTime localDateTime1, Pageable pageable);
}