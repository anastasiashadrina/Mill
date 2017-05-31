import model.MillField;
import player.Player;

import java.awt.*;
import java.util.Scanner;

import static javax.swing.SwingUtilities.invokeLater;

/**
 * mill
 * Created on 26.05.17.
 */
public class Application {
    public static void main(String[] args) {
        MillField millField = new MillField();
        invokeLater(MainWindow::new);

        final Player playerWhite = new Player(Color.WHITE, millField);
        final Player playerBlack = new Player(Color.BLACK, millField);

        final Scanner scanner = new Scanner(System.in);

        while (true) {
            if (scanner.hasNext()) {
                System.out.println(scanner.next());
            } else {
                break;
            }
        }
    }
}
