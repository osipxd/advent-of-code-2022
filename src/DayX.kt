fun main() {
    val day = "14"
    val testInput = readInput("Day${day}_test")
    val input = readInput("Day$day")

    "Part 1" {
        part1(testInput) shouldBe 0
        measureAnswer { part1(input) }
    }

    //"Part 2" {
    //    part2(testInput) shouldBe 0
    //    measureAnswer { part2(input) }
    //}
}

private fun part1(input: List<String>): Int = TODO()

private fun part2(input: List<String>): Int = TODO()

private fun readInput(name: String) = readLines(name)
