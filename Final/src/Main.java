//
// Title:           NBA Statistics
// Files:           Requires: N/A
// Course:          CS 400 Fall 19 2019
//
// Author:          Roy Sun
// Email:           rsun65@wisc.edu
// Lecturer's Name: Andrew Kuemmel
//
///////////////////////////// CREDIT OUTSIDE HELP /////////////////////////////
//
// Students who get help from sources other than their partner must fully
// acknowledge and credit those sources of help here.  Instructors and TAs do
// not need to be credited here, but tutors, friends, relatives, room mates,
// strangers, and others do.  If you received no outside help from either type
//  of source, then please explicitly indicate NONE.
//
// Persons:         NONE
// Online Sources:  thenewboston Youtube
//                  stackoverflow
//                  oracle docs
//                  geeksforgeeks
//

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main extends Application {
    private Stage primaryStage;

    // gui elements
    private TextField nameInput;
    private TextField posInput;
    private TextField ageInput;
    private TextField teamInput;
    private TextField gamesInput;
    private TextField threePtInput;
    private TextField twoPtInput;
    private TextField ftInput;
    private TextField reboundInput;
    private TextField assistInput;
    private TextField stealInput;
    private TextField blocksInput;
    private TextField turnOversInput;
    private TextField foulsInput;
    private TextField pointsInput;
    private TableView<NBAPlayer> table;
    private Button addButton;
    private Button delButton;
    private Label addText;
    private TextField downloadPrompt;
    private Label errorText;
    private Label fileCreatedText;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        HBox error = getError();
        // catches all errors to display at bottom
        try {
            BorderPane root = new BorderPane();

            // ------------------ TOP -----------------//
            HBox title = getTop();

            TextField filter = new TextField();
            filter.setPrefWidth(400);
            filter.setPrefHeight(40);
            filter.setFont(Font.font("Calibri", FontWeight.THIN, 30));
            HBox filterBox = getFilter(filter);
            VBox top = new VBox();
            top.getChildren().addAll(title, filterBox);
            root.setTop(top);


            // ------------------ CENTER -----------------//
            // table to display nba player data
            table = getTable();

            // filter logic
            ObservableList<NBAPlayer> playerList = getPlayers();
            FilteredList<NBAPlayer> filteredList = new FilteredList<NBAPlayer>(table.getItems(), a -> true);
            filter.textProperty().addListener((arg, oldVal, newVal) -> {
                table.setItems(filteredList);
                filteredList.setPredicate(player -> {
                    // filter is empty
                    if (newVal == null || newVal.isEmpty()) {
                        table.setItems(playerList);
                        return true;
                    }

                    String filterVal = newVal.toLowerCase();

                    // name, team, or position contains filter text
                    return player.getName().toLowerCase().contains(filterVal) || player.getTeam().toLowerCase().contains(filterVal)
                            || player.getPosition().toLowerCase().contains(filterVal);
                });
            });

            VBox addBox = new VBox();
            addBox.setAlignment(Pos.BASELINE_RIGHT);
            addBox.getChildren().addAll(getAddPlayers(), getAddText());

            // add everything to vbox
            VBox center = new VBox();
            center.getChildren().addAll(getDelPlayers(), table, getChangePlayers(), addBox);
            root.setCenter(center);

            // ------------------ BOTTOM -----------------//
            HBox downloadPrompt = getDownloadPrompt();
            HBox download = getDownload();
            HBox fileCreated = getFileCreated();
            VBox bottom = new VBox();
            bottom.getChildren().addAll(downloadPrompt, download, error, fileCreated);
            root.setBottom(bottom);

            // ------------------ STATISTICS -----------------//
            BorderPane stats = new BorderPane();
            PieChart positionChart = getPositionChart();
            BarChart<String, Number> pointsChart = getBarChart("Player Points", "Points", 10, NBAPlayer::getPoints);
            BarChart<String, Number> assistsChart = getBarChart("Player Assists", "Assists", 10, NBAPlayer::getAssists);
            BarChart<String, Number> blocksChart = getBarChart("Player Blocks", "Blocks", 10, NBAPlayer::getBlocks);
            stats.setTop(positionChart);
            HBox barGraphs = new HBox();
            barGraphs.getChildren().addAll(pointsChart, assistsChart, blocksChart);
            stats.setCenter(barGraphs);

            // set up tabs
            TabPane tabPane = new TabPane();
            Tab homeTab = new Tab("Home");
            homeTab.setContent(root);
            Tab statsTab = new Tab("Stats");
            statsTab.setContent(stats);
            tabPane.getTabs().addAll(homeTab, statsTab);
            tabPane.setTabClosingPolicy(TabPane.TabClosingPolicy.UNAVAILABLE);

            // stage up stage
            Scene scene = new Scene(tabPane, 1600, 900);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
            primaryStage.setScene(scene);
            primaryStage.setTitle("NBA Player Statistics");
            primaryStage.setOnCloseRequest(confirmClose);
            primaryStage.show();
            this.primaryStage = primaryStage;
        } catch (Exception e) {
            displayErrorMessage(e);
        }

    }

    /**
     * helper function for creating top of the gui     * @return hbox for top
     */
    private HBox getTop() {
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("NBA PLAYER STATISTICS");
        titleLabel.setId("title-label");

        try {
            top.getChildren().addAll(titleLabel);
        } catch (Exception e) {
            displayErrorMessage(e);
        }

        return top;
    }

    /**
     * helper function for creating filter
     * @return hbox for filter
     */
    private HBox getFilter(TextField filter) {
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        filter.setPromptText("Filter");
        filter.setFocusTraversable(false);
        filterBox.getChildren().add(filter);
        return filterBox;
    }

    /**
     * helper function for creating the add players button
     * @return hbox for add players
     */
    private HBox getAddPlayers() {
        // set up button logic
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClick());
        addButton.setId("add-button");

        // set up hbox
        HBox addPlayers = new HBox();
        addPlayers.getChildren().add(addButton);
        addPlayers.setPadding(new Insets(5));
        addPlayers.setSpacing(10);
        addPlayers.setAlignment(Pos.CENTER_RIGHT);

        return addPlayers;
    }

    /**
     * helper function for creating the delete players button
     * @return hbox for deleting players
     */
    private HBox getDelPlayers() {
        // set up button logic
        Button delButton = new Button("Delete");
        delButton.setOnAction(e -> delButtonClick());
        delButton.setId("del-button");

        // set up hbox
        HBox delPlayers = new HBox();
        delPlayers.getChildren().add(delButton);
        delPlayers.setPadding(new Insets(10));
        delPlayers.setSpacing(10);
        delPlayers.setAlignment(Pos.CENTER_RIGHT);

        return delPlayers;
    }

    /**
     * add new player
     */
    private void addButtonClick() {
        // require name, position and team to create player
        if (nameInput.getText().equals("") || posInput.getText().equals("") || teamInput.getText().equals("")) {
            addText.setText("Player must have a name, team, and position");
            return;
        }

        NBAPlayer player = new NBAPlayer(nameInput.getText(), posInput.getText(), Integer.parseInt(0 + ageInput.getText()),
                teamInput.getText(), Integer.parseInt(0 + gamesInput.getText()), Integer.parseInt(0 + threePtInput.getText()),
                Integer.parseInt(0 + twoPtInput.getText()), Integer.parseInt(0 + ftInput.getText()),
                Integer.parseInt(0 + reboundInput.getText()),Integer.parseInt(0 + assistInput.getText()),
                Integer.parseInt(0 + stealInput.getText()), Integer.parseInt(0 + blocksInput.getText()),
                Integer.parseInt(0 + turnOversInput.getText()), Integer.parseInt(0 + foulsInput.getText()),
                Integer.parseInt(0 + pointsInput.getText()));
        table.getItems().add(player);
        addText.setText("Player " + nameInput.getText() + " added successfully!");
        nameInput.clear();
        posInput.clear();
        ageInput.clear();
        teamInput.clear();
        gamesInput.clear();
        threePtInput.clear();
        twoPtInput.clear();
        ftInput.clear();
        reboundInput.clear();
        assistInput.clear();
        stealInput.clear();
        blocksInput.clear();
        turnOversInput.clear();
        foulsInput.clear();
        pointsInput.clear();
    }

    /**
     * delete highlighted player
     */
    private void delButtonClick() {
        ObservableList<NBAPlayer> playerSelected, allPlayers;
        allPlayers = table.getItems();
        playerSelected = table.getSelectionModel().getSelectedItems();
        playerSelected.forEach(allPlayers::remove);
        table.setItems(allPlayers);
    }


    /**
     * helper function for getting list of nba players from file
     * @return list of nba players
     */
    private ObservableList<NBAPlayer> getPlayers() {
        TreapTree<NBAPlayer> playerTree = NBAPlayerReader.parseCSV();
        return FXCollections.observableArrayList(playerTree.inOrderTraversal());
    }

    /**
     * helper function for getting prompt for adding and deleting players
     * @return hbox that allows adding and deleting of players
     */
    private HBox getChangePlayers() {
        // new player inputs
        int minWidth = 30;
        nameInput = new TextField();
        nameInput.setPromptText("Name");
        nameInput.setMinWidth(minWidth);
        nameInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!nameInput.getText().matches("[a-zA-Z]+")) {
                    nameInput.setText("");
                }
            }
        });

        posInput = new TextField();
        posInput.setPromptText("Position");
        posInput.setMinWidth(minWidth);
        posInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!posInput.getText().matches("[a-zA-Z]+")) {
                    posInput.setText("");
                }
            }
        });

        ageInput = new TextField();
        ageInput.setPromptText("Age");
        ageInput.setMinWidth(minWidth);
        ageInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!ageInput.getText().matches("\\d*")) {
                    ageInput.setText("");
                }
            }
        });

        teamInput = new TextField();
        teamInput.setPromptText("Team");
        teamInput.setMinWidth(minWidth);
        teamInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!teamInput.getText().matches("[a-zA-Z]+")) {
                    teamInput.setText("");
                }
            }
        });

        gamesInput = new TextField();
        gamesInput.setPromptText("Games");
        gamesInput.setMinWidth(minWidth);
        gamesInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!gamesInput.getText().matches("\\d*")) {
                    gamesInput.setText("");
                }
            }
        });

        threePtInput = new TextField();
        threePtInput.setPromptText("3pt");
        threePtInput.setMinWidth(minWidth);
        threePtInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!threePtInput.getText().matches("\\d*")) {
                    threePtInput.setText("");
                }
            }
        });

        twoPtInput = new TextField();
        twoPtInput.setPromptText("2pt");
        twoPtInput.setMinWidth(minWidth);
        twoPtInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!twoPtInput.getText().matches("\\d*")) {
                    twoPtInput.setText("");
                }
            }
        });

        ftInput = new TextField();
        ftInput.setPromptText("FT");
        ftInput.setMinWidth(minWidth);
        ftInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!ftInput.getText().matches("\\d*")) {
                    ftInput.setText("");
                }
            }
        });

        reboundInput = new TextField();
        reboundInput.setPromptText("Rebounds");
        reboundInput.setMinWidth(minWidth);
        reboundInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!reboundInput.getText().matches("\\d*")) {
                    reboundInput.setText("");
                }
            }
        });

        assistInput = new TextField();
        assistInput.setPromptText("Assists");
        assistInput.setMinWidth(minWidth);
        assistInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!assistInput.getText().matches("\\d*")) {
                    assistInput.setText("");
                }
            }
        });

        stealInput = new TextField();
        stealInput.setPromptText("Steals");
        stealInput.setMinWidth(minWidth);
        stealInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!stealInput.getText().matches("\\d*")) {
                    stealInput.setText("");
                }
            }
        });

        blocksInput = new TextField();
        blocksInput.setPromptText("Blocks");
        blocksInput.setMinWidth(minWidth);
        blocksInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!blocksInput.getText().matches("\\d*")) {
                    blocksInput.setText("");
                }
            }
        });

        turnOversInput = new TextField();
        turnOversInput.setPromptText("Turn Overs");
        turnOversInput.setMinWidth(minWidth);
        turnOversInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!turnOversInput.getText().matches("\\d*")) {
                    turnOversInput.setText("");
                }
            }
        });

        foulsInput = new TextField();
        foulsInput.setPromptText("Fouls");
        foulsInput.setMinWidth(minWidth);
        foulsInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!foulsInput.getText().matches("\\d*")) {
                    foulsInput.setText("");
                }
            }
        });

        pointsInput = new TextField();
        pointsInput.setPromptText("Points");
        pointsInput.setMinWidth(minWidth);
        pointsInput.focusedProperty().addListener((arg, oldVal, newVal) -> {
            if (!newVal) {
                if (!pointsInput.getText().matches("\\d*")) {
                    pointsInput.setText("");
                }
            }
        });

        HBox changePlayers = new HBox();
        changePlayers.setPadding(new Insets(10));
        changePlayers.setSpacing(10);
        changePlayers.getChildren().addAll(nameInput, posInput, ageInput,  teamInput, gamesInput,
                threePtInput, twoPtInput, ftInput, reboundInput, assistInput, stealInput, blocksInput,
                turnOversInput, foulsInput, pointsInput);

        return changePlayers;
    }

    /**
     * helper function for creating javafx table of nba players
     * @return table of nba playersNBAPlayer, String
     */
    private TableView<NBAPlayer> getTable() {
        int minWidth = 70;
        // set up table columns
        TableColumn<NBAPlayer, String> nameColumn = new TableColumn<NBAPlayer, String>("Name");
        nameColumn.setMinWidth(minWidth);
        nameColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, String>("name"));
        TableColumn<NBAPlayer, String> posColumn = new TableColumn<NBAPlayer, String>("Position");
        posColumn.setMinWidth(minWidth);
        posColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, String>("position"));
        TableColumn<NBAPlayer, Integer> ageColumn = new TableColumn<NBAPlayer, Integer>("Age");
        ageColumn.setMinWidth(minWidth);
        ageColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("age"));
        TableColumn<NBAPlayer, String> teamColumn = new TableColumn<NBAPlayer, String>("Team");
        teamColumn.setMinWidth(minWidth);
        teamColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, String>("team"));
        TableColumn<NBAPlayer, Integer> gamesColumn = new TableColumn<NBAPlayer, Integer>("# Games");
        gamesColumn.setMinWidth(minWidth);
        gamesColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("gamesPlayed"));
        TableColumn<NBAPlayer, Integer> threePtColumn = new TableColumn<NBAPlayer, Integer>("3 PT");
        threePtColumn.setMinWidth(minWidth);
        threePtColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("threePointGoals"));
        TableColumn<NBAPlayer, Integer> twoPtColumn = new TableColumn<NBAPlayer, Integer>("2 PT");
        twoPtColumn.setMinWidth(minWidth);
        twoPtColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("twoPointGoals"));
        TableColumn<NBAPlayer, Integer> freeThrowColumn = new TableColumn<NBAPlayer, Integer>("Free Throw");
        freeThrowColumn.setMinWidth(minWidth);
        freeThrowColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("freeThrowsMade"));
        TableColumn<NBAPlayer, Integer> reboundColumn = new TableColumn<NBAPlayer, Integer>("Rebounds");
        reboundColumn.setMinWidth(minWidth);
        reboundColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("totalRebounds"));
        TableColumn<NBAPlayer, Integer> assistColumn = new TableColumn<NBAPlayer, Integer>("Assists");
        assistColumn.setMinWidth(minWidth);
        assistColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("assists"));
        TableColumn<NBAPlayer, Integer> stealColumn = new TableColumn<NBAPlayer, Integer>("Steals");
        stealColumn.setMinWidth(minWidth);
        stealColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("steals"));
        TableColumn<NBAPlayer, Integer> blockColumn = new TableColumn<NBAPlayer, Integer>("Blocks");
        blockColumn.setMinWidth(minWidth);
        blockColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("blocks"));
        TableColumn<NBAPlayer, Integer> turnOverColumn = new TableColumn<NBAPlayer, Integer>("Turn Overs");
        turnOverColumn.setMinWidth(minWidth);
        turnOverColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("turnovers"));
        TableColumn<NBAPlayer, Integer> foulColumn = new TableColumn<NBAPlayer, Integer>("Fouls");
        foulColumn.setMinWidth(minWidth);
        foulColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("fouls"));
        TableColumn<NBAPlayer, Integer> pointColumn = new TableColumn<NBAPlayer, Integer>("Points");
        pointColumn.setMinWidth(minWidth);
        pointColumn.setCellValueFactory(new PropertyValueFactory<NBAPlayer, Integer>("points"));

        TableView<NBAPlayer> center = new TableView<NBAPlayer>();
        center.setItems(getPlayers());
        center.getColumns().addAll(nameColumn, posColumn, ageColumn, teamColumn, gamesColumn, threePtColumn, twoPtColumn,
                freeThrowColumn, reboundColumn, assistColumn, stealColumn, blockColumn, turnOverColumn, foulColumn, pointColumn);
        return center;
    }

    /**
     * helper function for creating text that shows after a player is added
     * @return label with help text
     */
    private Label getAddText() {
        addText = new Label();
        addText.setPadding(new Insets(0, 10, 10, 10));
        addText.setAlignment(Pos.CENTER_RIGHT);
        return addText;
    }

    /**
     * helper function for creating hbox with download button
     * @return hbox with download button
     */
    private HBox getDownload() {
        HBox download = new HBox();
        download.setAlignment(Pos.CENTER);
        Button downloadButton = new Button("Download");
        downloadButton.setId("download-button");
        downloadButton.setOnAction(e -> createCSV(downloadPrompt.getText(), fileCreatedText));
        downloadPrompt.setText("");
        download.getChildren().add(downloadButton);
        return download;
    }

    /**
     * helper function for creating hbox with download prompt text field
     * @return hbox with prompt text field
     */
    private HBox getDownloadPrompt() {
        HBox download = new HBox();
        download.setAlignment(Pos.CENTER);
        downloadPrompt = new TextField();
        downloadPrompt.setPadding(new Insets(5, 10, 5, 10));
        downloadPrompt.setPromptText("File name");
        download.getChildren().add(downloadPrompt);
        return download;
    }

    /**
     * helper function for creating hbox with error message
     * @return hbox with error message
     */
    private HBox getError() {
        HBox error = new HBox();
        error.setAlignment(Pos.CENTER);
        errorText = new Label();
        error.getChildren().add(errorText);
        return error;
    }

    /**
     * helper function for creating csv file
     * @param promptText textfield for file path
     * @param fileCreated label for created message
     */
    private void createCSV(String promptText, Label fileCreated) {
        // check file path exists
        if (promptText.equals("") || promptText.isEmpty()) {
            errorText.setText("Please enter a file name");
            return;
        }

        String playerCSV;
        Writer writer = null;

        try {
            File file = new File(promptText + ".csv");
            writer = new BufferedWriter(new FileWriter(file));

            for (NBAPlayer player : table.getItems()) {
                playerCSV = String.join(",", player.getValues());
                writer.write(playerCSV);
                ((BufferedWriter) writer).newLine();
            }
        } catch (Exception e) {
            displayErrorMessage(e);
        } finally {
            try {
                writer.flush();
                writer.close();
                errorText.setText("");
                fileCreated.setText("File " + promptText + ".csv created!");
            } catch (Exception e) {
                displayErrorMessage(e);
            }
        }
    }

    /**
     * helper function for creating hbox with download success message
     * @return hbox with download success message
     */
    private HBox getFileCreated() {
        HBox fileCreated = new HBox();
        fileCreated.setAlignment(Pos.CENTER);
        fileCreatedText = new Label();
        fileCreated.getChildren().add(fileCreatedText);
        return fileCreated;
    }

    /**
     * helper function for displaying error message
     * @param e exception
     */
    private void displayErrorMessage(Exception e) {
        errorText.setText(e.getLocalizedMessage());
        fileCreatedText.setText("");
    }

    /**
     * helper function for creating pie graph
     * @return pie graph
     */
    private PieChart getPositionChart() {

        String position;
        HashMap<String, Integer> data = new HashMap<String, Integer>();

        // extract position data
        for (NBAPlayer player : table.getItems()) {
            position = player.getPosition();
            if (data.get(position) == null) {
                data.put(position, 1);
            } else {
                data.put(position, data.get(position) + 1);
            }
        }

        // add to pie chart
        PieChart.Data chartData[] = new PieChart.Data[data.keySet().size()];
        List<String> positions = new ArrayList<String>(data.keySet());

        for (int i = 0; i < positions.size(); i++) {
            chartData[i] = new PieChart.Data(positions.get(i), data.get(positions.get(i)));
        }

        // create pie chart
        PieChart pieChart = new PieChart(FXCollections.observableArrayList(chartData));
        pieChart.setTitle("Position Breakdown");
        return pieChart;
    }

    /**
     * helper function for creating a bar chart for a NBA player statistic
     * @param chartTitle chart title
     * @param yValue y-axis value
     * @param numPlayers number of players to display data
     * @param getInt function for returning the statistic
     * @return bar graph
     */
    private BarChart<String, Number> getBarChart(String chartTitle, String yValue, int numPlayers, NBAPlayerInt getInt) {
        // initialize graph
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<String, Number>(xAxis, yAxis);
        barChart.setTitle(chartTitle);
        xAxis.setLabel("Top " + Integer.toString(numPlayers) + " Players");
        yAxis.setLabel(yValue);

        // add data
        XYChart.Series<String, Number> data = new XYChart.Series<String, Number>();
        List<NBAPlayer> players = table.getItems().stream().
                sorted((x, y) -> getInt.getStat(y) - getInt.getStat(x)).
                limit(numPlayers).
                collect(Collectors.toList());

        for (NBAPlayer player : players) {
            data.getData().add(new XYChart.Data<String, Number>(player.getName(), getInt.getStat(player)));
        }

        // create graph
        barChart.getData().add(data);
        barChart.setLegendVisible(false);
        barChart.setPadding(new Insets(50));
        return barChart;
    }

    private EventHandler<WindowEvent> confirmClose = event -> {
        // create alert
        Alert closeConfirmation = new Alert(
                Alert.AlertType.CONFIRMATION,
                "Do you want to save your work first?"
        );

        Button saveButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.CANCEL
        );

        // button for save
        saveButton.setOnAction(e -> saveAlert());
        saveButton.setText("Save");
        saveButton.setId("save-button");

        //button to close
        Button exitButton = (Button) closeConfirmation.getDialogPane().lookupButton(
                ButtonType.OK
        );
        exitButton.setText("Exit");
        closeConfirmation.setHeaderText("Confirm Close");
        closeConfirmation.initModality(Modality.APPLICATION_MODAL);
        closeConfirmation.initOwner(primaryStage);

        Optional<ButtonType> close = closeConfirmation.showAndWait();
        if (!ButtonType.OK.equals(close.get())) {
            event.consume();
        }
    };

    /**
     * helper function for pop up window to prompt user for save
     */
    public void saveAlert() {
        Stage saveStage = new Stage();
        saveStage.initModality(Modality.APPLICATION_MODAL);
        saveStage.setMinWidth(300);

        // label and button to for save file path
        Label saveMessage = new Label();
        saveMessage.setText("Enter file path to save");

        TextField saveField = new TextField();
        saveField.setPromptText("File name");
        saveField.setFocusTraversable(false);
        saveField.addEventHandler(KeyEvent.ANY, e -> saveField.requestFocus());

        Button closeButton = new Button("Save");
        closeButton.setOnAction(e -> {
            createCSV(saveField.getText(), saveMessage);
            saveStage.close();
            primaryStage.close();
        });

        // create stage
        VBox saveBox = new VBox();
        saveBox.getChildren().addAll(saveMessage, saveField, closeButton);
        saveBox.setAlignment(Pos.CENTER);

        Scene scene = new Scene(saveBox);
        saveStage.setScene(scene);
        saveStage.show();
    }
}
