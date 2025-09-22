package com.mercatopokemon.Gestionale_DB;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import com.mercatopokemon.Tables.Card;
import com.mercatopokemon.Tables.DatiCarta;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class CardsController {

    @FXML private TextField tfHost;
    @FXML private TextField tfPort;
    @FXML private TextField tfDatabase;
    @FXML private TextField tfUser;
    @FXML private PasswordField pfPassword;
    @FXML private Button btnConnect;
    @FXML private Button btnLoad;
    @FXML private Button remCard;
    @FXML private Label lblStatus;
    @FXML private Button AddCard;

    @FXML private TableView<Card> tableCards;
    @FXML private TableColumn<Card, String> colNome;
    @FXML private TableColumn<Card, String> colCodesp;
    @FXML private TableColumn<Card, String> colNumero;
    @FXML private TableColumn<Card, Integer> colCodL;
    @FXML private TableColumn<Card, String> colEspansione;

    @FXML private TableView<DatiCarta> tableRecords;
    @FXML private TableColumn<DatiCarta, LocalDate> colDataO;
    @FXML private TableColumn<DatiCarta, Integer> colPop;
    @FXML private TableColumn<DatiCarta, Double> colMin;
    @FXML private TableColumn<DatiCarta, Double> colMed;

    @FXML private ImageView imgCardPreview;
    @FXML private Label lblImgInfo;

    private Connection connection;

    private final ObservableList<Card> cards = FXCollections.observableArrayList();
    private final ObservableList<DatiCarta> records = FXCollections.observableArrayList();

    private static final String IMAGES_RES_FOLDER = "/com/mercatopokemon/Gestionale_DB/IMMAGINI/";
    private final BooleanProperty connected = new SimpleBooleanProperty(false);

    @FXML
    public void initialize() {
        tfHost.setText("127.0.0.1");
        tfPort.setText("3306");
        tfDatabase.setText("mercatopokemon");
        tfUser.setText("root");


        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colCodesp.setCellValueFactory(new PropertyValueFactory<>("codesp"));
        colNumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        colCodL.setCellValueFactory(new PropertyValueFactory<>("codL"));
        colEspansione.setCellValueFactory(new PropertyValueFactory<>("nomeE"));

        colDataO.setCellValueFactory(new PropertyValueFactory<>("dataO"));
        colPop.setCellValueFactory(new PropertyValueFactory<>("pop"));
        colMin.setCellValueFactory(new PropertyValueFactory<>("min"));
        colMed.setCellValueFactory(new PropertyValueFactory<>("med"));

        tableCards.setItems(cards);
        tableRecords.setItems(records);

        tableCards.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> showCardImage(newSel));
        tableCards.setRowFactory(tv -> {
            TableRow<Card> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 1 && !row.isEmpty()) {
                    tableCards.getSelectionModel().select(row.getIndex());
                    Card selected = row.getItem();
                    loadRecordsForCard(selected);
                }
            });
            return row;
        });
        tableCards.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> remCard.setDisable(newSel == null));
        AddCard.disableProperty().bind(connected.not());

        btnLoad.setDisable(true);
    }

    @FXML
    private void onConnect() {
        String host = tfHost.getText().trim();
        String port = tfPort.getText().trim();
        String db = tfDatabase.getText().trim();
        String user = tfUser.getText().trim();
        String pw = pfPassword.getText();

        final String url = String.format("jdbc:mysql://%s:%s/%s", host, port, db);

        lblStatus.setText("Connessione in corso...");
        btnConnect.setDisable(true);

        Task<Void> connectTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                    } catch (ClassNotFoundException ignored) {
                    }
                    connection = DriverManager.getConnection(url, user, pw);
                } catch (SQLException e) {
                    throw e;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                lblStatus.setText("Connesso a " + db + "@" + host);
                btnLoad.setDisable(false);
                btnConnect.setDisable(false);
                connected.set(true);

            }

            @Override
            protected void failed() {
                Throwable e = getException();
                lblStatus.setText("Errore connessione: " + (e != null ? e.getMessage() : "sconosciuto"));
                showAlert(Alert.AlertType.ERROR, "Connessione fallita", e != null ? e.getMessage() : "");
                btnConnect.setDisable(false);
                connected.set(false);

            }
        };

        new Thread(connectTask).start();
    }

    @FXML
    private void onLoadCards() {
        if (connection == null) {
            showAlert(Alert.AlertType.WARNING, "Non connesso", "Connettiti al database prima.");
            return;
        }

        lblStatus.setText("Caricamento carte...");
        cards.clear();

        Task<Void> loadTask = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "SELECT C.nome, C.Codesp, C.numero, L.tipo, S.nome FROM Carta C JOIN Lingua L ON C.CodL = L.CodLingua JOIN espansione S ON C.Codesp = S.COD ORDER BY C.nome, C.Codesp, C.numero";
                try (PreparedStatement ps = connection.prepareStatement(sql);
                     ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        String nome = rs.getString("C.nome");
                        String codesp = rs.getString("C.Codesp");
                        String numero = rs.getString("C.numero");
                        String codL = rs.getString("L.tipo");
                        String nomeE = rs.getString("S.nome");
                        Card c = new Card(nome, codesp, numero, codL, nomeE);
                        Platform.runLater(() -> cards.add(c));
                    }
                } catch (SQLException ex) {
                    throw ex;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                lblStatus.setText("Caricate " + cards.size() + " carte.");
            }

            @Override
            protected void failed() {
                lblStatus.setText("Errore caricamento: " + getException().getMessage());
                showAlert(Alert.AlertType.ERROR, "Errore caricamento", getException().getMessage());
            }
        };

        new Thread(loadTask).start();
    }


    private void loadRecordsForCard(Card card) {
        if (connection == null) {
            showAlert(Alert.AlertType.WARNING, "Non connesso", "Connettiti al database prima.");
            return;
        }

        records.clear();
        lblStatus.setText("Caricamento record per " + card.getNome());

        Task<Void> t = new Task<>() {
            @Override
            protected Void call() throws Exception {
                String sql = "SELECT dataO, pop, min, med FROM DatiCarta WHERE nomeCarta = ? AND CodespCarta = ? AND numeroCarta = ? ORDER BY dataO DESC";
                try (PreparedStatement ps = connection.prepareStatement(sql)) {
                    ps.setString(1, card.getNome());
                    ps.setString(2, card.getCodesp());
                    ps.setString(3, card.getNumero());
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            Date d = rs.getDate("dataO");
                            LocalDate ld = d != null ? d.toLocalDate() : null;
                            int pop = rs.getInt("pop");
                            double min = rs.getDouble("min");
                            double med = rs.getDouble("med");
                            DatiCarta dc = new DatiCarta(ld, pop, min, med);
                            Platform.runLater(() -> records.add(dc));
                        }
                    }
                } catch (SQLException ex) {
                    throw ex;
                }
                return null;
            }

            @Override
            protected void succeeded() {
                lblStatus.setText("Record caricati: " + records.size());
            }

            @Override
            protected void failed() {
                lblStatus.setText("Errore caricamento record: " + getException().getMessage());
                showAlert(Alert.AlertType.ERROR, "Errore", getException().getMessage());
            }
        };

        new Thread(t).start();
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Platform.runLater(() -> {
            Alert a = new Alert(type);
            a.setTitle(title);
            a.setHeaderText(null);
            a.setContentText(message);
            a.showAndWait();
        });
    }

    public void closeConnection() {
        if (connection != null) {
            try { connection.close(); } catch (SQLException ignored) {}
            connection = null;
        }
    }


    public void cardAdd() {
        Dialog<ButtonType> dlg = new Dialog<>();
        dlg.setTitle("Aggiungi carta");
        dlg.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        TextField tfNome = new TextField();
        TextField tfCodesp = new TextField();
        TextField tfNumero = new TextField();
        TextField tfCodL = new TextField();

        GridPane g = new GridPane();
        g.setHgap(8);
        g.setVgap(8);
        g.setPadding(new Insets(10, 10, 10, 10));
        g.addRow(0, new Label("Nome:"), tfNome);
        g.addRow(1, new Label("Codesp:"), tfCodesp);
        g.addRow(2, new Label("Numero:"), tfNumero);
        g.addRow(3, new Label("CodL (opz):"), tfCodL);

        dlg.getDialogPane().setContent(g);
        Optional<ButtonType> res = dlg.showAndWait();
        if (res.isEmpty() || res.get() != ButtonType.OK) return;

        String nome = tfNome.getText().trim();
        String codesp = tfCodesp.getText().trim();
        String numero = tfNumero.getText().trim();
        String codLtxt = tfCodL.getText().trim();

        if (nome.isEmpty() || codesp.isEmpty() || numero.isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Input non valido", "Nome, Codesp e Numero sono obbligatori.");
            return;
        }

        Integer codL = null;
        if (!codLtxt.isEmpty()) {
            try { codL = Integer.parseInt(codLtxt); }
            catch (NumberFormatException e) {
                showAlert(Alert.AlertType.ERROR, "Input non valido", "CodL deve essere un intero.");
                return;
            }
        }

        try {
            if (connection == null) {
                showAlert(Alert.AlertType.ERROR, "Errore", "Non connesso al database.");
                return;
            }

            String sql = "INSERT INTO Carta (nome, Codesp, numero, CodL) VALUES (?, ?, ?, ?)";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, nome);
                ps.setString(2, codesp);
                ps.setString(3, numero);
                if (codL != null) ps.setInt(4, codL); else ps.setNull(4, Types.INTEGER);
                ps.executeUpdate();
            }

            lblStatus.setText("Carta aggiunta.");
            onLoadCards();

            String fileName = String.format("%s (%s %s).jpg", nome, codesp, numero);
            String info = "Carta aggiunta. Aggiungi l'immagine con questo nome:\n\n" +
                    fileName + "\n\nin: src/main/resources" + IMAGES_RES_FOLDER;
            showAlert(Alert.AlertType.INFORMATION, "Immagine da aggiungere", info);

        } catch (SQLException e) {
            if (e instanceof SQLIntegrityConstraintViolationException) {
                showAlert(Alert.AlertType.ERROR, "Errore inserimento", "Carta già presente (duplicato).");
            } else {
                showAlert(Alert.AlertType.ERROR, "Errore inserimento", e.getMessage());
            }
            lblStatus.setText("Errore inserimento");
        }
    }


    public void cardRemove() {
        Card sel = tableCards.getSelectionModel().getSelectedItem();
        if (sel == null) {
            showAlert(Alert.AlertType.INFORMATION, "Seleziona una carta", "Seleziona una carta da rimuovere.");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION,
                "Eliminare la carta \"" + sel.getNome() + "\" (" + sel.getCodesp() + " " + sel.getNumero() + ")?\n" +
                        "Verranno rimossi anche tutti i record associati.",
                ButtonType.OK, ButtonType.CANCEL);
        confirm.setTitle("Conferma eliminazione");
        Optional<ButtonType> opt = confirm.showAndWait();
        if (opt.isEmpty() || opt.get() != ButtonType.OK) return;

        try {
            boolean oldAuto = connection.getAutoCommit();
            connection.setAutoCommit(false);
            try (PreparedStatement delPercent = connection.prepareStatement(
                    "DELETE FROM percentuale WHERE nomeCartaPERC = ? AND CodespCartaPERC = ? AND numeroCartaPERC = ?");
                 PreparedStatement delDati = connection.prepareStatement(
                         "DELETE FROM DatiCarta WHERE nomeCarta = ? AND CodespCarta = ? AND numeroCarta = ?");
                 PreparedStatement delCarta = connection.prepareStatement(
                         "DELETE FROM Carta WHERE nome = ? AND Codesp = ? AND numero = ?")) {

                // elimina percentuale (figli)
                delPercent.setString(1, sel.getNome());
                delPercent.setString(2, sel.getCodesp());
                delPercent.setString(3, sel.getNumero());
                delPercent.executeUpdate();

                // elimina DatiCarta (figli)
                delDati.setString(1, sel.getNome());
                delDati.setString(2, sel.getCodesp());
                delDati.setString(3, sel.getNumero());
                delDati.executeUpdate();

                // elimina Carta (padre)
                delCarta.setString(1, sel.getNome());
                delCarta.setString(2, sel.getCodesp());
                delCarta.setString(3, sel.getNumero());

                connection.commit();
            } catch (Exception ex) {
                try { connection.rollback(); } catch (Exception ignored) {}
                throw ex;
            } finally {
                connection.setAutoCommit(oldAuto);
            }

            cards.remove(sel);
            tableRecords.getItems().clear();
            tableCards.refresh();
            lblStatus.setText("Carta rimossa.");
            remCard.setDisable(tableCards.getSelectionModel().getSelectedItem() == null);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Errore eliminazione", e.getMessage());
            lblStatus.setText("Errore eliminazione");
            remCard.setDisable(tableCards.getSelectionModel().getSelectedItem() == null);
        }
    }


    private void showCardImage(Card card) {
        if (card == null) {
            imgCardPreview.setImage(null);
            lblImgInfo.setText("Nessuna carta selezionata");
            return;
        }

        String imageFile = String.format("%s (%s %s).jpg", card.getNome(), card.getCodesp(), card.getNumero());
        String resourcePath = IMAGES_RES_FOLDER + imageFile;

        try (java.io.InputStream is = getClass().getResourceAsStream(resourcePath)) {
            if (is != null) {
                URL resUrl = getClass().getResource(resourcePath);
                imgCardPreview.setImage(new Image(resUrl.toString()));
                lblImgInfo.setText(imageFile);
                return;
            }
        } catch (Exception e) {
            System.out.println("DEBUG: errore durante getResourceAsStream: " + e.getMessage());
        }

        URL def = getClass().getResource(IMAGES_RES_FOLDER + "default.jpg");
        imgCardPreview.setImage(new Image(def.toString()));
        lblImgInfo.setText("Default");
    }
}
