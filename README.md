# Jotto
### About
[Jotto](http://en.wikipedia.org/wiki/Jotto) is a logic-oriented word game, originally invented by Morton M. Rosenfeld in 1955. Jotto is written entirely in Java. The graphical component of the game is implemented using the Swing GUI toolkit. The computer chooses a five-letter target word, which is in the dictionary, isn't a proper noun, and isn't a contraction. The target word may contain repeated letters. In ten or less guesses, the player then repeatedly guesses words in an attempt to guess the target word. The guesses also follow the same restrictions as the target word: in the dictionary, five letters, not a proper noun, may contain repeated characters. After each guess, the computer will indicate two values: the number of exact matches and the number of partial matches. An exact match is where target[i] = guess[i] \(i.e.: correct letter and position). A partial match is where target[i] = guess[j], i ≠ j (i.e.: correct letter, incorrect position). Exact matches are found before partial matches and a given letter participates in at most one match. The game ends when either the player quits, guesses the target, or runs out of guesses.

### Screenshots
#### Gameplay Screen
![Jotto_Screenshot1](https://cloud.githubusercontent.com/assets/7763904/11166776/77ed45f2-8b13-11e5-881c-830385df0cd1.png)
#### End of Game Screen
![Jotto_Screenshot2](https://cloud.githubusercontent.com/assets/7763904/11166777/7c55fa26-8b13-11e5-8029-690c14270b47.png)

### Compilation
```Bash
make
```

### Clean Build
```Bash
make clean
```

### Execution
```Bash
java Jotto
```

> Note: The compilation and execution step can be combined into a single step by running: **make run**.

### Usage


### License
Jotto is licensed under the [MIT license](https://github.com/elailai94/Jotto/blob/master/LICENSE.md).
