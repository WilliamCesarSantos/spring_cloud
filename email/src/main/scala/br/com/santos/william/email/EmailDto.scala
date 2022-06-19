package br.com.santos.william.email

class EmailDto(val to: Array[String], val subject: String, val content: String) extends Serializable
