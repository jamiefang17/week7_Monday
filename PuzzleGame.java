import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class PuzzleGame extends JFrame {

    private final int WIDTH =500;
    private int HEIGHT =500;//going to change later
    JPanel panel;//container
    private final int COLUMNS =3, ROWS = 4;//use to set layout
    //add buttons as instance viable
    private ArrayList<FancyButton> buttonsList;
    //to check if it succeed
    private ArrayList<FancyButton> buttonsSolution = new ArrayList<FancyButton>();
    //add new instant variable
    private BufferedImage imageSource;
    private BufferedImage imageRezied;


    public PuzzleGame()
    {
        super("Puzzle Game");

        panel = new JPanel();//sets a new instance of the Jpanel, initialize
        panel.setLayout(new GridLayout(ROWS,COLUMNS));
        add(panel);//add the panel that contains GridLayout to the JFrame

        try{
            imageSource = loadImage();
            int sourceWdith = imageSource.getWidth();
            int sourceHeight =imageSource.getHeight();
            double aspectRatio = (double)sourceHeight/ sourceWdith;
            HEIGHT = (int)(aspectRatio * WIDTH);
            //Grab the variable imageRezied
            imageRezied = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
            //If not sure for the type, you can use var first, and then fix it later with correct Fraphic2d
            Graphics2D graphic = imageRezied.createGraphics();
            graphic.drawImage(imageSource,0,0,WIDTH,HEIGHT,null);
            graphic.dispose();


        }catch(IOException e){
            JOptionPane.showMessageDialog(rootPane, "Error loading the image");
        }



        //buttons
        buttonsList = new ArrayList<FancyButton>();
        for(int i =0; i < COLUMNS * ROWS; i++)
        {
        //To cut the picutre into small pieces
        int row = i/COLUMNS;
        int column = i % COLUMNS;
        Image imagedlice = createImage(
            new FilteredImageSource(imageRezied.getSource(),
            new CropImageFilter(column*WIDTH/COLUMNS, row *HEIGHT/ROWS, WIDTH/COLUMNS, HEIGHT/ROWS)));
        //create JBUTTONS
        FancyButton btn = new FancyButton();
        btn.addActionListener(e -> MyClickEventHandler(e));

//remove the last piece of the pic
      if(i == COLUMNS*ROWS -1)//the last btn
      {
        btn.setBorderPainted(false);//set no border
        btn.setContentAreaFilled(false);//set no fill
    
      }
      else{
    btn.setIcon(new ImageIcon(imagedlice));
      }
    


        buttonsList.add(btn);
        


        }
//shuffle the buttons
        Collections.shuffle(buttonsList);
        for(var button: buttonsList)
        {panel.add(button);}


        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);

    }

    private void MyClickEventHandler(ActionEvent e)
    {
//get the btn that is clicked
    FancyButton btnClicked = (FancyButton)e.getSource();
    int i = buttonsList.indexOf(btnClicked);//we need to know the btm we click in which colum aand row
    int column = i % COLUMNS;
    int row = i/COLUMNS;

    int emptyIndex= -1;//creat an variable and initi it with -1
    //this is going to find our empty button
    for(int j=0; j < buttonsList.size(); j ++)
    {
        if(buttonsList.get(j).getIcon()== null)
        {
            emptyIndex = j;
        }
    }
    int emptyColumn = emptyIndex % COLUMNS;
    int emptyRow = emptyIndex /COLUMNS;
    
    //clicked column 1, empty is column 2
    //3-2, if it is 1 difference, it means they are next to each other
    if((emptyRow == row && Math.abs(column - emptyColumn)==1 ||
    emptyColumn == column && Math.abs(row - emptyRow)==1))
    {
        Collections.swap(buttonsList,i,emptyIndex);
        updateButtons();

    }

    if(buttonsSolution.equals(buttonsList))
    {
        JOptionPane.showMessageDialog(btnClicked, "Well done! You won!!!");
    }


    }
    public void updateButtons()
    {
        panel.removeAll();
        for(var btn: buttonsList)
        {
            panel.add(btn);
        }
        panel.validate();
    }
    

    //Load Image
    private BufferedImage loadImage() throws IOException{
    
        return ImageIO.read(new File("HelloKitty.png"));
    }
    
}
