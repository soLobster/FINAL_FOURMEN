package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.domain.Follow;
import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.Post;
import com.itwill.teamfourmen.dto.board.PostDto;
import com.itwill.teamfourmen.dto.follow.MemberDTO;
import com.itwill.teamfourmen.repository.FollowRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class FollowService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    private int memberPerPage = 10;

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
                int followings = followRepository.countByFromUserEmail(targetMember.getEmail());
                int followers = followRepository.countByToUserEmail(targetMember.getEmail());

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

    // 팔로우 목록 페이지
    public Page<Follow> getFollowPage(Long memberId, int page){
        log.info("GET FOLLOWER LIST fromUser MEMBER_ID = {}, page = {}", memberId , page);

        Member member = memberRepository.findByMemberId(memberId).orElseThrow();

        Pageable pageable = PageRequest.of(page, memberPerPage, Sort.by("createdTime").descending());

        List<Follow> followList = member.getFollowers();

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), followList.size());
        Page<Follow> followPage = new PageImpl<>(followList.subList(start,end), pageable, followList.size());

        return followPage;
    }

    // 팔로잉 목록 페이지
    public Page<Follow> getFollowingPage(Long memberId , int page){
        log.info("GET FOLLOWING LIST toUSER MEMBER_ID = {} , page = {}", memberId , page);

        Member member = memberRepository.findByMemberId(memberId).orElseThrow();

        Pageable pageable = PageRequest.of(page, memberPerPage , Sort.by("createdTime").descending());

        List<Follow> followingList = member.getFollowings();

        int start = (int)pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), followingList.size());
        Page<Follow> followingPage = new PageImpl<>(followingList.subList(start, end), pageable, followingList.size());

        return followingPage;
    }

    public List<Follow> getAllFollowList() {

        List<Follow> allFollowList = followRepository.findAll();

        log.info("ALL FOLLOW LIST = {}", allFollowList);

        return allFollowList;
    }

}
