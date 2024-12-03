import kotlin.math.absoluteValue

fun main() {
    fun part1(input: List<String>): Int {
        val firstItems = mutableListOf<Int>()
        val secondItems = mutableListOf<Int>()
        input.forEach {
            val (first, second) = it.split("\\s+".toRegex())
            firstItems.add(first.toInt())
            secondItems.add(second.toInt())
        }
        val sortedFirst = firstItems.sorted()
        val sortedSecond = secondItems.sorted()

        return sortedFirst.mapIndexed { index, value ->
            (sortedSecond[index] - value).absoluteValue
        }.sum()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 11)
//    check(part2(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    part1(input).println()
    part2(input).println()
}
