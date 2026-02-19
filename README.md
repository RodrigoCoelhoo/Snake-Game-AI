# 🐍 Snake Game AI

A Java-based Snake game that supports player-controlled and AI-controlled snakes, dynamic and static obstacles and both graphical and text-based interfaces.

## 🔎 Overview

The project is an object-oriented Java implementation of the classic Snake game enhanced with AI and dynamic obstacles. The architecture separates game logic, UI and allow modular updates and easy experimentation with game strategies.

## 🔧 Features
- Configurable arena dimensions, snake head size, tile size and food size.
- **Dynamic and static** obstacles that **rotate** or move in the arena.
- **Collision detection** for obstacles, snake, and arena borders.
- Graphical and Text-based UI support.
- **Leaderboard system** for high scores.
- **AI-Controlled Snake** using **A\*** pathfinding.

> [!NOTE]  
> This is and academic project. At the time of development, Artificial Intelligence topics such as A* and other pathfinding algorithms had not yet been covered in the course. The AI-Controlled snake was a challenge made by the professors, therefore, the implementation may contain limitations or bugs.
> - For best AI performance, use head size = 1 and static obstacles (isDynamic = false).
> - The AI may fail with larger heads or dynamic obstacles due to inefficiencies in the pathfinding algorithm.

## 📂 Project Structure

| Component | Description |
|---------|-------------|
| **SnakeGame** | Main game controller responsible for initializing components, updating the game state, and handling interactions such as collisions and food generation. |
| **Arena** | Represents the game board as a grid of `Tile` objects, managing occupied and free spaces. |
| **Snake** | Manages the snake body as a queue, handles movement, growth, and self-collision detection. |
| **Food** | Randomly generated in valid, unoccupied areas of the arena. |
| **Obstacles** | Loads static and dynamic obstacles from a `.txt` file and handles collision detection. |
| **Leaderboard** | Maintains a ranking of top players based on score and time. |
| **Time** | Utility class for tracking and formatting game duration (`mm:ss:SSS`). |
| **IGameUI** | UI abstraction using the Strategy Pattern, supporting multiple UI implementations. |
| **IGameStrategy** | AI abstraction implementing A* pathfinding for autonomous snake movement. |

## 🕹️ Controls

#### Graphical UI (``GraphicalGameUI``)
- Arrow Keys: ``↑ (UP)`` ``←  (LEFT)`` ``↓ (DOWN)`` ``→ (RIGHT)``

#### Text-based UI (``TextGameUI``)
- Input on terminal: ``W (UP)`` ``A (LEFT)`` ``S (DOWN)`` ``D (RIGHT)``

## 🧪 Setup and Running

1. Clone the repository:
```bash
git clone https://github.com/RodrigoCoelhoo/Snake-Game-AI.git
cd Snake-Game-AI/src
```

2. Compile the project:
```bash
javac -d ../bin Cliente.java data/*.java geofig/*.java game/*.java strategy/*.java ui/*.java
```

3. Run the game:
```bash
java -cp ../bin Cliente
```

## ⚙️ Configuration (``Client class``)

<p align="center">
  <img src="docs/Client.java.png" alt="Client config" />
</p>

### 🧱 Obstacles

The game supports static and dynamic obstacles, fully configurable through an external text file.
This design allows obstacles to be added, removed, or modified without changing the game code.

#### 📄 Obstacles Configuration File

Obstacles are loaded from a .txt file where each field must be separated by a tab character (\t).

> [!IMPORTANT]
> - The tab character (\t) separates arguments.
> - The space character (" ") separates values inside an argument.
> - The first line is a header and is ignored by the parser.
> - Each subsequent line defines a single obstacle.

```pgsql
Class name | Constructor Argument | isDynamic | Point of rot. | Angle of rot.
```

#### 📌 Example
```pgsql
Class name	Constructor Argument					isDynamic	Point of rot.	Angle of rot.
Triangulo	100 100 222 100 150 195					false		null				0
Poligono	800 500 900 500 900 600 800 600			false		900 500				25
Poligono	450 300 500 400 600 450 500 500 450 600	true		null				15
```

| Field                    | Description                                                                                                                           |
| ------------------------ | ------------------------------------------------------------------------------------------------------------------------------------- |
| **Class name**           | Name of the geometric class used to create the obstacle (e.g. `Triangulo`, `Poligono`). The class must exist in the `geofig` package. |
| **Constructor Argument** | A sequence of coordinates used to build the polygon. Passed directly to the class constructor.                                        |
| **isDynamic**            | `true` → obstacle rotates every update<br>`false` → obstacle is static                                                                |
| **Point of rotation**    | Rotation center (`x y`). If set to `null`, the polygon’s centroid is used automatically.                                              |
| **Angle of rotation**    | Rotation angle (in degrees) applied on each update cycle (only if the obstacle is dynamic).                                           |

## 📚 Documentation

- **Javadoc**: Available in the [`JAVADOC/`](./JAVADOC) folder.
- **UML Diagram**: Available at [`docs/UML.jpg`](./docs/UML.jpg).