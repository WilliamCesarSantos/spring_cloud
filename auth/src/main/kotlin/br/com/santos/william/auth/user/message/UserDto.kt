package br.com.santos.william.auth.user.message

data class UserDto(
        val id: Long,
        val name: String,
        val username: String,
        val email: String
)
