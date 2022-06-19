package br.com.santos.william.crud.message

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class MessagePublisher @Autowired constructor(
        val rabbitTemplate: RabbitTemplate,
        @Value("\${message.amqp.exchange:TASK_EXCHANGE}") val exchange: String
) {

    private val log: Logger = LoggerFactory.getLogger(MessagePublisher::class.java)

    fun publish(topic: String, data: Any) {
        log.info("publishing message on topic: $topic")
        rabbitTemplate.convertAndSend(exchange, topic, data)
    }

}