package aybat.net.boardgame.recommender.service

import aybat.net.boardgame.recommender.model.AnalysisResponse
import aybat.net.boardgame.recommender.model.BoardGame
import aybat.net.boardgame.recommender.model.MatchingResult
import aybat.net.boardgame.recommender.model.Options
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import kotlin.streams.toList

class Recommender {

    fun recommend(options: Options): AnalysisResponse {
        val data: List<BoardGame> = getData()
        val gameMechanics = mutableSetOf<String>()
        val categories = mutableSetOf<String>()
        val families = mutableSetOf<String>()
        val matches = mutableMapOf<BoardGame, Int>()

        val listOfInputBoardgames = data.filter { item -> options.games.map { it.lowercase() }.contains(item.name.lowercase()) }.toSet()
        listOfInputBoardgames.forEach { boardGame ->
            boardGame.category?.let { categories.add(it) }
            boardGame.gameMechanics?.let { gameMechanics.add(it) }
            boardGame.families?.let { families.addAll(it) }
        }
        val allOtherGames = data.subtract(listOfInputBoardgames)
        val listOfGamesNotFound = options.games.filterNot { inputName -> listOfInputBoardgames.map { it.name.lowercase() }.contains(inputName.lowercase()) }.toSet()

        if (categories.isEmpty() && gameMechanics.isEmpty() && families.isEmpty()) {
            return AnalysisResponse(emptyList(), listOfGamesNotFound)
        }

        allOtherGames.forEach { boardGame ->
            var numberOfMatches = 0
            if (categories.contains(boardGame.category)) {
                numberOfMatches++
            }

            if (gameMechanics.contains(boardGame.gameMechanics)) {
                numberOfMatches++
            }
            boardGame.families?.forEach {
                if (families.contains(it)) {
                    numberOfMatches++
                }
            }
            matches[boardGame] = numberOfMatches
        }
        val matchingResults = matches.toList().stream()
                .filter { (_, value) -> value > 0 }
                .sorted { (bg1, v1), (bg2, v2) ->
                    if (v1 == v2) {
                        bg1.boardGameRank.compareTo(bg2.boardGameRank)
                    } else {
                        v2.compareTo(v1)
                    }
                }
                .map { MatchingResult(it.first.name, it.second) }
                .toList()
                .take(options.numberOfOutputs)

        return AnalysisResponse(matchingResults, listOfGamesNotFound)
    }

    private fun getData(): List<BoardGame> {
        val objectMapper = ObjectMapper().registerKotlinModule()
        val dataStream = this.javaClass.classLoader.getResourceAsStream("boardgames-custom.json")
        return objectMapper.readValue(dataStream, Array<BoardGame>::class.java).toList()
    }
}
