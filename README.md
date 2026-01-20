I2CS_EX3 - PACMAN
Brief
This project is an implementation of a Pacman game that demonstrates AI logic and game server coordination. It is built as a Java application that utilizes a 2D grid (raster) system where Pacman must navigate a maze, collect food, and avoid ghosts. The project relies on foundational classes like Map and Index2D to handle spatial logic, such as finding the shortest path between points or calculating distances.

Installation & Usage
The project requires Java 23 to be installed on your computer.

Download: Obtain the pacman.jar file from the repository.

Run: Open your terminal and type: java -jar pacman.jar

Controls:

SPACE BAR: Toggles between Play and Pause.

Manual Movement: Use w (up), a (left), x (down), and d (right).

Settings: Press L to change levels (0 to 4) and C to toggle "Cyclic Mode" (where you can wrap around the screen edges).

Project Structure (Core Logic)
1. Pacman AI (Ex3Algo.java)

This class contains the "brain" of Pacman. It decides which direction to move in every frame.

The Strategy: The algorithm prioritizes staying alive. It first checks if any ghosts are "eatable" and close enough to hunt. If not, it looks for the closest food while maintaining a "safe distance" from dangerous ghosts.

Safe Map: The code creates a "virtual" map where ghosts and the area around them are treated as walls, preventing Pacman from accidentally walking into danger.

Food Search: It doesn't just go to the nearest food; it looks for "hot spots" where a lot of food is clustered together to get a higher score faster.

2. Map & Navigation (Map.java & Index2D.java)

These files handle the geometry of the game.

Shortest Path: Using an algorithm called BFS (Breadth-First Search), the map calculates the exact sequence of steps needed to get from Pacman to a target while avoiding blue walls.

Distances: The system can calculate the distance to every single point on the board at once, which helps in deciding which food is actually the "closest".

Cyclic Logic: The map is "wrapped". Moving off the right edge will teleport Pacman to the left edge, and the AI is programmed to recognize these shortcuts.

3. Game Management (Ex3Main.java & GameInfo.java)

Ex3Main: This is the entry point that starts the game loop. It listens for your keyboard presses and tells the game when to update.

GameInfo: This file holds your settings, such as your ID, the level you want to play, and whether the game should be in AI or Manual mode.

Error Handling (game.err)
The system includes a logging mechanism. If the game crashes or if Pacman cannot find any food, the error details are written to a file called game.err. This acts like a "black box" that helps developers understand what went wrong during a specific run.
