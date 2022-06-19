package br.com.santos.william.auth.user

import org.springframework.security.core.userdetails.UserDetails
import java.io.Serializable

class UserDetailsImpl constructor(private val delegate: User) : UserDetails, Serializable {

    override fun getAuthorities() = delegate.authorities

    override fun getPassword() = delegate.password

    override fun getUsername() = delegate.username

    override fun isAccountNonExpired() = delegate.isAccountNonExpired

    override fun isAccountNonLocked() = delegate.isAccountNonLocked

    override fun isCredentialsNonExpired() = delegate.isCredentialsNonExpired

    override fun isEnabled() = delegate.isEnabled

}