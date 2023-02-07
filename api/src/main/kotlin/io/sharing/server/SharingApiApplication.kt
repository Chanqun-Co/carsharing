package io.sharing.server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class SharingApiApplication

fun main(args: Array<String>) {
    runApplication<SharingApiApplication>(*args)
}
