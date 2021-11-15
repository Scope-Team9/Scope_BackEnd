package com.studycollaboproject.scope.security;


import com.studycollaboproject.scope.exception.BadRequestException;
import com.studycollaboproject.scope.exception.ErrorCode;
import com.studycollaboproject.scope.exception.NoAuthException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Base64;
import java.util.Date;

@RequiredArgsConstructor
@Component
@Slf4j
public class JwtTokenProvider {


    @Value("${jwtToken}")
    private String secretKey; // 암호 키 설정

    SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256; // 알고리즘 선택


    // 토큰 유효시간 설정               24시간
    private long tokenUseTime = 24 * 60 * 60 * 1000L;

    private final UserDetailsServiceImpl userDetailsService;

    // 객체 초기화, secretKey를 Base64로 인코딩
    @PostConstruct // bean 이 여러번 초기화되는 상황 방지용
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    private Key getSigninKey() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String createToken(String snsId) {
        Claims claims = Jwts.claims().setSubject(snsId);
        // claim : JWT payload 에 저장되는 정보단위
        Date now = new Date();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenUseTime)) // set Expire Time
                .signWith(getSigninKey(), signatureAlgorithm)// 사용할 암호화 알고리즘과 시크릿 키
                .compact();
    }

    // 클라이언트 Header에서 가져온 token 값을 이용해 유저의 정보를 가져와서 담은 후 밑에 있는 getUserPk 요기에 보내서
    // 토큰에 숨겨져 있는 회원의 정보를 추출해와 UserDetails 객체에 담은 후 UsernamePasswordAuthenticationToken 이용해서 Authentication에 담겨서
    // SecurityContextHolder.getContext에 최종적으로 담겨서 로그아웃 하기 전까지 계속 사용되어진다.
    public Authentication getAuthentication(String token) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(this.getUserPk(token));
        log.info("=============getAuthentication============");
        log.info("[{}] snsId={}", MDC.get("UUID"), userDetails.getUsername());
        log.info("=============getAuthentication============");
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        JwtParser parser = Jwts.parserBuilder().setSigningKey(getSigninKey()).build();
        Jws<Claims> claims = parser.parseClaimsJws(token);
        return claims.getBody().getSubject();
    }

    // Request의 Header에서 token 값을 가져옴. "authorization" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        final String header = request.getHeader("authorization");
        if (header != null && (header.toLowerCase().indexOf("bearer ") == 0))
            return header.substring(7);
        else return header;
    }

    // 토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String jwtToken) {
        if (jwtToken == null) {
            log.info("토큰이 존재하지 않습니다.");
            throw new BadRequestException(ErrorCode.NO_TOKEN_ERROR);
        }
        try {
            JwtParser parser = Jwts.parserBuilder().setSigningKey(getSigninKey()).build();
            parser.parseClaimsJws(jwtToken);

            return true;

        } catch (ExpiredJwtException e) {
            log.info("만료된 토큰");
            throw new BadRequestException(ErrorCode.TOKEN_EXPIRATION_ERROR);

        } catch (Exception e) {
            log.info("정상적인 토큰이 아닙니다.");
            throw new NoAuthException(ErrorCode.NO_AUTHENTICATION_ERROR);
        }

    }
}
