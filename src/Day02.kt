import kotlin.math.absoluteValue

fun main() {
    fun isSafe(levels: List<Int>): Boolean {
        var increasingSequence = true
        for (i in 0..<levels.lastIndex) {
            val a = levels[i]
            val b = levels[i + 1]

            val diff = a - b
            if (diff.absoluteValue > 3 || diff == 0) return false


            if (i == 0) {
                increasingSequence = diff < 0
                continue
            }

            if (diff > 0) {
                if (increasingSequence) {
                    return false
                }
            } else {
                if (!increasingSequence) {
                    return false
                }
            }
        }

        return true
    }

    fun part1(input: List<String>): Int {
        return input.map { line ->
            val levels = line
                .split(" ")
                .map { it.toInt() }

            isSafe(levels)
        }.count { it }
    }

    fun part2(input: List<String>): Int {
        return input.map { line ->
            val levels = line
                .split(" ")
                .map { it.toInt() }

            var isSafe = isSafe(levels)

            if (!isSafe) {
                var removedIndex = 0

                while (!isSafe && removedIndex <= levels.lastIndex) {
                    isSafe = isSafe(levels.toMutableList().apply { removeAt(removedIndex) })
                    removedIndex++
                }
            }

            isSafe
        }.count { it }    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 2)
    check(part2(testInput) == 4)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day02")
    part1(input).println()
    part2(input).println()
}
