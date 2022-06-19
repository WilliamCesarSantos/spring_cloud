package br.com.santos.william.crud.email

import java.io.Serializable

data class EmailDto(
        val to: List<String>,
        val subject: String,
        val content: String
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as EmailDto

        if (to != other.to) return false
        if (subject != other.subject) return false
        if (content != other.content) return false

        return true
    }

    override fun hashCode(): Int {
        var result = to.hashCode()
        result = 31 * result + subject.hashCode()
        result = 31 * result + content.hashCode()
        return result
    }
}
