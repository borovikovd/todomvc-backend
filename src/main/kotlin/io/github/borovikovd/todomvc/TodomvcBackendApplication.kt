package io.github.borovikovd.todomvc

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TodomvcBackendApplication

fun main(args: Array<String>) {
    runApplication<TodomvcBackendApplication>(*args)
}
