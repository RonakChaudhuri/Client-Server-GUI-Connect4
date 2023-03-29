import java.io.Serializable;
import java.util.Arrays;

public class C4Info implements Serializable {
    private String p1Plays;
    private String p2Plays;
    private Boolean have2players;
    private Boolean p1turn;
    private Boolean p2turn;
    private Boolean turn;
    int[][] gameBoard;
    int winner;

    public C4Info() {
        p1Plays = "";
        p2Plays = "";
        have2players = false;
        p1turn = true;
        p2turn = false;
        turn = false;
        gameBoard = new int[6][7];
        for (int[] ints : gameBoard) {
            Arrays.fill(ints, 0);
        }
        winner = 0;
    }

    public void setP1Plays(String p1Plays) {
        this.p1Plays = p1Plays;
    }

    public String getP1Plays() {
        return p1Plays;
    }

    public void setP2Plays(String p2Plays) {
        this.p2Plays = p2Plays;
    }

    public String getP2Plays() {
        return p2Plays;
    }

    public void setHave2players(Boolean have2players) {
        this.have2players = have2players;
    }

    public Boolean getHave2players() {
        return have2players;
    }

    public void setP1turn(Boolean turn) {
        this.p1turn = turn;
    }

    public Boolean getP1Turn() {
        return p1turn;
    }

    public void setP2turn(Boolean turn) {
        this.p2turn = turn;
    }

    public Boolean getP2Turn() {
        return p2turn;
    }

    public void setTurn(Boolean turn) {
        this.turn = turn;
    }

    public Boolean getTurn() {
        return turn;
    }

    //Updates board with player one or player two piece
    public void updateBoard(int i, int j, int player) {
        gameBoard[i][j] = player;
    }

    //Returns true if space on board is occupied
    public Boolean checkBoard(int i, int j) {
        return (gameBoard[i][j] == 1) || (gameBoard[i][j] == 2);
    }

    //Return 0 if no winner, 1 if P1 wins, 2 if P2 Wins, 3 if tie
    public int checkForWinner(int num){
        // horizontalCheck
        for (int j = 0; j<4; j++ ){
            for (int i = 0; i<6; i++){
                if (this.gameBoard[i][j] == num && this.gameBoard[i][j+1] == num
                        && this.gameBoard[i][j+2] == num && this.gameBoard[i][j+3] == num){
                    return this.gameBoard[i][j];
                }
            }
        }
        // verticalCheck
        for (int i = 0; i<3; i++ ){
            for (int j = 0; j<7; j++){
                if (this.gameBoard[i][j] == num && this.gameBoard[i+1][j] == num
                        && this.gameBoard[i+2][j] == num && this.gameBoard[i+3][j] == num){
                    return this.gameBoard[i][j];
                }
            }
        }
        // ascendingDiagonalCheck
        for (int i=3; i<6; i++){
            for (int j=0; j<4; j++){
                if (this.gameBoard[i][j] == num && this.gameBoard[i-1][j+1] == num
                        && this.gameBoard[i-2][j+2] == num && this.gameBoard[i-3][j+3] == num)
                    return this.gameBoard[i][j];
            }
        }
        // descendingDiagonalCheck
        for (int i=3; i<6; i++){
            for (int j=3; j<7; j++){
                if (this.gameBoard[i][j] == num && this.gameBoard[i-1][j-1] == num
                        && this.gameBoard[i-2][j-2] ==num && this.gameBoard[i-3][j-3] == num)
                    return this.gameBoard[i][j];
            }
        }

        //tie
        for(int i = 0; i < 6; i++){
            for(int j = 0; j < 7; j++) {
                if(this.gameBoard[i][j] == 0) {
                    return 0;
                }
            }
        }
        return 3;
    }

    public void setWinner(int winner) {
        this.winner = winner;
    }

    public int getWinner() {
        return winner;
    }
}
