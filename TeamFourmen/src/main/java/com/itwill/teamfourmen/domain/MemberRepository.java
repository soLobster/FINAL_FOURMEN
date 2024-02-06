package com.itwill.teamfourmen.domain;

import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MemberRepository extends JpaRepository<Member, String>{

    // select m, r.roles 
    // from Member m 
    // left join Member_Roles r on m.id = r.member_id 
    // where m.username = ?
    @EntityGraph(attributePaths = "roles")
    Optional<Member> findByEmail(String email);
    
 
    Member findByNameAndPhone(String name, String phone);
    
    // select m, r.roles
    // from Member m
    // left join Member_Roles r on m.id = r.member_id
    // where m.name = ? and m.phone = ?
    @EntityGraph(attributePaths = "roles")
    Member findByEmailAndName(String email, String name);
    
    
    Member findByNickname(String nickname);
    
    Member findByPhone(String phone);
    

}
	

