package com.o3.member.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpHeaders;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class MemberConstants {

    public static final String AUTHORIZATION_HEADER = HttpHeaders.AUTHORIZATION;

}
