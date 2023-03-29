import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.event.WindowFocusListener;

public class C4Test {

    @Test
    void testOne() {
        C4Info info = new C4Info();
        info.updateBoard(2,3,1);
        info.updateBoard(2,4,1);
        info.updateBoard(2,5,1);
        info.updateBoard(2,6,1);
        assertEquals(1, info.checkForWinner(1));
    }

    @Test
    void testTwo() {
        C4Info info = new C4Info();
        info.updateBoard(1,3,2);
        info.updateBoard(2,3,2);
        info.updateBoard(3,3,2);
        info.updateBoard(4,3,2);
        assertEquals(2, info.checkForWinner(2));
    }

    @Test
    void testThree() {
        C4Info info = new C4Info();
        info.updateBoard(1,1,2);
        info.updateBoard(2,2,2);
        info.updateBoard(3,3,2);
        info.updateBoard(4,4,2);
        assertEquals(2, info.checkForWinner(2));
    }

    @Test
    void testFour() {
        C4Info info = new C4Info();
        info.updateBoard(1,1,2);
        info.updateBoard(2,5,2);
        info.updateBoard(3,1,2);
        info.updateBoard(0,0,2);
        assertEquals(0, info.checkForWinner(2));
    }

    @Test
    void testFive() {
        C4Info info = new C4Info();
        for(int i = 0; i < 6; i++) {
            for(int j = 0; j < 7; j++) {
                info.updateBoard(i,j,5);
            }
        }
        assertEquals(3, info.checkForWinner(1));
    }

    @Test
    void testSix() {
        C4Info info = new C4Info();
        info.updateBoard(3,3,1);
        info.updateBoard(3,4,1);
        info.updateBoard(3,5,1);
        info.updateBoard(3,6,1);
        assertEquals(1, info.checkForWinner(1));
    }

    @Test
    void testSeven() {
        C4Info info = new C4Info();
        info.updateBoard(0,1,2);
        info.updateBoard(1,1,2);
        info.updateBoard(2,1,2);
        info.updateBoard(3,1,2);
        assertEquals(2, info.checkForWinner(2));
    }


}
