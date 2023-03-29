import javafx.scene.control.Button;
public class GameButton extends Button{
    private Integer row;
    private Integer col;
    Button checker;
    public GameButton(int i, int j) {
        checker = new Button();
        checker.setMinSize(150,150);
        checker.setStyle("-fx-background-color: Black; -fx-border-color: #243000; -fx-border-width: 3px;");
        row = i;
        col = j;
    }

    public Integer getCol() {
        return col;
    }

    public Integer getRow() {
        return row;
    }
}
