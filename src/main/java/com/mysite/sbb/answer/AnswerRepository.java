package com.mysite.sbb.answer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
    // 생성일 기준 정렬
    Page<Answer> findByQuestionId(Integer questionId, Pageable pageable);

    // 추가: 추천 수 기준 정렬
    Page<Answer> findByQuestionIdOrderByVoterDesc(Integer questionId, Pageable pageable);
}