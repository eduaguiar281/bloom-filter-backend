package io.arcotech.benchmarks.data

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

fun configureDatabase() {
    Database.connect(
        url = "jdbc:sqlserver://localhost:1433;databaseName=mydatabase;encrypt=true;trustServerCertificate=true",
        driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver",
        user = "sa",
        password = "s3nhaEhPa55w0rD"
    )
}


fun createTables() {
    transaction {
        addLogger(StdOutSqlLogger)
        SchemaUtils.create(Users)
    }
}

fun insertUsers(users: List<User>){
    configureDatabase()
    transaction {
        addLogger(StdOutSqlLogger)
        Users.batchInsert(users) { user ->
            this[Users.username] = user.username
            this[Users.email] = user.email
        }
    }
}


fun insertUser(user: User): Int{
    configureDatabase()
    return transaction {
        Users.insertAndGetId {
            it[username] = user.username
            it[email] = user.email
        }.value
    }
}

fun hasUsers(): Boolean{
    configureDatabase()
    val qtUsers = transaction {
        addLogger(StdOutSqlLogger)
        Users.selectAll().count()
    }
    return qtUsers > 0L
}

private fun getAllUsers(): List<User> {
    configureDatabase()
    return transaction {
        addLogger(StdOutSqlLogger)
        Users.selectAll().map { row ->
            User(
                id = row[Users.id].value,
                username = row[Users.username],
                email = row[Users.email]
            )
        }
    }
}

fun getById(id: Int): User?{
    configureDatabase()
    return transaction{
        addLogger(StdOutSqlLogger)
        Users.select {Users.id eq id}
            .map{ row ->
                User(
                    id = row[Users.id].value,
                    username = row[Users.username],
                    email = row[Users.email])
            }.firstOrNull()
    }
}

fun getFirst20(): List<User>{
    configureDatabase()
    return transaction {
        addLogger(StdOutSqlLogger)
        Users.selectAll().limit(20).map { row ->
            User(
                id = row[Users.id].value,
                username = row[Users.username],
                email = row[Users.email]
            )
        }
    }
}

fun delete(id: Int){
    configureDatabase()
    transaction {
        addLogger(StdOutSqlLogger)
        Users.deleteWhere { Users.id eq id }
    }
}

fun hasEmail(email:String): Boolean{
    configureDatabase()
    val qtUsers = transaction {
        Users.select { Users.email eq email }
            .count()
    }
    return qtUsers > 0L
}


fun getEmails(): List<String>{
    return getAllUsers().map { u -> u.email }
}