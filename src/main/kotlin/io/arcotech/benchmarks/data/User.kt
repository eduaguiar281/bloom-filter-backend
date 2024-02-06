package io.arcotech.benchmarks.data

import kotlinx.serialization.Serializable

@Serializable
data class User (var id: Int, val username: String, val email:String)