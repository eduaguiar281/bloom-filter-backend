package io.arcotech.benchmarks.bloomfilter

object BloomFilterSingleton {
    private const val numElements: Int = 2000000
    private const val numHashFunctions: Int = 10

    val bloomFilter = BloomFilter(numElements, numHashFunctions)
}