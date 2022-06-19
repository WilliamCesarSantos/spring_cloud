package br.com.santos.william.email.message

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.amqp.core.{Exchange, ExchangeBuilder}
import org.springframework.amqp.support.converter.{Jackson2JsonMessageConverter, MessageConverter}
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.context.annotation.Bean
import org.springframework.stereotype.Component

@Component
@Autowired
class MessageConfig(@Value("${message.amqp.exchange:EMAIL_EXCHANGE}") val exchange: String) {

  @Bean
  def declareExchange(): Exchange = ExchangeBuilder.directExchange(exchange)
    .durable(true).build();

  @Bean
  def declareMessageConverter(): MessageConverter = new Jackson2JsonMessageConverter(objectMapper())

  @Bean
  def objectMapper(): ObjectMapper = new ObjectMapper().registerModule(DefaultScalaModule)
}
