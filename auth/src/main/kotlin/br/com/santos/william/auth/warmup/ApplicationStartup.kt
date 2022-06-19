package br.com.santos.william.auth.warmup

import br.com.santos.william.auth.permission.Permission
import br.com.santos.william.auth.permission.PermissionRepository
import br.com.santos.william.auth.user.User
import br.com.santos.william.auth.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Component

@Component
class ApplicationStartup @Autowired constructor(
        val permissionRepository: PermissionRepository,
        val userRepository: UserRepository,
        val enconder: BCryptPasswordEncoder
): ApplicationListener<ApplicationReadyEvent?> {

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val permission = permissionRepository.findByDescription("Admin")
                ?: Permission(description = "Admin").also { permissionRepository.save(it) }

        userRepository.findByUsername("admin@local.com")
                ?: User(
                        username = "admin@local.com",
                        name = "Administrator",
                        isAccountNonExpired = true,
                        isAccountNonLocked = true,
                        isCredentialsNonExpired = true,
                        isEnabled = true,
                        password = enconder.encode("123"),
                        authorities = setOf(permission)
                ).also { userRepository.save(it) }
    }
}