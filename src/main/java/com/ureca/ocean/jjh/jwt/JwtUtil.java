package com.ureca.ocean.jjh.jwt;

import io.jsonwebtoken.Jwts;
import jakarta.annotation.PostConstruct;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@Getter
@Setter
@Slf4j
public class JwtUtil {
    @Value("${myapp.jwt.secret}")
    private String secretKeyStr;
    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        secretKey = new SecretKeySpec(secretKeyStr.getBytes(StandardCharsets.UTF_8),
                Jwts.SIG.HS256.key().build().getAlgorithm());
    }


    // 토큰에서 username 추출 (DB 조회 없이)
    public String getUsernameFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    // 토큰 서명 및 만료 검증
    public boolean validateToken(String token) {
        log.info("validating token...");
        try {
            return !Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload()
                    .getExpiration()
                    .before(new Date());
        } catch (Exception e) {
            return false; // 검증 실패 시 false 반환
        }
    }
}
