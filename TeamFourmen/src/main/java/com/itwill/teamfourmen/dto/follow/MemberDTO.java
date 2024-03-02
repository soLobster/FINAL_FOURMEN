package com.itwill.teamfourmen.dto.follow;

import com.itwill.teamfourmen.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {

    // 이메일만 필요함...
    private String email;

    public static MemberDTO from(Member member){
        return new MemberDTO(member.getEmail());
    }
}
