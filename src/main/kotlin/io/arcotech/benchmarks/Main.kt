package io.arcotech.benchmarks

import io.arcotech.benchmarks.bloomfilter.BloomFilterSingleton
import io.arcotech.benchmarks.data.getEmails
import io.arcotech.benchmarks.routes.configureRouting
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*


fun Application.module() {
    configureRouting()
}

fun main(args: Array<String>) {

    val bloomFilter = BloomFilterSingleton.bloomFilter
    val allEmails = getEmails()
    allEmails.forEach {
            e -> bloomFilter.add(e)
    }
    println("emails adicionado a bloomfilter com sucesso")
    embeddedServer(Netty, port = 8090, module = Application::module).start(wait = true)
    println("Iniciando servidor")
}