package com.itwill.teamfourmen.dto.follow;

import com.itwill.teamfourmen.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    private String email;

    public static MemberDTO from(Member member){
        return new MemberDTO(member.getEmail());
    }

}
