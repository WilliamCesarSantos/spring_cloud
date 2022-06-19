package br.com.santos.william.email.send

import br.com.santos.william.email.EmailDto
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service

@Service
@Autowired
class EmailService(val sender: JavaMailSender) {

  lazy val logger = LoggerFactory.getLogger(getClass)

  def sendMail(dto: EmailDto) = {
    logger.info(s"Sending new email to ${dto.to: _*}")
    val message = new SimpleMailMessage
    message.setTo(dto.to: _*)
    message.setSubject(dto.subject)
    message.setText(dto.content)
    sender.send(message)
    logger.debug("Sent e-mail")
  }

}
