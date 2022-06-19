package br.com.santos.william.auth.message

import br.com.santos.william.auth.TRANSACTION_LOCK_TIMEOUT
import br.com.santos.william.auth.TRANSACTION_LOCK_TIMEOUT_DEFAULT_VALUE
import org.springframework.data.domain.Sort
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.QueryHints
import org.springframework.stereotype.Repository
import java.util.stream.Stream
import javax.persistence.LockModeType
import javax.persistence.QueryHint

@Repository
interface MessageRepository : JpaRepository<Message, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @QueryHints(value = [QueryHint(name = TRANSACTION_LOCK_TIMEOUT, value = TRANSACTION_LOCK_TIMEOUT_DEFAULT_VALUE)])
    fun findBySend(send: Boolean, sort: Sort): Stream<Message>

}