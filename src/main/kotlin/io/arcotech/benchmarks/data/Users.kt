package io.arcotech.benchmarks.data

import org.jetbrains.exposed.dao.id.IntIdTable

object Users: IntIdTable() {
    val username = varchar("username", 50)
    val email = varchar("email", 80)
}