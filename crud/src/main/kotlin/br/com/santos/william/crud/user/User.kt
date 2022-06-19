package br.com.santos.william.crud.user

import java.io.Serializable
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.validation.constraints.NotNull

@Entity
class User : Serializable {

    @Id
    var id: Long? = null

    @NotNull
    @Column(length = 80)
    var name: String? = null

    @Column(length = 240)
    var username: String? = null

    @Column(length = 240)
    var email: String? = null
}