package br.com.santos.william.auth.message

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Sort
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Component
class MessageSchedule @Autowired constructor(
        val repository: MessageRepository,
        val publisher: MessagePublisher
) {

    private val log: Logger = LoggerFactory.getLogger(MessageSchedule::class.java)

    @Transactional
    @Scheduled(cron = "\${schedule.message.sender:10 * * * * ?}")
    fun senderMessages() {
        log.info("Running schedule to send message")

        repository.findBySend(false, Sort.by(Sort.Direction.ASC, "createdAt"))
                .forEach {
                    publisher.publish(it.topic, it.data)
                    it.send = true
                    repository.save(it)
                }
    }

}