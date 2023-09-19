package aybat.net.boardgame.recommender.model

data class Options(
        val games: List<String>,
        val numberOfOutputs: Int = 10
)
