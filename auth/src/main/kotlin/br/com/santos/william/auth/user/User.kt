package br.com.santos.william.auth.user

import br.com.santos.william.auth.permission.Permission
import java.io.Serializable
import javax.persistence.*
import javax.validation.Valid
import javax.validation.constraints.Email
import javax.validation.constraints.NotNull

@Entity
data class User(

    @field:Id
    @field:GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @field:Email
    @field:NotNull
    @field:Column(unique = true)
    var username: String? = null,

    @field:NotNull
    @field:Column
    var name: String? = null,

    @field:NotNull
    @field:Column
    var password: String? = null,

    @field:NotNull
    @field:Column
    var isEnabled: Boolean = true,

    @field:NotNull
    @field:Column
    var isCredentialsNonExpired: Boolean = false,

    @field:NotNull
    @field:Column
    var isAccountNonExpired: Boolean = false,

    @field:NotNull
    @field:Column
    var isAccountNonLocked: Boolean = false,

    @field:NotNull
    @field:ManyToMany(fetch = FetchType.EAGER)
    @field:JoinTable(name = "user_permission",
            joinColumns = [JoinColumn(name = "user_id")],
            inverseJoinColumns = [JoinColumn(name= "permission_id")]
    )
    @field:Valid
    var authorities: Set<Permission>? = null

) : Serializable {

    fun roles() : Set<String> = authorities!!.map { it.description!! }.toSet()
}