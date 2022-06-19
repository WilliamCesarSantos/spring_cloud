package br.com.santos.william.auth.message

import br.com.santos.william.auth.hibernate.convert.JpaConverterJson
import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
data class Message(

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  @CreationTimestamp
  var createdAt: LocalDateTime? = null,

  @UpdateTimestamp
  var sentAt: LocalDateTime? = null,

  @NotNull
  @Column
  var send: Boolean = false,

  @NotNull
  @Column
  var topic: String,

  @NotNull
  @Column(length = 4000)
  @Convert(converter = JpaConverterJson::class)
  val data: Any

) : Serializable