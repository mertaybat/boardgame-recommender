package aybat.net.boardgame.recommender

import aybat.net.boardgame.recommender.command.line.arguments.Arguments
import aybat.net.boardgame.recommender.model.Options
import aybat.net.boardgame.recommender.service.Recommender
import com.beust.jcommander.JCommander
import com.beust.jcommander.ParameterException


fun run(options: Options) {
    val recommender = Recommender()
    val analysisResponse = recommender.recommend(options)
    if (analysisResponse.nonMatchingResults.isNotEmpty()) {
        println("Following games are not found. Please check their names and retry")
        analysisResponse.nonMatchingResults.forEach(::println)
        println("-------------------------------")
    }
    if (analysisResponse.matchingResults.isEmpty()) {
        println("No match has been found")
    } else {
        println("Following matches have been found")
        analysisResponse.matchingResults.forEach(::println)
    }
}


fun main(args: Array<String>) {
    val arguments = Arguments()
    val jCommander = JCommander.newBuilder()
            .addObject(arguments)
            .build()
    try {
        jCommander.parse(*args)
    } catch (ex: ParameterException) {
        jCommander.usage()
        return
    }
    if (arguments.help) {
        jCommander.usage()
        return
    }
    val options = Options(arguments.games.map { it.trim() }, arguments.numberToOutput ?: 10)
    run(options)
}
