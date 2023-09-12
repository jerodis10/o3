package o3.security.sec;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static o3.security.sec.UserPermission.*;

@RequiredArgsConstructor
public enum UserRole {

    MEMBER(Sets.newHashSet(USER)),
    ADMINTRAINEE(Sets.newHashSet(MEMBER_READ, USER)),
    ADMIN(Sets.newHashSet(MEMBER_READ, MEMBER_WRITE, USER));

    private final Set<UserPermission> permissions;

    public Set<UserPermission> getPermissions() {
        return permissions;
    }

    public Set<SimpleGrantedAuthority> grantedAuthorities() {
        Set<SimpleGrantedAuthority> permissions = getPermissions()
                .stream()
                .map(permission -> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));

        return permissions;
    }
}
