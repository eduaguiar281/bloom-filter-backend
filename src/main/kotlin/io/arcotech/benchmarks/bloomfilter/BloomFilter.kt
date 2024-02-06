package io.arcotech.benchmarks.bloomfilter

import java.util.*
import kotlin.math.abs

class BloomFilter(private val numElements: Int, private val numHashFunctions: Int) {
    private val bitArray: BitSet = BitSet(numElements)

    private fun getHashValues(input: String): IntArray {
        val hashValues = IntArray(numHashFunctions)
        val hash1 = input.hashCode()
        val hash2 = hash1

        for (i in 0 until numHashFunctions) {
            hashValues[i] = abs((hash1 + i * hash2) % numElements)
        }

        return hashValues
    }

    fun add(input: String) {
        val hashValues = getHashValues(input)

        for (hash in hashValues) {
            bitArray.set(hash)
        }
    }

    fun contains(input: String): Boolean {
        val hashValues = getHashValues(input)

        for (hash in hashValues) {
            if (!bitArray.get(hash)) {
                return false
            }
        }
        return true
    }

    fun getBitArray(): BitSet{
        return bitArray
    }

}