package io.arcotech.benchmarks.routes

import io.arcotech.benchmarks.bloomfilter.BloomFilterSingleton
import io.arcotech.benchmarks.bloomfilter.toBooleanArray
import io.arcotech.benchmarks.data.*
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*

import io.ktor.routing.*
import io.ktor.routing.delete
import io.ktor.serialization.json

fun Application.configureRouting(){
    install (ContentNegotiation){
        json()
    }
    install(StatusPages) {
        exception<Throwable> { cause ->
            call.respond(HttpStatusCode.InternalServerError, cause.localizedMessage)
        }
    }
    install(CORS) {
        // Configuração básica permitindo todas as origens, métodos e cabeçalhos.
        anyHost()
        allowCredentials = true
        allowNonSimpleContentTypes = true
        methods += HttpMethod.Put
        methods += HttpMethod.Delete
        methods += HttpMethod.Options
        header(HttpHeaders.Authorization)
        header(HttpHeaders.AccessControlAllowHeaders)
        header(HttpHeaders.AccessControlAllowOrigin)
        header(HttpHeaders.AccessControlAllowMethods)
    }

    routing{

        route("users/emails-bloomfilter"){
            get{
                val bloomFilter = BloomFilterSingleton.bloomFilter
                call.respond(BitSetWrapper(bloomFilter.getBitArray().toBooleanArray()))
            }
        }
        route("users"){
            get{
                val users = getFirst20()
                call.respond(users)
            }
            post{
                val newUser = call.receive<User>()

                val bloomFilter = BloomFilterSingleton.bloomFilter

                println(newUser)
                if (bloomFilter.contains(newUser.email)){
                    println(newUser.email)
                    call.respond(HttpStatusCode.BadRequest, "User already exists!")
                } else{
                    // Verificar se existe em bloomFilter
                    newUser.id = insertUser(newUser)
                    call.respond(HttpStatusCode.Created, newUser)
                }

            }
        }

        route("users/{id}") {
            get {
                val userId = call.parameters["id"]?.toIntOrNull()
                val user = getById(userId!!)
                    if (user != null) {
                        call.respond(user)
                    } else {
                        call.respond(HttpStatusCode.NotFound)
                    }
            }

            delete {
                val userId = call.parameters["id"]?.toIntOrNull()
                val user = getById(userId!!)
                if (user != null) {
                    delete(user.id)
                    call.respond(HttpStatusCode.NoContent)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }


        }

    }

}