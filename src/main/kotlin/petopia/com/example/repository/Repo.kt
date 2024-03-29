package com.example.repository

import com.example.data.model.User
import com.example.data.table.UserTable
import com.example.repository.DatabaseFactory.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class Repo {
    suspend fun addUser(user: User) {
        dbQuery {
            UserTable.insert { ut ->
                ut[UserTable.email] = user.email
                ut[UserTable.name] = user.userName
                ut[UserTable.hashPassword] = user.hashPassword
            }
        }
    }


    suspend fun findUserByEmail(email: String) = dbQuery {
        UserTable.select { UserTable.email.eq(email) }
            .map { rowToUser(it) }
            .singleOrNull()
    }

    private fun rowToUser(row: ResultRow?): User? {
        if (row == null) {
            return null
        }

        return User(
            email = row[UserTable.email],
            userName = row[UserTable.name],
            hashPassword =  row[UserTable.hashPassword]
        )
    }
}