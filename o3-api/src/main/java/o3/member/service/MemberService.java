package o3.member.service;

import lombok.RequiredArgsConstructor;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.member.util.MemberValidator;
import o3.member.repository.MemberRepository;
import o3.security.jwt.JwtProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;


    public Optional<Member> createMember(Member member) {
        MemberValidator.validateCreation(member);

        memberRepository.findByLoginId(member.getLoginId())
                .ifPresent(p -> { throw new O3Exception(O3ExceptionStatus.DUPLICATION_MEMBER); });

        encode(member);
        memberRepository.save(member);

        return memberRepository.findByLoginId(member.getLoginId());
    }

    public Optional<Member> searchDetailMember(HttpServletRequest request) {
        String jwtToken = jwtProvider.resolveToken(request);
        String loginInd = jwtProvider.getUserIdFromToken(jwtToken);

        return memberRepository.findByLoginId(loginInd);
    }

    private void encode(Member member) {
        member.passwordEncode(passwordEncoder.encode(member.getPassword()));
        member.regNoEncode(passwordEncoder.encode(member.getRegNo()));
    }
}
