package com.example.loginapi

import kotlinx.coroutines.Dispatchers
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

@Serializable
data class UserLoginModel(val gmail: String, val password: Int)

class UserLoginSchema(database: Database) {
    object Users : Table() {
        val id = integer("id").autoIncrement()
        val gmail = varchar("gmail", length = 50)
        val password = integer("password")

        override val primaryKey = PrimaryKey(id)
    }

    init {
        transaction(database) {
            SchemaUtils.create(Users)
        }
    }

    suspend fun create(user: UserLoginModel): Int = dbQuery {
        Users.insert {
            it[gmail] = user.gmail
            it[password] = user.password
        }[Users.id]
    }

    suspend fun read(id: Int): UserLoginModel? {
        return dbQuery {
            Users.selectAll()
                .where { Users.id eq id }
                .map { UserLoginModel(it[Users.gmail], it[Users.password]) }
                .singleOrNull()
        }
    }

    suspend fun delete(id: Int): Boolean {
        return dbQuery {
            Users.deleteWhere { Users.id eq id } > 0
        }
    }

    private suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }


}



