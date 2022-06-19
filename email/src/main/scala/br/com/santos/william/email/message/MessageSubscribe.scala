package br.com.santos.william.email.message

import br.com.santos.william.email.EmailDto
import br.com.santos.william.email.send.EmailService
import org.slf4j.LoggerFactory
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
@Autowired
class MessageSubscribe (val emailService: EmailService){

  lazy val logger = LoggerFactory.getLogger(getClass)

  @RabbitListener(queues = Array("${message.queue.email.send:EMAIL_SENDER_QUEUE}"))
  def consuming(emailDto: EmailDto) = {
      logger.debug(s"Received new message with e-mail to send")
      emailService.sendMail(emailDto)
  }

}
