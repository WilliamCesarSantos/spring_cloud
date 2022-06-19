package br.com.santos.william.auth.permission

import org.springframework.security.core.GrantedAuthority
import java.io.Serializable
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Permission(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:NotNull
    @field:Column(nullable = false, length = 255)
    var description: String? = null

) : Serializable, GrantedAuthority {
    override fun getAuthority() = description!!
}