package br.com.santos.william.auth.jwt

import br.com.santos.william.auth.BEARER_TOKEN_LABEL
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*
import javax.annotation.PostConstruct
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest

@Service
class JwtTokenProvider @Autowired constructor(
        @Value("\${security.jwt.token.secret-key}") val secretKey: String,
        @Value("\${security.jwt.token.expire-length}") val expireLength: Long,
        @Qualifier("UserService") val userDetailsService: UserDetailsService
) {

    private lateinit var encryptKey: String

    @PostConstruct
    fun init() {
        encryptKey = Base64.getEncoder().encodeToString(secretKey.toByteArray(Charsets.UTF_8))
    }

    fun newToken(username: String, roles: Collection<String>): String {
        val subject = Jwts.claims().setSubject(username)
                .also { it["roles"] = roles }

        return Jwts.builder()
                .setClaims(subject)
                .setIssuedAt(Date())
                .setExpiration(Date(Date().time + expireLength))
                .signWith(SignatureAlgorithm.HS256, encryptKey)
                .compact()
    }

    fun authentication(token: String): Authentication? {
        return usernameByToken(token)
                ?.let { userDetailsService.loadUserByUsername(it) }
                ?.let { UsernamePasswordAuthenticationToken(it, "", it.authorities) }
    }

    fun resolveToken(request: HttpServletRequest) =
            request.getHeader(HttpHeaders.AUTHORIZATION)
                ?.takeIf { it.startsWith(BEARER_TOKEN_LABEL) }
                ?.let { it.substring(BEARER_TOKEN_LABEL.length, it.length) }

    fun validateToken(token: String) =
            Jwts.parser()
                .setSigningKey(encryptKey)
                .parseClaimsJws(token)
                    .body.expiration.after(Date())


    private fun usernameByToken(token: String) = Jwts.parser()
            .setSigningKey(encryptKey)
            .parseClaimsJws(token)
            .body.subject

}