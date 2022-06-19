package br.com.santos.william.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration
import org.springframework.cloud.client.discovery.EnableDiscoveryClient

@SpringBootApplication(exclude = Array(classOf[GsonAutoConfiguration]))
@EnableDiscoveryClient
class BootConfig
