package o3.security.sec;

import lombok.RequiredArgsConstructor;
import o3.member.domain.Member;
import o3.member.repository.MemberRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String loginId) throws UsernameNotFoundException {
        Optional<Member> member = Optional.ofNullable(memberRepository.findByLoginId(loginId)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format("member : ( %s ) not found", loginId))));

        Set<SimpleGrantedAuthority> authority = null;
        for (UserRole role : UserRole.values()) {
            if(member.get().getRole().equals(role.name())) authority = role.grantedAuthorities();
        }

        return new UserDetailsImpl(
                member.get().getLoginId(),
                member.get().getPassword(),
                authority,
                true,
                true,
                true,
                true
        );
    }
}
