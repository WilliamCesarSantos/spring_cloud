package br.com.santos.william.crud.email

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Email(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @ElementCollection
    @Column(name = "email_to")
    val to: List<String>,

    @Column(length = 240)
    val subject: String,

    @Column(length = 4000)
    val content: String,

    var send: Boolean = false,

    @CreationTimestamp
    var createdAt: LocalDateTime? = null,

    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

) : Serializable