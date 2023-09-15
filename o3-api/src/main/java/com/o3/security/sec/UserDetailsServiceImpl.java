package com.o3.security.sec;

import com.o3.member.domain.Member;
import com.o3.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
        String id = "";
        String password = "";
        for (UserRole role : UserRole.values()) {
            if (member.isPresent()) {
                if(member.get().getRole().equals(role.name())) authority = role.grantedAuthorities();
                id = member.get().getLoginId();
                password = member.get().getPassword();
            }
        }

        return new UserDetailsImpl(
                id,
                password,
                authority,
                true,
                true,
                true,
                true
        );
    }
}
