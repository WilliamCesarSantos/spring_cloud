package br.com.santos.william.crud.task

import br.com.santos.william.crud.user.User
import com.fasterxml.jackson.annotation.JsonIgnore

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.io.Serializable
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime
import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
class Task : Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null

    @NotNull
    @Column(length = 80)
    var title: String? = null

    @Column(length = 240)
    var description: String? = null

    @JsonIgnore
    @CreationTimestamp
    val createdAt: LocalDateTime = LocalDateTime.now(ZoneOffset.UTC)

    @JsonIgnore
    @UpdateTimestamp
    var updatedAt: LocalDateTime? = null

    @NotNull
    @Column
    var deadline: LocalDateTime? = null

    @Column
    var closedAt: LocalDateTime? = null

    @NotNull
    @Enumerated(value = EnumType.STRING)
    var status: TaskStatus = TaskStatus.OPEN

    @NotNull
    @ManyToOne
    var user: User? = null
}