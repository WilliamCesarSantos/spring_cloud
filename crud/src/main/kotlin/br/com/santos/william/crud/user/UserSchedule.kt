package br.com.santos.william.crud.user

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class UserSchedule @Autowired constructor(
    val repository: UserRepository
) {

    private val log: Logger = LoggerFactory.getLogger(UserSchedule::class.java)

    @RabbitListener(queues = ["\${message.queue.user:USER_QUEUE}"])
    fun notifyUser(user: User) {
        log.info("Receiver a new user with username: ${user.username}")
        repository.save(user)
    }

}