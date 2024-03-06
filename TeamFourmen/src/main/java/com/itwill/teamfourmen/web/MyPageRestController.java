package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import com.itwill.teamfourmen.domain.PlaylistItem;
import com.itwill.teamfourmen.domain.PlaylistLike;
import com.itwill.teamfourmen.domain.Review;
import com.itwill.teamfourmen.dto.review.CombineReviewDTO;
import com.itwill.teamfourmen.service.FeatureService;
import com.itwill.teamfourmen.service.MyPageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/mypage")
public class MyPageRestController {

    private final FeatureService featureService;
    private final MyPageService myPageService;

    @GetMapping("/user-info")
    public ResponseEntity<Member> getMemberInfo(@RequestParam(name = "memberId") Long memberId){
        log.info("GET MEMBER INFO = {} ", memberId);

        Member userInfo = myPageService.getMember(memberId);

        return ResponseEntity.ok(userInfo);
    }


    @GetMapping("/get-num-of-reviews")
    public ResponseEntity<Integer> getNumReviews(@RequestParam(name = "memberId") Long memberId){
        log.info("GET REVIEWS NUM WHO  = {} ", memberId);

        List<Review> allReview =  featureService.getAllMyReview(memberId);

        int numOfReview = 0;

        if(!allReview.isEmpty()){
            numOfReview = allReview.size();
        } else {
            numOfReview = 0;
        }

        return ResponseEntity.ok(numOfReview);
    }
    
    
    /**
     * PlaylistLike타입의 객체를 받아서 playlist_like DB에 추가하는 컨트롤러 메서드
     * @param playlistLike
     */
    @PostMapping("/playlist/add/like")
    public void addPlaylistLike(@RequestBody PlaylistLike playlistLike) {
    	log.info("addPlaylistLike(playlistLike={})", playlistLike);
    	
    	featureService.addPlaylistLike(playlistLike);
    	
    }
    
    /**
     * PlaylistLike타입의 객체를 아규먼트로 받아서, 좋아요 취소
     * @param playlistLike
     */
    @PostMapping("/playlist/delete/like")
    public void deletePlaylistLike(@RequestBody PlaylistLike playlistLike) {
    	log.info("deletePlaylistLike(playlistLike={})", playlistLike);
    	
    	featureService.deletePlaylistLike(playlistLike);
    }
    
    
    /**
     * playlistId에 해당하는 playlist를 DB에서 삭제하는 컨트롤러 메서드
     * @param playlistId
     */
    @DeleteMapping("/playlist/delete/{playlistId}")
    public void deletePlaylist(@PathVariable(name = "playlistId") Long playlistId) {
    	log.info("deletePlaylist(playlistId={})", playlistId);
    	
    	featureService.deletePlaylist(playlistId);
    }
    
    
    /**
     * 유저가 playlist내에서 playlist item의 순서를 바꾼 경우 바뀐 순서를 저장해주는 컨트롤러 메서드
     * @param playlistItemList
     */
    @PostMapping("/playlist/reorder")
    public void reorderPlaylist(@RequestBody List<PlaylistItem> playlistItemList) {
    	log.info("reorderPlaylist(playlistItemList={})", playlistItemList);
    	
    	featureService.reorderPlaylist(playlistItemList);
    	
    }
    
    
   /**
    * playlistItemId를 아규먼트로 받아 해당 플레이리스트 아이템을 삭제하는 컨트롤러 메서드
    * @param playlistItemId
    */
    @DeleteMapping("/playlist/delete-item/{playlistItemId}")
    public void deletePlaylistItem(@PathVariable(name = "playlistItemId") Long playlistItemId) {
    	log.info("deletePlaylistItem(playlistItemId={})", playlistItemId);
    	
    	featureService.deletePlaylistItem(playlistItemId);
    }
    
}
