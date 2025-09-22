JAVA_INTERFACE

A simple Java project demonstrating the use of JavaFX for GUI interfaces and MySQL for data handling.
It includes a controller, model classes, and FXML layout definitions.

📂 Project Structure
JAVA_INTERFACE/
│   App.java                  # Main entry point of the application
│   README.md                 # Project documentation
│
├── AppClass/
│   └── CardsController.java  # Controller handling user interactions and business logic
│
├── Interface/
│   └── cards_layout.fxml     # JavaFX layout definition
│
├── MySQL_Connector/
│   └── mysql-connector-j-9.4.0.jar  # MySQL JDBC driver
│
└── Tables/
    ├── Card.java             # Model class for cards
    └── DatiCarta.java        # Model class for card data

🚀 Features

JavaFX interface defined via FXML.

MVC-like structure with controllers and models.

MySQL integration using JDBC connector.

Example entities: Card and DatiCarta.

🛠️ Prerequisites

Java 17+ (JavaFX works best with recent JDKs).

JavaFX SDK installed and configured.

MySQL JDBC driver (already included in the project as mysql-connector-j-9.4.0.jar).

An IDE such as IntelliJ IDEA, Eclipse, or VS Code with JavaFX plugins.

💻 Importing the Project
1. Clone the repository
git clone https://github.com/Elmoro04/JAVA_INTERFACE.git
cd JAVA_INTERFACE

2. Open in your IDE

IntelliJ IDEA:

File → Open → select the JAVA_INTERFACE folder.

Mark mysql-connector-j-9.4.0.jar as a library (Project Structure → Modules → Dependencies).

Add JavaFX SDK to your project (Settings → Libraries → Add → JavaFX).

Eclipse:

File → Import → Existing Projects into Workspace.

Add external JAR: right-click project → Properties → Java Build Path → Add External JARs → select mysql-connector-j-9.4.0.jar.

Configure JavaFX runtime arguments.

3. Configure JavaFX VM options

If running from IDE, add the following VM arguments (replace /path/to/javafx/lib with your local JavaFX SDK path):

--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml

▶️ Running the Project

Ensure MySQL is installed and running.

Open and run App.java (this is the main class).

The JavaFX GUI will launch, loading the layout from cards_layout.fxml.

📌 Notes

The included mysql-connector-j-9.4.0.jar must remain in the project for database access.

You may need to configure your MySQL database credentials inside the code (check the controller or connection utility).

If you only want to test the GUI, you can run it without connecting to MySQL.
