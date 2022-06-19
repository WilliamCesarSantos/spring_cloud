package br.com.santos.william.auth.login

import java.io.Serializable
import javax.validation.constraints.NotNull

data class LoginDto(
        @field:NotNull
        val username: String? = null,
        @field:NotNull
        val password: String? = null
) : Serializable