package br.com.santos.william.crud.task

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.LocalDate
import java.time.LocalDateTime

@Repository
interface TaskRepository : JpaRepository<Task, Long> {

    fun findByDeadlineLessThanAndClosedAtIsNull(deadline: LocalDateTime): Collection<Task>

}