# JAVA_INTERFACE

A simple Java project demonstrating the use of **JavaFX** for GUI interfaces and **MySQL** for data handling.  
It includes a controller, model classes, and FXML layout definitions.

---

## 📂 Project Structure


JAVA_INTERFACE/  
│ App.java # Main entry point of the application  
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

## 🏗️ Importing the Project in IntelliJ with Maven

1. **Clone the repository**
    
    ```bash
    git clone https://github.com/Elmoro04/JAVA_INTERFACE.git
    cd JAVA_INTERFACE
    ```

2. **Open IntelliJ IDEA**
   - Go to **File → New → Project from Existing Sources...**
   - Select the `JAVA_INTERFACE` folder
   - Choose **Maven** as the project model

3. **Configure Maven**
   - IntelliJ will automatically detect the `pom.xml` file (make sure it exists in the project root)
   - Wait until Maven finishes downloading dependencies
   - If prompted, click **Enable Auto-Import**

4. **Add JavaFX libraries**
   - Go to **File → Project Structure → Libraries → + → Java**
   - Select the `lib` folder of your JavaFX SDK (for example `/path/to/javafx-sdk/lib`)
   - Confirm and apply changes

5. **Configure VM options for JavaFX**
   - Go to **Run → Edit Configurations...**
   - Select your run configuration (e.g. `App`)
   - In **VM options** add:

    ```
    --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml
    ```

6. **Run the project**
   - Open `App.java`
   - Right-click → **Run 'App.main()'**
   - The JavaFX interface should start



