import static javax.swing.SwingUtilities.invokeLater;

/**
 * mill
 * Created on 26.05.17.
 */
class Application {
    public static void main(final String[] args) {
        invokeLater(MainWindow::new);
    }
}
