package org.codesquad.todo.domain.member;

import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
	private final MemberRepository memberRepository;

	public MemberValidator(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public boolean isExist(Long memberId) {
		return memberRepository.isExist(memberId);
	}
}
