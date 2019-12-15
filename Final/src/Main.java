import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
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

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();

        // ------------------ TOP -----------------//
        HBox title = getTop();
        HBox filter = getFilter();
        VBox top = new VBox();
        top.getChildren().addAll(title, filter);
        root.setTop(top);


        // ------------------ CENTER -----------------//
        // add and delete players
        HBox changePlayers = getChangePlayers();
        HBox addPlayers = getAddPlayers();
        HBox delPlayers = getDelPlayers();

        // table
        table = getTable();

        FilteredList<NBAPlayer> filteredList = new FilteredList<NBAPlayer>(table.getItems(), b -> true);
        // filterField

        // add everything to vbox
        VBox center = new VBox();
        center.getChildren().addAll(delPlayers, table, changePlayers, addPlayers);
        root.setCenter(center);

        // set up stage
        Scene scene = new Scene(root, 1200, 800);
        primaryStage.setScene(scene);
        primaryStage.setTitle("NBA Player Statistics");
        primaryStage.show();
    }

    /**
     * helper function for creating top of the gui
     * @return hbox for top
     */
    private HBox getTop() {
        HBox top = new HBox(10);
        top.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("NBA Player Statistics");
        top.getChildren().add(titleLabel);

        return top;
    }

    /**
     * helper function for creating filter
     * @return hbox for filter
     */
    private HBox getFilter() {
        HBox filterBox = new HBox(10);
        filterBox.setAlignment(Pos.CENTER);
        TextField filter = new TextField();
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

        // set up hbox
        HBox addPlayers = new HBox();
        addPlayers.getChildren().add(addButton);
        addPlayers.setPadding(new Insets(10));
        addPlayers.setSpacing(10);


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
        List<NBAPlayer> players = playerTree.inOrderTraversal();
        return FXCollections.observableArrayList(players);
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
}
