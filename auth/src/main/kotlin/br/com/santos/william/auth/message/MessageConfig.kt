package br.com.santos.william.auth.message

import org.springframework.amqp.core.Exchange
import org.springframework.amqp.core.ExchangeBuilder
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.amqp.support.converter.MessageConverter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class MessageConfig @Autowired constructor(
        @Value("\${message.amqp.exchange.user:USER_EXCHANGE}") val exchange: String
) {

    @Bean
    fun declareExchange(): Exchange = ExchangeBuilder.directExchange(exchange)
            .durable(true).build()

    @Bean
    fun declareMessageConverter(): MessageConverter = Jackson2JsonMessageConverter()
}