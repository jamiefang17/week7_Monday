import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.*;

public class PuzzleGame extends JFrame {

    private final int WIDTH =500;
    private int HEIGHT =500;//going to change later
    JPanel panel;//container
    private final int COLUMNS =3, ROWS = 4;//use to set layout

    public PuzzleGame()
    {
        super("Puzzle Game");

        panel = new JPanel();//sets a new instance of the Jpanel, initialize
        panel.setLayout(new GridLayout(ROWS,COLUMNS));
        add(panel);//add the panel that contains GridLayout to the JFrame



        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }
    
}
