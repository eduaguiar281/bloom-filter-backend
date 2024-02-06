package io.arcotech.benchmarks

import io.arcotech.benchmarks.bloomfilter.BloomFilter
import io.arcotech.benchmarks.data.*
import kotlinx.benchmark.Scope
import org.openjdk.jmh.annotations.*
import java.util.concurrent.TimeUnit

@State(Scope.Benchmark)
@Warmup(iterations = 1)
@Measurement(iterations = 2, time = 1, timeUnit = TimeUnit.NANOSECONDS)
open class BloomfilterBenchmark {

    @Param("eduaguiar281@gmail.com", "eduaguiar281@hotmail.com", "eduardo.aguiar@arcotech.io", "casey.barton@yahoo.com", "ebony.abshire@gmail.com")
    lateinit var email: String
    private var bloomFilter: BloomFilter = BloomFilter(1000000, 10)

    private var emailList: List<String> = listOf()

    @Setup
    fun setup(){
        configureDatabase()
        createTables()
        if (!hasUsers()){
            insertUsers(generateUsers(600000))
        }
        emailList = getEmails()
        emailList.forEach { e -> bloomFilter.add(e) }
    }

    @Benchmark
    fun emailExistsByBloomFilter() {
        bloomFilter.contains(email)
    }

    @Benchmark
    fun emailExistsByDatabase() {
        hasEmail(email)
    }
}