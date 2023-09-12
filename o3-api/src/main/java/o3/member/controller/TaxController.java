package o3.member.controller;

import lombok.RequiredArgsConstructor;
import o3.exception.O3Exception;
import o3.exception.O3ExceptionStatus;
import o3.member.domain.Member;
import o3.member.dto.TaxScrapRequest;
import o3.member.repository.MemberRepository;
import o3.member.service.TaxService;
import o3.openfeign.MemberScrapOpenfeign;
import o3.member.dto.TaxScrapResponse;
import o3.security.jwt.JwtProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/szs")
@RequiredArgsConstructor
public class TaxController {

    private final TaxService taxService;
    private final MemberScrapOpenfeign feign;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;

    @PostMapping("/scrap")
    public ResponseEntity<?> scrapTax(HttpServletRequest request) {
        // 토큰에서 정보 가져오기
        String regNo = "";
        String jwtToken = jwtProvider.resolveToken(request);
        String loginInd = jwtProvider.getUserIdFromToken(jwtToken);

        Member member = memberRepository.findByLoginId(loginInd)
                .orElseThrow(() -> new O3Exception(O3ExceptionStatus.NO_MEMBER));

//        List<Member> MemberList = memberRepository.findMembersByName(taxScrapRequest.getName());
//        for (Member m : MemberList) {
//            if (passwordEncoder.matches(taxScrapRequest.getRegNo(), m.getRegNo())) {
//                regNo = m.getLoginId();
//            }
//        }

        TaxScrapRequest taxScrapRequest = TaxScrapRequest.builder()
                .name(member.getName())
                .regNo(member.getRegNo())
                .build();
        TaxScrapResponse taxScrapResponse = feign.call(taxScrapRequest);

        if (!"success".equals(taxScrapResponse.getStatus()) || ObjectUtils.isEmpty(taxScrapResponse.getData())) {
            throw new O3Exception(O3ExceptionStatus.FAIL_EXTERNAL_API);
        }

        return ResponseEntity.ok(taxService.scrapTax(taxScrapRequest, taxScrapResponse));
    }

    @PostMapping("/refund")
    public ResponseEntity<?> refundTax(HttpServletRequest request) {
        taxService.refundTax();

        return ResponseEntity.ok(null);
    }


}
