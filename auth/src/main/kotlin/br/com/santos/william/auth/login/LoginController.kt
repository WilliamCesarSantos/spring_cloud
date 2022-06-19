package br.com.santos.william.auth.login

import br.com.santos.william.auth.jwt.JwtTokenProvider
import br.com.santos.william.auth.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.naming.AuthenticationException
import javax.validation.Valid

@RestController
@RequestMapping("/login")
class LoginController @Autowired constructor(
        private val authenticationManager: AuthenticationManager,
        private val provider: JwtTokenProvider,
        private val userRepository: UserRepository
) {

    @PostMapping(
            produces = [MediaType.APPLICATION_JSON_VALUE],
            consumes = [MediaType.APPLICATION_JSON_VALUE])
    fun login(@RequestBody @Valid user: LoginDto): ResponseEntity<Any> {
        try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(user.username, user.password))
            userRepository.findByUsername(user.username!!)?.let {
                val token = provider.newToken(it.username!!, it.roles())
                return ResponseEntity.ok(mapOf<Any, Any>(
                        "username" to it.username!!,
                        "token" to token
                ))
            } ?: throw UsernameNotFoundException("User is invalid")
        } catch (exception: AuthenticationException) {
            throw BadCredentialsException("User is invalid")
        }
    }

}