package o3.member.service;

import lombok.RequiredArgsConstructor;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.member.domain.Tax;
import o3.member.dto.TaxScrapRequest;
import o3.member.repository.MemberRepository;
import o3.member.repository.TaxRepository;
import o3.member.dto.TaxScrapResponse;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaxService {

    private final TaxRepository taxRepository;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public TaxScrapResponse scrapTax(TaxScrapRequest taxScrapRequest, TaxScrapResponse taxScrapResponse) {
        String loginId = "";
        Tax tax = taxScrapResponse.toEntity();

        List<Member> MemberList = memberRepository.findMembersByName(taxScrapRequest.getName());
        for (Member member : MemberList) {
            if (passwordEncoder.matches(taxScrapRequest.getRegNo(), member.getRegNo())) {
                loginId = member.getLoginId();
            }
        }

        tax.updateMember(memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER)));

        taxRepository.save(tax);

        return taxScrapResponse;
    }

    public void refundTax() {

    }

}
