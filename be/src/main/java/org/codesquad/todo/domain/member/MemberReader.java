package org.codesquad.todo.domain.member;

import org.springframework.stereotype.Component;

@Component
public class MemberReader {
	private final MemberRepository memberRepository;

	public MemberReader(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public boolean isExist(Long memberId) {
		return memberRepository.isExist(memberId);
	}
}
