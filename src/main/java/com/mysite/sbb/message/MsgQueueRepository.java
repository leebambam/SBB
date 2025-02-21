package com.mysite.sbb.message;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MsgQueueRepository extends JpaRepository<MsgQueue, Long> {
}
