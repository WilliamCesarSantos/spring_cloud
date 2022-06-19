package br.com.santos.william.auth.permission

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PermissionRepository : JpaRepository<Permission, Long> {

    fun findByDescription(description: String): Permission?

}