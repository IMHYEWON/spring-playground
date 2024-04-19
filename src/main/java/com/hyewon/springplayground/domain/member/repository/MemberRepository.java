package com.hyewon.springplayground.domain.member.repository;

import com.hyewon.springplayground.domain.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
