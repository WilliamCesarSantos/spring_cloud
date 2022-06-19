package br.com.santos.william.crud

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
@EnableDiscoveryClient
@EnableJpaRepositories
class CrudApplication

fun main(args: Array<String>) {
	runApplication<CrudApplication>(*args)
}
