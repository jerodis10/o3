package com.o3.security.sec;


import com.google.common.collect.Sets;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public enum UserRole {

    MEMBER(Sets.newHashSet(UserPermission.USER)),
    ADMINTRAINEE(Sets.newHashSet(UserPermission.MEMBER_READ, UserPermission.USER)),
    ADMIN(Sets.newHashSet(UserPermission.MEMBER_READ, UserPermission.MEMBER_WRITE, UserPermission.USER));

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
