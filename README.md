# LuckyNine

A CLI-based game written in Java with AI opponents and tie-breaking rounds.


## Features
- Round-based gameplay with tie-breakers
- Single player vs AI (1-9 opponents)
- AI decision-making (draw if hand value < 7)
- UTF-8 encoding is auto-enabled on launch for Windows using `chcp 65001`


## How to run

### Option 1: Download pre-compiled `.jar`
1. Go to [Releases](https://github.com/djsahagun15/LuckyNine/releases) page.
2. Download the latest release.
3. Run from the terminal:
```
java -jar LuckyNine.jar
```

### Option 2: Compile from source
1. Clone this repository.
2. Compile all `.java` files:
```
javac *.java
```
3. Run the game:
```
java 
```