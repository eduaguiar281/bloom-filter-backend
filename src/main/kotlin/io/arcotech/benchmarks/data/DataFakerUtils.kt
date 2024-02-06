package io.arcotech.benchmarks.data

import com.github.javafaker.Faker

fun generateUsers(count: Int) :List<User> {
    val faker = Faker()

    return List(count) {
        val email = faker.internet().emailAddress()
        val username = email.substring(0, email.indexOf("@"))
        User(
            id = 0,
            username = username,
            email = email
        )
    }
}
