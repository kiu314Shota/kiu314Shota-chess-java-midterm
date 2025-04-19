# Java Chess Game – Refactored & Enhanced Version (2025)

This project is a fully functional two-player chess game developed in Java. It includes a graphical user interface (GUI), complete chess rules implementation (including checkmate, stalemate, and pawn promotion), and thorough unit testing.

The codebase was heavily refactored to follow the Model-View-Controller (MVC) design pattern and encapsulate game logic properly. Unit tests for all pieces and game rules have been added and pass successfully.

## Features

- Complete implementation of all chess pieces and legal moves
- Check, checkmate, and stalemate detection
- Pawn promotion to any piece
- Move validation using king safety logic
- Piece dragging with GUI feedback
- Visual checkmate and game-over detection
- Fully refactored MVC architecture
- 100% test coverage for core game logic

## Technologies Used

- Java 17+
- Java Swing (GUI)
- JUnit 5 (unit testing)
- Maven (build & dependency management)

## How to Run

1. Clone the repository.
2. Open the project in IntelliJ IDEA or another Java IDE.
3. Run the `Main` class to start the application.

To run tests:
mvn test

## Project Structure

- `model/` – Contains core game logic, board state, and piece classes
- `view/` – Swing-based GUI components (board, squares, pieces)
- `controller/` – Handles input and coordinates model/view (integrated into view in this version)
- `test/` – JUnit 5 test suite covering movement rules, special rules, and game state

## Contributors

This version was developed and fully tested by **[Your Name]**, a Computer Science student at Kutaisi International University.

## License

This project is for educational use only.
