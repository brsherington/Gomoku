package sample;

import com.sun.glass.events.MouseEvent;
import com.sun.org.apache.xerces.internal.impl.xs.identity.Selector;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

public class Controller {
    @FXML
    private GridPane gp1; //This holds the play field
    @FXML
    private GridPane gp2;
    @FXML
    private Button quitButton;
    @FXML
    private Button restartButton;
    @FXML
    private Button backButton;
    @FXML
    private MenuItem quitMenu;
    @FXML
    private MenuItem changeBg;
    @FXML
    private Board model = null;
    @FXML
    private Paint color;
    @FXML
    private Circle stone;
    @FXML
    BorderPane window;
    public boolean isBlacksMove = true;
    @FXML
    private StackPane stackPane;
    @FXML
    private Label victory;
    @FXML
    private HBox vicBox;
    private int imgCounter = 0;
    private String[] imgs = {
            "-fx-background-image: url(\"/sample/mocha1.jpg\");-fx-background-size: cover; -fx-background-repeat: no-repeat;",
            "-fx-background-image: url(\"/sample/mocha2.jpg\");-fx-background-size: cover; -fx-background-repeat: no-repeat;",
            "-fx-background-image: url(\"/sample/mocha3.jpg\");-fx-background-size: cover; -fx-background-repeat: no-repeat;"
    };
    @FXML
    private void initialize(){
        quitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        quitMenu.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });
        restartButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame();
            }
        });
        changeBg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (imgCounter > 2) {
                    imgCounter = 0;
                }
                window.setStyle(imgs[imgCounter++]);
            }
        });

        startGame();
    }
    public void startGame(){
        gp1.getChildren().clear();
        window.setStyle(imgs[imgCounter++]);
        vicBox.setVisible(false);
        model = new Board(15,15);
        move(model);
        undo(model);
    }

    public void undo(Board b) {
        backButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String color;
                Move backMove = b.undoMove();
                color = (backMove.color == 1) ? "WHITE" : "BLACK";
                gp1.getChildren().remove(stone);
            }
        });
    }



    public void move(Board b){
        gp1.setOnMouseClicked(new EventHandler<javafx.scene.input.MouseEvent>() {
            @Override
            public void handle(javafx.scene.input.MouseEvent click) {
                double gap = gp1.getWidth() / b.row;
                String whoWins = "";
                String color = (isBlacksMove) ? "BLACK" : "WHITE";
                int colorInt = (isBlacksMove) ? 2 : 1;
                double xPos = click.getX();
                double yPos = click.getY();
                int row = (int) Math.round(yPos/gap);
                int col = (int) Math.round(xPos/gap);
                Move m = b.move(row, col, colorInt);
                if (m.color != 0) {
                    drawStone(m.row, m.col, color);
                    isBlacksMove = !isBlacksMove;
                    if (b.checkForWins()) {
                        whoWins = (b.winningMove.color == 1) ? "WHITE" : "BLACK";
                        vicBox.setVisible(true);
                        victory.setText(whoWins + " wins! Select 'Quit' or 'Restart'");
                        victory.setVisible(true);
                    }
                }
            }
        });
    }
    private void drawStone(int row, int col, String whoWins){
        double gap = gp1.getWidth() / 15;
        color = Color.valueOf(whoWins);
        stone = new Circle();
        stone.setCenterX(col);
        stone.setCenterY(row);
        stone.setRadius(gap/2);
        stone.setFill(color);
        gp1.add(stone, col, row);

    }
}