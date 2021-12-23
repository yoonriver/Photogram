package com.cos.photogramstart.domain.user.subscribe;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    
                                                                                   // : <- 매개변수를 쿼리문에 넣는 표시
    @Query(value = "INSERT INTO subscribe(fromUserId, toUserId, createDate) VALUES(:fromUserId, :toUserId, now())", nativeQuery = true)
    @Modifying // INSERT, DELETE, UPDATE를 네이티브 쿼리로 작성하려면 해당 어노테이션이 필요
    void mSubscribe(Long fromUserId, Long toUserId);

    @Query(value = "DELETE FROM subscribe WHERE fromUserID =:fromUserId AND toUserID =:toUserId", nativeQuery = true)
    void mUnSubscribe(Long fromUserId, Long toUserId);
}
