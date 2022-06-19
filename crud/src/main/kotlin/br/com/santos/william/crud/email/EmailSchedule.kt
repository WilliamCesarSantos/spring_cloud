package br.com.santos.william.crud.email

import br.com.santos.william.crud.message.MessagePublisher
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class EmailSchedule @Autowired constructor(
        val repository: EmailRepository,
        val publisher: MessagePublisher,
        @Value("\${message.topic.email.sender:SENDER_EMAIL}") val topic: String
) {

    private val log: Logger = LoggerFactory.getLogger(EmailSchedule::class.java)

    @Transactional
    @Scheduled(cron = "\${schedule.send.email:10 * * * * ?}")
    fun sendEmail() {
        log.info("Running schedule to publish the late tasks")
        repository.findBySend(false, Sort.by(Sort.Direction.ASC, "createdAt"))
                .forEach {
                    publisher.publish(topic, makeDto(it))
                    it.send = true
                    repository.save(it)
                }
    }

    private fun makeDto(email: Email) =
            EmailDto(to = email.to, subject = email.subject, content = email.content)
}