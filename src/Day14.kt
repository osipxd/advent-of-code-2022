fun main() {
    val testInput = readInput("Day14_test")
    val input = readInput("Day14")

    "Part 1" {
        part1(testInput) shouldBe 24
        answer(part1(input))
    }

    "Part 2" {
        part2(testInput) shouldBe 93
        answer(part2(input))
    }
}

private fun part1(input: List<List<List<Int>>>) = simulateSandFalling(input, hasFloor = false)
private fun part2(input: List<List<List<Int>>>) = simulateSandFalling(input, hasFloor = true)

private const val AIR = '.'
private const val ROCK = '#'
private const val SAND = 'o'

private const val SAND_SOURCE_COL = 500

private const val WIDTH = SAND_SOURCE_COL * 2
private const val HEIGHT = WIDTH / 2

private fun simulateSandFalling(structures: List<List<List<Int>>>, hasFloor: Boolean): Int {
    val cave = Array(HEIGHT) { Array(WIDTH) { AIR } }

    // Add rock structures from the input to the cave
    var lastRow = 0
    for (structure in structures) {
        structure
            // Remember last row, it will be helpful later
            .onEach { lastRow = maxOf(lastRow, it.row) }
            .windowed(2) { (from, to) ->
                // Either row, or column will be equal, so we just draw a line, not a square
                for (row in (from.row..to.row).fixOrder()) {
                    for (col in (from.col..to.col).fixOrder()) {
                        cave[row][col] = ROCK
                    }
                }
            }
    }

    // Add floor if we need it
    if (hasFloor) {
        lastRow += 2
        for (col in 0 until WIDTH) cave[lastRow][col] = ROCK
    }

    // Here we will count how many sand blocks have fallen already
    var sandBlocks = 0

    // Simulate sand falling block-by-block
    var row = 0
    var col = SAND_SOURCE_COL
    while (row < lastRow && cave[row][col] == AIR) {
        when {
            cave[row + 1][col] == AIR -> row++                // Move down
            cave[row + 1][col - 1] == AIR -> { row++; col-- } // Move down-left
            cave[row + 1][col + 1] == AIR -> { row++; col++ } // Move down-right

            // It is impossible to move down, so just place sand block here and stop falling
            else -> {
                sandBlocks++
                cave[row][col] = SAND

                // Reset row and column to spawn next sand block
                row = 0
                col = SAND_SOURCE_COL
            }
        }
    }

    return sandBlocks
}

/** Returns [this] range in right order. */
private fun IntRange.fixOrder() = if (start > endInclusive) endInclusive..start else this

// "498,4 -> 498,6 -> 496,6" => [[498, 4], [498, 6], [496, 6]]
private fun readInput(name: String) =
    readLines(name).map { line -> line.split(" -> ").map { it.splitInts(",") } }

// Extensions to hide 0 and 1 indices from code
private val List<Int>.col: Int get() = get(0)
private val List<Int>.row: Int get() = get(1)
