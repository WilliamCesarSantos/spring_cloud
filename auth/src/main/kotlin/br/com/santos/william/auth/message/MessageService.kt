package br.com.santos.william.auth.message

import br.com.santos.william.auth.exception.ConflictException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated

@Service
class MessageService @Autowired constructor(
    val repository: MessageRepository
){

    fun insert(@Validated message: Message): Message {
        message.id?.let { throw ConflictException() }
        return repository.save(message)
    }

    fun update(@Validated message: Message): Message =
            repository.save(message)

}