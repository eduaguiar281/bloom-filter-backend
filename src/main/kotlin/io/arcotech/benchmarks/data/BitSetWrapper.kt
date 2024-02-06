package io.arcotech.benchmarks.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class BitSetWrapper(
    @SerialName("bitArray") val bitArray: BooleanArray
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BitSetWrapper

        if (!bitArray.contentEquals(other.bitArray)) return false

        return true
    }

    override fun hashCode(): Int {
        return bitArray.contentHashCode()
    }
}