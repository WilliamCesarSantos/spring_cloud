package br.com.santos.william.auth.hibernate.convert

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import javax.persistence.AttributeConverter

class JpaConverterJson @Autowired constructor(
        private val mapper: ObjectMapper
) : AttributeConverter<Any, String> {

    override fun convertToDatabaseColumn(value: Any?): String? =
            value?.let { mapper.writeValueAsString(it) } ?: null

    override fun convertToEntityAttribute(value: String?): Any? =
            value?.let { mapper.readValue(it, Any::class.java) } ?: null
}