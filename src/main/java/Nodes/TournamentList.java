package Nodes;

import HelpfulClasses.UsefulConstants;
import Pages.Page;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Translate;

import java.util.ArrayList;

/**TournamentList extends ScrollPane
 * A visual list of TextFields that are used to create parameters
 * for initializing a tournament, and uploading it to a database.
 *
 * Additional list rows can be added or removed with the ADD and X buttons
 *
 * @author Colton LaChance
 */
public class TournamentList extends ScrollPane {
    private ArrayList<HBox> arrList = new ArrayList<HBox>();
    private Label label;
    private VBox vBox = new VBox();
    private Button addButton = new Button("+ ADD NEW");
    private double cellPrefWidth;

    public TournamentList(String labelStr,double spacing, double cellPrefWidth) {
        this.label = new Label(labelStr);
        label.setUnderline(true);
        label.setAlignment(Pos.TOP_LEFT);
        label.setLabelFor(vBox);
        vBox.setSpacing(spacing);
        if (labelStr != "") vBox.getChildren().add(label);
        this.cellPrefWidth = cellPrefWidth;
        this.setContent(vBox);
        this.setMinWidth(cellPrefWidth+60);
        this.setPannable(false);
    }

    /**reconstructVBox()
     * Reconstructs the list, refreshing it when changes are made.
     * @author Colton LaChance
     */
    public void reconstructVBox() {
        vBox.getChildren().clear();
        if (label.getText() != "") vBox.getChildren().add(label);
        for (var i = 0; i < arrList.size(); i++) {
            HBox curHBox = (HBox) arrList.get(i);
            DeleteButton curDelButton = (DeleteButton) curHBox.getChildren().get(1);
            curDelButton.index = i;
            curHBox.getChildren().remove(1);
            curHBox.getChildren().add(curDelButton);
            arrList.set(i, curHBox);
            vBox.getChildren().add(arrList.get(i));
        }
        vBox.getChildren().add(addButton);
        vBox.setPadding(new Insets(10,10,100,10));
        this.setContent(vBox);
        this.setMinHeight(UsefulConstants.DEFAULT_SCREEN_HEIGHT/3);
        this.setMaxHeight(UsefulConstants.DEFAULT_SCREEN_HEIGHT/3);
        this.setStyle("-fx-background: #EEEEEE;");
    }

    /**deleteFromList(int)
     * Removes from and reconstructs list using the reconstructVBox() method
     * @param index
     * @author Colton LaChance
     */
    public void deleteFromList(int index) {
        arrList.remove(index);
        reconstructVBox();
    }

    /**addToList(String, bool, bool)
     * Adds a new TextField to the list (VBox) as well as a delete button (X)
     * within a HBox
     * @param initialString
     * @param cellDisabled
     * @param forceAdd
     * @author Colton LaChance
     */
    public void addToList(String initialString, boolean cellDisabled, boolean forceAdd) {
        if (!forceAdd) {
            addButton.setOnMouseClicked(e -> {
                HBox hBox = new HBox();
                TextField newVal = new TextField(initialString);
                newVal.setPrefWidth(cellPrefWidth);
                newVal.setDisable(cellDisabled);
                DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
                deleteAt.setOnMouseClicked(de -> {
                    deleteFromList(deleteAt.index);
                });
                deleteAt.setDisable(cellDisabled);
                hBox.getChildren().addAll(newVal, deleteAt);

                arrList.add(hBox);

                reconstructVBox();
            });
        } else {
            HBox hBox = new HBox();
            TextField newVal = new TextField(initialString);
            newVal.setPrefWidth(cellPrefWidth);
            newVal.setDisable(cellDisabled);
            DeleteButton deleteAt = new DeleteButton(arrList.size(), "X");
            deleteAt.setOnMouseClicked(de -> {
                deleteFromList(deleteAt.index);
            });
            deleteAt.setDisable(cellDisabled);
            hBox.getChildren().addAll(newVal, deleteAt);
            arrList.add(hBox);

            reconstructVBox();
        }
    }

    public VBox getVBox() {
        return vBox;
    }

    public ArrayList<HBox> getArrList() {
        return arrList;
    }

    /**DeleteButton extends Button
     * A custom button, needed as an index is needed to delete proper row from
     * TournamentList
     * @author Colton LaChance
     */
    private class DeleteButton extends Button {
        public int index = 0;
        public Button button = this;

        public DeleteButton(int index, String string) {
            this.index = index;
            this.setText(string);
            button = this;
        }
    }
}