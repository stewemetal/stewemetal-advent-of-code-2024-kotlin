fun main() {

    fun List<String>.asRules() =
        buildMap<Int, MutableSet<Int>> {
            this@asRules.forEach { line ->
                val (page, beforePage) = line.split("|").map { it.toInt() }

                if (this[page] == null) {
                    this[page] = mutableSetOf(beforePage)
                } else {
                    this.getValue(page).add(beforePage)
                }
            }
        }

    fun List<String>.asPrints() = map { it.split(',').map { it.toInt() } }

    fun List<Int>.fits(rules: Map<Int, Set<Int>>): Boolean {
        val activeRules = rules.filter {
            this.contains(it.key)
        }

        forEachIndexed { index, page ->
            val pagesShouldBeAfter = activeRules[page] ?: emptySet()
            if(take(index).intersect(pagesShouldBeAfter).isNotEmpty()) return false
        }

        return true
    }

    fun part1(input: List<String>): Int {
        val rules = input.takeWhile { it.isNotBlank() }.asRules()
        val prints = input.takeLastWhile { it.isNotBlank() }.asPrints()

        return prints.sumOf {
            if (it.fits(rules)) it[it.size / 2] else 0
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 143)
//    check(part2(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day05")
    part1(input).println()
//    part2(input).println()
}

