package br.com.santos.william.crud.jwt

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtTokenFilter @Autowired constructor(
        private val provider: JwtTokenProvider
) : GenericFilterBean() {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        provider.resolveToken(request as HttpServletRequest)
                ?.also { token ->
                    if (provider.validateToken(token)) {
                        provider.authentication(token)
                                ?.also { SecurityContextHolder.getContext().authentication = it }
                    }
                }

        chain.doFilter(request, response)
    }
}