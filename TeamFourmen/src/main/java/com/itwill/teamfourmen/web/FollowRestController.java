package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.dto.follow.MemberDTO;
import com.itwill.teamfourmen.service.FollowService;
import com.itwill.teamfourmen.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/follow")
public class FollowRestController {

    private final FollowService followService;

    // 팔로우 추가
    @PostMapping("/{friendEmail}")
    public ResponseEntity<Boolean> follow(Authentication authentication , @PathVariable(name = "friendEmail") String email){

        MemberDTO from_user = new MemberDTO(authentication.getName());
        MemberDTO to_user = new MemberDTO(email);

        Boolean isDone= followService.addFollow(from_user, to_user);

        return ResponseEntity.ok(isDone);
    }

    // 팔로우 삭제
    @DeleteMapping("/{friendEmail}")
    public ResponseEntity<Boolean> unFollow(Authentication authentication, @PathVariable(name = "friendEmail") String email){

        MemberDTO from_user = new MemberDTO(authentication.getName());
        MemberDTO to_user = new MemberDTO(email);

        Boolean isDelete = followService.unFollow(from_user, to_user);

        return ResponseEntity.ok(isDelete);
    }

    //팔로우 체크
    @GetMapping("/{friendEmail}")
    public ResponseEntity<Boolean> didAlreadyFollowUser(Authentication authentication, @PathVariable(name = "friendEmail") String email){
        MemberDTO from_user = new MemberDTO(authentication.getName());
        MemberDTO to_user = new MemberDTO(email);

        Boolean didAlreadyFollow = followService.didAlreadyFollow(from_user, to_user);

        return ResponseEntity.ok(didAlreadyFollow);
    }

    // 팔로워 수 가져오기
    @GetMapping("/{userEmail}/follower")
    public ResponseEntity<Map<String, Integer>> getUserFollowers(@PathVariable (name = "userEmail") String email) {
        MemberDTO target_user = new MemberDTO(email);

         Map<String, Integer> getSocialMap = followService.followersCount(target_user);

        return ResponseEntity.ok(getSocialMap);
    }
}
