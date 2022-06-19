package br.com.santos.william.crud.task

import br.com.santos.william.crud.DATE_DISPLAY_PATTERN
import br.com.santos.william.crud.email.Email
import br.com.santos.william.crud.email.EmailRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Component
class TaskSchedule @Autowired constructor(
        val repository: TaskRepository,
        val emailRepository: EmailRepository
) {

    var dateTimeFormatter = DateTimeFormatter.ofPattern(DATE_DISPLAY_PATTERN)!!
    private val log: Logger = LoggerFactory.getLogger(TaskSchedule::class.java)

    @Transactional
    @Scheduled(cron = "\${schedule.late.task:10 * * * * ?}")
    fun discoveryLateTask() {
        log.info("Running schedule to discovery which tasks are late")

        repository.findByDeadlineLessThanAndClosedAtIsNull(LocalDateTime.now())
                .parallelStream()
                .forEach {
                    log.debug("Found {} task late, but not flagged. Flagging task to LATE.", it.id)
                    it.status = TaskStatus.LATE
                    repository.save(it)
                    emailRepository.save(makeEmail(it))
                }
    }

    private fun makeEmail(task: Task) = Email(
                to = listOf(task.user!!.email!!),
                subject = "Task ${task.title} is late",
                content = "The task \"${task.title}\" is late. The task should have been finished in ${task.deadline?.format(dateTimeFormatter)}"
        )

}