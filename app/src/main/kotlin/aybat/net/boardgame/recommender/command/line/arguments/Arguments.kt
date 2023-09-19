package aybat.net.boardgame.recommender.command.line.arguments

import com.beust.jcommander.Parameter

class Arguments {
    @Parameter(names = ["-g", "--games"], description = "Comma separated value of games you like. The names are case insensitive, however, they need to match exactly the game names in the boardgamegeek dataset.", required = true)
    var games: List<String> = arrayListOf()

    @Parameter(names = ["-n", "--number"], description = "Number of outputs", required = false)
    var numberToOutput: Int? = 10

    @Parameter(names = ["--help", "-h"], help = true, description = "Prints options")
    var help = false
}
