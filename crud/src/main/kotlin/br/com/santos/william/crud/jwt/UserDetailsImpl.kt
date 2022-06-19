package br.com.santos.william.crud.jwt

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserDetailsImpl : UserDetails {

    override fun getPassword() = ""

    override fun getUsername() = ""

    override fun isAccountNonExpired() = true

    override fun isAccountNonLocked() = true

    override fun isCredentialsNonExpired() = true

    override fun isEnabled() = true

    override fun getAuthorities() = emptyList<GrantedAuthority>()
}