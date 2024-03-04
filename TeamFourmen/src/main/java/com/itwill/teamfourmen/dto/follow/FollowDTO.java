package com.itwill.teamfourmen.dto.follow;


import com.itwill.teamfourmen.domain.Follow;
import com.itwill.teamfourmen.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FollowDTO {

    private String fromUserEmail;
    private String toUserEmail;

    public static FollowDTO from(Follow follow){
        return new FollowDTO(
                follow.getFromUser().getEmail(),
                follow.getToUser().getEmail()
        );
    }
}
