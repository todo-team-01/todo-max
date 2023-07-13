package org.codesquad.todo.domain.member;

import org.springframework.stereotype.Component;

@Component
public class MemberValidator {
	private final MemberRepository memberRepository;

	public MemberValidator(MemberRepository memberRepository) {
		this.memberRepository = memberRepository;
	}

	public boolean exist(Long memberId) {
		return memberRepository.exist(memberId);
	}
}
