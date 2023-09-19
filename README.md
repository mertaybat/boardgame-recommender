# Boardgame Recommender

This project contains a software to recommend boardgames based on input boardgame
names for games that you want to base recommendations on. Data that is used for generating
the recommendations is based on Boardgamegeek data.

You can get this data, for all the ranked boardgamegeek games, from the following repository:

https://github.com/mertaybat/boardgamegeek-downloader

The json data is located and need to be updated at
```
app/src/main/resources/boardgames-custom.json
```

## How to use

In order to build, from the root perform

```bash
./gradlew clean build jar
```

You can find the executable at

```
app/build/libs/bg-rec.jar
```

To get help run

```bash
java -jar bg-rec.jar -h
```

An example run looks like

```bash
java -jar bg-rec.jar -g "nemesis" -n 10
```

This will output 10 recommended games based on the game Nemesis.

An example run based on multiple inputs looks like

```bash
java -jar bg-rec.jar -g "nemesis, brass: birmingham" -n 15
```

This will output 15 recommended games based on the games Nemesis and Brass: Birmingham.

## Algorithm

The software uses the following algorithm

- Using boardgamegeek data, generate a set of *game mechanics*, *categories* and *families* for all the games that were input
- Go through all the remaining boardgames and find matches from their *game mechanics*, *categories* and *families* with the sets that were generated in the previous step
- Sort the results in number of matches
- For games with equal number of matches sort in their boardgame rank
- Print out the first *n* results which is a user input. This defaults to 10 if it is not provided as input.

Games whose names do not match any of the games in the data set are also printed out to inform the user.


