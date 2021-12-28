package com.cos.photogramstart.service;

import com.cos.photogramstart.domain.user.subscribe.Subscribe;
import com.cos.photogramstart.domain.user.subscribe.SubscribeRepository;
import com.cos.photogramstart.handler.ex.CustomApiException;
import com.cos.photogramstart.web.dto.subscribe.SubscribeDto;
import lombok.RequiredArgsConstructor;
import org.qlrm.mapper.JpaResultMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SubscribeService {

    private final SubscribeRepository subscribeRepository;
    private final EntityManager em; // Repository는 EntityManager를 구현해서 만들어져 있는 구현체


    @Transactional(readOnly = true)
    public List<SubscribeDto> 구독리스트(int principalId, int pageUserId) {

        // 쿼리 준비
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT u.id, u.username, u.profileImageUrl, ");
        sb.append("if ((SELECT 1 FROM subscribe WHERE fromUserId = ? AND toUserId = u.id), 1, 0) subscribeState, ");
        sb.append("if ((?=u.id), 1, 0) equalUserState ");
        sb.append("FROM user u INNER JOIN subscribe s ");
        sb.append("ON u.id = s.toUserId ");
        sb.append("WHERE s.fromUserId = ?"); // 세미콜론 첨부하면 안됨


        // 첫번째 물음표 : principalId
        // 두번째 물음표 : principalId
        // 마지막 물음표 : pageUserId

        // 쿼리 완성
        Query query = em.createNativeQuery(sb.toString())
                .setParameter(1, principalId)
                .setParameter(2, principalId)
                .setParameter(3, pageUserId);


        // 쿼리 실행
        // JpaResultMapper = qlrm 라이브러리
        // qlrm 라이브러리 필요 = Dto에 결과를 매핑하기 위해서
        JpaResultMapper resultMapper = new JpaResultMapper();
        List<SubscribeDto> subscribeDtos = resultMapper.list(query, SubscribeDto.class);

        return subscribeDtos;
    }

    @Transactional
    public void 구독하기(int fromUserId, int toUserId) {
        try {
            subscribeRepository.mSubscribe(fromUserId, toUserId);
        }catch (Exception e) {
            throw new CustomApiException("이미 구독을 하였습니다.");
        }

    }

    @Transactional
    public void 구독취소하기(int fromUserId, int toUserId) {
        subscribeRepository.mUnSubscribe(fromUserId, toUserId);
    }
}
