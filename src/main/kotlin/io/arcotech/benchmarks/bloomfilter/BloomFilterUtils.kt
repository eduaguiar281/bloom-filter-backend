package io.arcotech.benchmarks.bloomfilter

import java.util.*

fun BitSet.toBooleanArray(): BooleanArray {
    val booleanArray = BooleanArray(size())
    for (i in 0 until size()) {
        booleanArray[i] = get(i)
    }
    return booleanArray
}