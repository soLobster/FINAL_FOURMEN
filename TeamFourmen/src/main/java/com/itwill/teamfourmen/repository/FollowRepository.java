package com.itwill.teamfourmen.repository;

import com.itwill.teamfourmen.domain.Follow;
import com.itwill.teamfourmen.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    Follow findByFromUserEmailAndToUserEmail(String fromUserEmail, String toUserEmail);
    
    Integer countByFromUserEmail(String userEmail);

    Integer countByToUserEmail(String userEmail);

}
