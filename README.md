I2CS_EX3 - PACMAN Project Overview
This project is a Java-based implementation of a Pacman game that focuses on Pathfinding and Logic-Based Decision Making. The goal is to create an intelligent Pacman that can navigate a maze, collect food efficiently, and avoid ghosts using structured logic. The game is built on a 2D grid system where every position is defined by (x,y) coordinates.

Installation & How to Play
Requirement: Ensure Java 23 is installed on your machine.

Run: Open a terminal in the project folder and run: java -jar pacman.jar

Controls:

Space Bar: Start or Pause the game.

'L' Key: Change the level (0 to 4), which increases the number of ghosts.

'C' Key: Toggle Cyclic Mode (allows wrapping around edges).

Manual Control: Use W, A, X, D to move if the autonomous mode is disabled.

The Strategic Controller (Ex3Algo.java)
The core of the project is the controller that decides where Pacman should go. Instead of "guessing," it follows a strict priority list to survive and win.

1. Decision Strategy (move function)

The controller analyzes the board in every frame and picks the best action based on these rules:

Hunting: If a ghost is "blue" (edible), Pacman will chase it down for extra points.

Targeted Eating: If no ghosts are edible, it looks for the most "crowded" area of food to clear the map faster.

Escape: If a dangerous ghost gets too close, Pacman drops everything and runs to the safest nearby tile.

2. Danger Zones (createSafeBoard)

The controller creates a "mental map" where it treats the area around ghosts as if they were walls. By doing this, the pathfinding logic automatically avoids those areas, keeping Pacman at a safe distance.

3. Escape Maneuvers (runToSafety)

If Pacman is cornered, this function checks all four directions. It scores each direction based on how far it leads from ghosts and how many "escape routes" (openings) it has, choosing the one that offers the best chance of survival.

The Navigation System (Map.java)
This is the "GPS" of the game. It handles the logic of moving through the maze using graph-search techniques.

1. Finding Paths (shortestPath & allDistance)

The map uses BFS (Breadth-First Search) to navigate.

allDistance: This function starts from Pacman and spreads out like a ripple in water, marking how many steps it takes to reach every single point on the board.

shortestPath: Once the distances are known, this function follows the numbers backward from the target to Pacman to find the quickest route.

2. The Wrap-Around Effect (Cyclic Mode)

The map is "Cyclic," meaning the edges are connected.

getNeighbours: This function calculates the tiles next to Pacman. If he is at the far right edge, it tells the controller that the "right" neighbor is actually the far left edge of the screen.

getDist: This calculates the distance by checking if it's shorter to walk across the screen or just wrap around the edge to reach the goal.

Error Handling & Logs
The system includes a game.err file. If the logic fails to find a path or the game hits an unexpected state, it records the current coordinates and the board state. This allows for clear debugging to see exactly why a decision was made at any given time.


https://github.com/user-attachments/assets/192ef9c7-75c3-48ec-bc81-d04482331592

🎨 Server - Implementation of Visual & Temporal Features(The Artistic Bonuses)


1. Color-Space Mapping (The Aesthetic Basis)

The numerical grid was mapped to a specific color palette to establish visual hierarchy and immediate state recognition:

Blue Hex-Code Mapping: Applied to all wall segments (ID: 1). This defines the static "boundaries" of the subspace.

Pink/Red Vector Styling: Applied to the GhostCL entities. This distinguishes enemy vectors from the background, allowing for immediate pathing recognition.

Yellow Rendering: Assigned to the PacmanAgent to signify the primary controlled entity.

2. Temporal Logic (The Game Clock)

Instead of a static snapshot, a Time-State Manager was integrated.

The Clock: A sequential counter that tracks the duration of the session. Technically, this adds a temporal dimension to the game state, allowing for time-based scoring and movement speed calculations.

3. Termination Logic (The Game-Over Trigger)

The "Game Over" feature was implemented as a logical interrupt:

Collision Detection: When the coordinate vector of Pacman p(x,y) equals the coordinate vector of a Ghost g(x,y), the system triggers a state transition.

Signal Output: The engine stops all movement calculations and renders a "Game Over" overlay, resetting the active session parameters.


<img width="585" height="596" alt="צילום מסך 2026-01-20 ב-21 58 08" src="https://github.com/user-attachments/assets/fc59de25-bdf0-4cf8-b932-283b2f2508a0" />






