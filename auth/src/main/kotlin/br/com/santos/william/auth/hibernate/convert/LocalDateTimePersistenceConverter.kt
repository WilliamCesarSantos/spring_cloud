package br.com.santos.william.auth.hibernate.convert

import java.sql.Timestamp
import java.time.LocalDateTime
import javax.persistence.AttributeConverter
import javax.persistence.Converter

@Converter(autoApply = true)
class LocalDateTimePersistenceConverter : AttributeConverter<LocalDateTime, Timestamp> {

    override fun convertToDatabaseColumn(value: LocalDateTime?) =
            value?.let { Timestamp.valueOf(it) } ?: null

    override fun convertToEntityAttribute(value: Timestamp?) =
            value?.toLocalDateTime()

}