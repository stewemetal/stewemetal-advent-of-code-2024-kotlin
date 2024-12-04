import State.*

enum class State {
    INVALID,
    M,
    U,
    L,
    OPENING_PARENTHESIS,
    NUMBER_FIRST,
    COMMA,
    NUMBER_SECOND,
}

fun main() {
    fun part1(input: List<String>): Int {
        return input.sumOf { line ->
            var state = INVALID
            var currentCharIndex = 0
            var a = ""
            var b = ""
            val multiplicationResults = mutableListOf<Int>()

            while (currentCharIndex <= line.lastIndex) {
                val currentChar = line[currentCharIndex]
                when {
                    currentChar == 'm' -> {
                        state = if (state == INVALID) {
                            M
                        } else {
                            a = ""
                            b = ""
                            INVALID
                        }
                    }

                    currentChar == 'u' -> {
                        state = if (state == M) {
                            U
                        } else {
                            a = ""
                            b = ""
                            INVALID
                        }
                    }

                    currentChar == 'l' -> {
                        state = if (state == U) {
                            L
                        } else {
                            a = ""
                            b = ""
                            INVALID
                        }
                    }

                    currentChar == '(' -> {
                        state = if (state == L) {
                            OPENING_PARENTHESIS
                        } else {
                            a = ""
                            b = ""
                            INVALID
                        }
                    }

                    currentChar.isDigit() -> {
                        when (state) {
                            OPENING_PARENTHESIS -> {
                                state = NUMBER_FIRST
                                a += currentChar.toString()
                            }

                            COMMA -> {
                                state = NUMBER_SECOND
                                b += currentChar.toString()
                            }

                            NUMBER_FIRST -> a += currentChar.toString()

                            NUMBER_SECOND -> b += currentChar.toString()

                            else -> {
                                a = ""
                                b = ""
                                state = INVALID
                            }
                        }
                    }

                    currentChar == ',' -> {
                        when (state) {
                            NUMBER_FIRST -> {
                                state = COMMA
                            }

                            else -> {
                                a = ""
                                b = ""
                                INVALID
                            }
                        }
                    }

                    currentChar == ')' -> {
                        when (state) {
                            NUMBER_SECOND -> {
                                multiplicationResults += a.toInt() * b.toInt()
                                a = ""
                                b = ""
                                state = INVALID
                            }

                            else -> {
                                a = ""
                                b = ""
                                INVALID
                            }
                        }
                    }

                    else -> {
                        a = ""
                        b = ""
                        state = INVALID
                    }
                }

                currentCharIndex++
            }

            multiplicationResults.sum()
        }
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 161)
//    check(part2(testInput) == 1)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day03")
    part1(input).println()
//    part2(input).println()
}
