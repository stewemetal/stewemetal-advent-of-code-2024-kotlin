enum class Direction(
    val rowOffset: Int,
    val columnOffset: Int
) {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1),
    UP_LEFT(-1, -1),
    UP_RIGHT(-1, 1),
    DOWN_LEFT(1, -1),
    DOWN_RIGHT(1, 1);
}

fun main() {

    operator fun List<String>.get(row: Int, column: Int): Char? =
        try {
            this[row][column]
        } catch (e: IndexOutOfBoundsException) {
            null
        }

    operator fun List<String>.get(row: Int, column: Int, direction: Direction): Char? =
        this[row + direction.rowOffset, column + direction.columnOffset]

    fun check(
        input: List<String>,
        lastChar: Char?,
        currentRow: Int,
        currentColumn: Int,
        direction: Direction,
    ): Int {
        val nextExpectedChar = when (lastChar) {
            'X' -> 'M'
            'M' -> 'A'
            'A' -> 'S'
            'S' -> return 1
            else -> return 0
        }

        val currentChar = input[currentRow, currentColumn] ?: return 0

        return when {
            currentChar == nextExpectedChar -> {
                check(
                    input = input,
                    lastChar = currentChar,
                    currentRow = currentRow + direction.rowOffset,
                    currentColumn = currentColumn + direction.columnOffset,
                    direction = direction,
                )
            }

            else -> 0
        }
    }

    fun part1(input: List<String>): Int {

        var sum = 0

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, character ->
                sum += if (character == 'X') {
                    Direction.entries.sumOf {
                        check(
                            input = input,
                            lastChar = character,
                            currentRow = rowIndex + it.rowOffset,
                            currentColumn = columnIndex + it.columnOffset,
                            direction = it,
                        )
                    }
                } else 0
            }
        }

        return sum
    }

    fun part2(input: List<String>): Int {
        var sum = 0

        input.forEachIndexed { rowIndex, row ->
            row.forEachIndexed { columnIndex, character ->
                sum += if (character == 'A') {
                    if (
                        input[rowIndex, columnIndex, Direction.UP_LEFT] == null ||
                        input[rowIndex, columnIndex, Direction.UP_RIGHT] == null ||
                        input[rowIndex, columnIndex, Direction.DOWN_LEFT] == null ||
                        input[rowIndex, columnIndex, Direction.DOWN_RIGHT] == null
                    ) {
                        0
                    } else if (
                        ((input[rowIndex, columnIndex, Direction.UP_LEFT] == 'M' && input[rowIndex, columnIndex, Direction.DOWN_RIGHT] == 'S') || (input[rowIndex, columnIndex, Direction.UP_LEFT] == 'S' && input[rowIndex, columnIndex, Direction.DOWN_RIGHT] == 'M')) &&
                        ((input[rowIndex, columnIndex, Direction.UP_RIGHT] == 'M' && input[rowIndex, columnIndex, Direction.DOWN_LEFT] == 'S') || (input[rowIndex, columnIndex, Direction.UP_RIGHT] == 'S' && input[rowIndex, columnIndex, Direction.DOWN_LEFT] == 'M'))
                    ) {
                        1
                    } else { 0 }
                } else 0
            }
        }

        return sum
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 18)
    check(part2(testInput) == 9)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day04")
    part1(input).println()
    part2(input).println()
}
