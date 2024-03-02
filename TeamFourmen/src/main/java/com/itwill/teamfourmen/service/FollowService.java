package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.domain.Follow;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.dto.follow.MemberDTO;
import com.itwill.teamfourmen.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    // 팔로우 추가
    public boolean addFollow(MemberDTO fromUser, MemberDTO toUser){
        log.info("ADD FOLLOW SERVICE - FROM USER EMAIL = {} ,TO USER EMAIL = {}", fromUser.getEmail(), toUser.getEmail());

        Member fromMember = memberRepository.findByEmail(fromUser.getEmail()).orElse(null);

        Member toMember = memberRepository.findByEmail(toUser.getEmail()).orElse(null);

        if(fromMember == null || toMember == null){
            return false;
        }

        Follow entity = Follow.builder()
                .fromUser(fromMember)
                .toUser(toMember)
                .build();

        try {
            followRepository.save(entity);
            return true;
        } catch (Exception e) {
            log.error("에러 발생 = {}", e.getMessage());
            return false;
        }
    }

    // 팔로우 삭제
    public boolean unFollow(MemberDTO fromUser, MemberDTO toUser){
        log.info("UNFOLLOW SERVICE - FROM USER EMAIL = {} , TO USER EMAIL = {}", fromUser.getEmail(), toUser.getEmail());

        Member fromMember = memberRepository.findByEmail(fromUser.getEmail()).orElse(null);
        Member toMember = memberRepository.findByEmail(toUser.getEmail()).orElse(null);

        Follow targetFollow = followRepository.findByFromUserEmailAndToUserEmail(fromUser.getEmail(), toUser.getEmail());

        try {
            followRepository.delete(targetFollow);
            return true;
        } catch (Exception e){
            log.error("에러 발생 = {}", e.getMessage());
            return false;
        }
    }

    // 팔로우/팔로워 수
    public Map<String, Integer> followersCount(MemberDTO targetUser){
        log.info("GET FOLLOWERS COUNT TARGET USER EMAIL = {}", targetUser.getEmail());

        Member targetMember = memberRepository.findByEmail(targetUser.getEmail()).orElse(null);

        Map<String, Integer> targetUserSocialCount = new HashMap<>();
            try {
                int followers = followRepository.countByFromUserEmail(targetMember.getEmail());
                int followings = followRepository.countByToUserEmail(targetMember.getEmail());

                log.info("NUM OF FOLLOWERS = {}",followers);

                log.info("NUM OF FOLLOWINGS = {}", followings);

                targetUserSocialCount.put("followers" , followers);
                targetUserSocialCount.put("followings", followings);

                return targetUserSocialCount;
            } catch (Exception e) {
                log.error("에러 발생 = {}", e.getMessage());

                targetUserSocialCount.put("followers", 0);
                targetUserSocialCount.put("followings", 0);

                return targetUserSocialCount;
            }
    }

    // 팔로우 체크
    public boolean didAlreadyFollow(MemberDTO fromUser, MemberDTO toUser){
        log.info("DID ALREADY FOLLOW FROM = {} ,  TO = {}", fromUser.getEmail(), toUser.getEmail());

        Follow targetFollow = followRepository.findByFromUserEmailAndToUserEmail(fromUser.getEmail(), toUser.getEmail());

        log.info("follow??? = {}", targetFollow);

        if(targetFollow != null){
            return true;
        } else {
            return false;
        }
    }

}
