# JAVA_INTERFACE

A simple Java project demonstrating the use of **JavaFX** for GUI interfaces and **MySQL** for data handling.  
It includes a controller, model classes, and FXML layout definitions.

---

## 📂 Project Structure


JAVA_INTERFACE/  
│ App.java # Main entry point of the application  
│ README.md # Project documentation  
│  
├── AppClass/  
│ └── CardsController.java # Controller handling user interactions and business logic  
│  
├── Interface/  
│ └── cards_layout.fxml # JavaFX layout definition  
│  
├── MySQL_Connector/  
│ └── mysql-connector-j-9.4.0.jar # MySQL JDBC driver  
│  
└── Tables/  
├── Card.java # Model class for cards  
└── DatiCarta.java # Model class for card data  


---

## 🚀 Features

- **JavaFX interface** defined via FXML  
- **MVC-like structure** with controllers and models  
- **MySQL integration** using JDBC connector  
- Example entities: `Card` and `DatiCarta`  

---

## 🛠️ Prerequisites

- **Java 17+** (JavaFX works best with recent JDKs)  
- **JavaFX SDK** installed and configured  
- **MySQL JDBC driver** (already included in the project as `mysql-connector-j-9.4.0.jar`)  
- An IDE such as **IntelliJ IDEA**, **Eclipse**, or **VS Code** with JavaFX plugins  

---

## 💻 Importing the Project

### 1. Clone the repository
```bash
git clone https://github.com/Elmoro04/JAVA_INTERFACE.git
cd JAVA_INTERFACE
