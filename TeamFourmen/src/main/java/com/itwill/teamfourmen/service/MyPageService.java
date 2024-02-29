package com.itwill.teamfourmen.service;

import com.itwill.teamfourmen.domain.Member;
import com.itwill.teamfourmen.domain.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MyPageService {

    private final MemberRepository memberDao;

    public Member getMember(String email){

        Member member = Member.builder().email(email).build();

        Optional<Member> findMember = memberDao.findByEmail(member.getEmail());

        Member targetmember = findMember.orElseThrow();

        return targetmember;
    }

}
