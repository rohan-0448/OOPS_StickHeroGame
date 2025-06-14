# Stick Hero Game Clone

This repository contains a clone of the popular mobile game "Stick Hero". The game aims to help the hero cross by stretching a stick from one platform to another. The longer the stick, the further the hero can go. The game challenges your precision and timing skills.

## Features

- Simple and intuitive gameplay mechanics.
- Score tracking to keep a record of your high scores.
- Responsive design for a smooth experience on different screen sizes.
- Engaging graphics and animations to enhance user experience.
- 
## How to Play the Game

- Hold and Release the Mouse to Extend the Stick.
- Click the Mouse Once to Flip the Character onto the Cherry.
- Require More Than 3 Cherries to Revive.
- For a Consecutive Streak of 15 Successful Moves, Players are Awarded an Extra 50 Points.

## Development

This game is built using Java, JavaFX, and Scene Builder. Please feel free to explore the code and make any improvements or modifications.

## Contributing

If you would like to contribute to the development of this game, follow these steps:

1. Fork the repository.
2. Create a new branch for your feature or bugfix.
3. Make your changes.
4. Submit a pull request with a description of your changes.

## Code Explanation

1. **Initial Scene**: Loads with three options:
   - Start
   - Exit
   - Reload

2. **Main Game Screen**: Includes three major point systems:
   - Cherries: Used to revive the character.
   - Normal Points.
   - Bonus Points: Earned after a streak of 15 successful moves.

3. **Pause Option**: Allows the player to:
   - Save and Exit
   - Resume

4. **Game Over**: If the user undershoots or overshoots the stick, the character falls, leading to a scene with three options:
   - Restart
   - Revive (only if cherries >= 3)
   - Main Menu

## Acknowledgments

Inspired by the original Stick Hero game by Ketchapp. Enjoy the game and happy coding!

## Important Concepts Applied

- **Inheritance**: Utilized in the game's class structure.
- **Serialization and Deserialization**: Implemented for saving and loading games.
- **Threading**: Applied to maintain a count for successful move streaks.
- **Singleton/Design Patterns**: Used in the Admin and StickHero classes to ensure only one instance exists.
- **JUnit**: Used for testing the game logic and components.
