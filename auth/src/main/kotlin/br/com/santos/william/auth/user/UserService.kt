package br.com.santos.william.auth.user

import br.com.santos.william.auth.exception.ConflictException
import br.com.santos.william.auth.message.Message
import br.com.santos.william.auth.message.MessageService
import br.com.santos.william.auth.user.message.UserDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.data.domain.Pageable
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.validation.annotation.Validated
import javax.transaction.Transactional

@Service
@Qualifier(value = "UserService")
class UserService @Autowired constructor(
    val repository: UserRepository,
    val messageService: MessageService,
    @Value("\${message.user.updated:USER}") val topic: String
) : UserDetailsService {

    override fun loadUserByUsername(username: String?) = repository.findByUsername(username!!)?.let { UserDetailsImpl(it) }
            ?: throw UsernameNotFoundException("Username with login $username not found!!!")

    fun list(pageable: Pageable) = repository.findAll(pageable)

    fun get(id: Long) = repository.findById(id)

    @Transactional
    fun insert(@Validated user: User): User {
        user.id?.let { throw ConflictException() }
        user.password = BCryptPasswordEncoder().encode(user.password)
        return repository.save(user)
                .also { sendMessage(it) }
    }

    @Transactional
    fun update(@Validated user: User): User {
        user.password = BCryptPasswordEncoder().encode(user.password)
        return repository.save(user)
                .also { sendMessage(it) }
    }

    private fun sendMessage(user: User) {
        val dto = UserDto(id = user.id!!, name = user.name!!, username = user.username!!, email = user.username!!)
        messageService.insert(Message(topic = topic, data = dto))
    }
}