fun main() {

    fun Direction.turn() =
        when (this) {
            Direction.LEFT -> Direction.UP
            Direction.DOWN -> Direction.LEFT
            Direction.RIGHT -> Direction.DOWN
            Direction.UP -> Direction.RIGHT
            else -> throw Exception("Invalid direction")
        }

    operator fun List<String>.get(row: Int, column: Int): Char? =
        try {
            this[row][column]
        } catch (e: IndexOutOfBoundsException) {
            null
        }

    operator fun List<String>.get(row: Int, column: Int, direction: Direction): Char? =
        this[row + direction.rowOffset, column + direction.columnOffset]

    fun Pair<Int, Int>.move(direction: Direction): Pair<Int, Int> =
        Pair(first + direction.rowOffset, second + direction.columnOffset)

    fun isInArea(
        input: List<String>,
        currentPosition: Pair<Int, Int>,
    ): Boolean {
        return input[currentPosition.first, currentPosition.second] != null
    }

    fun part1(input: List<String>): Int {
        val visited = mutableSetOf<Pair<Int, Int>>()
        var currentPosition: Pair<Int, Int> = Pair(0, 0)
        var currentDirection: Direction = Direction.UP

        input.forEachIndexed { rowIndex, row ->
            val columnIndex = row.indexOf("^")
            if (columnIndex != -1) {
                currentPosition = Pair(rowIndex, columnIndex)
                return@forEachIndexed
            }
        }

        do {
            visited += currentPosition
            val lookaheadPosition = currentPosition.move(currentDirection)
            if (input[lookaheadPosition.first, lookaheadPosition.second] == '#') {
                currentDirection = currentDirection.turn()
            }
            currentPosition = currentPosition.move(currentDirection)
        } while (isInArea(input, currentPosition))


        return visited.size
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day06_test")
    check(part1(testInput).apply { println() } == 41)
//    check(part2(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day06")
    part1(input).println()
//    part2(input).println()
}
