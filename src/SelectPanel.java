/*
SelectPanel.java
Sat Arora
Class that holds the simple interface of playing or editing a level.
 */

//importing packages
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.io.*;
import java.util.ArrayList;

public class SelectPanel extends JPanel implements MouseListener {
    private App mainFrame; //storing the frame
    private Image plus, plusClicked; //images for clicking on the plus button to add a new level
    private Ellipse2D.Double plusEllipse; //collision with plus button
    private Font font15, font25, font30, font25Bold, font45, font45Bold, font60; //different font sizes
    private String welcome; //welcome text
    private String description; //description text
    private ArrayList<FileData> fileDataArrayList; //arraylist of all text files that can be converted to games or to be edited
    private Rectangle[] dataRects; //rectangle array storing the locations of the options of the games to play
    public static final Color LIGHTGREEN = new Color(144,238,144);
    private boolean mouseOn;
    private int clickedIdx; //the index of the option that was clicked on
    private Rectangle playRect, editRect, cancelRect;
    //contsructor
    public SelectPanel(App a) throws IOException {
        setLayout(null);
        mainFrame = a;
        addMouseListener(this);
        mouseOn = false;
        plus = ImageIO.read(new File("plus.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        plusClicked = ImageIO.read(new File("plusClicked.png")).getScaledInstance(50,50,Image.SCALE_SMOOTH);
        plusEllipse = new Ellipse2D.Double(1125,800,50,50);
        font15 = new Font("System San Francisco Display Regular.ttf",Font.TRUETYPE_FONT,15);
        font25 = new Font("System San Francisco Display Regular.ttf",Font.TRUETYPE_FONT,25);
        font30 = new Font("System San Francisco Display Regular.ttf",Font.TRUETYPE_FONT,30);
        font25Bold = new Font("System San Francisco Display Regular.ttf",Font.BOLD,25);
        font45 = new Font("System San Francisco Display Regular.ttf",Font.TRUETYPE_FONT,45);
        font45Bold = new Font("System San Francisco Display Regular.ttf",Font.BOLD,45);
        welcome = "Welcome to Gamestar Mechanic!";
        description = "Click on a level to edit it. Otherwise, click on the plus icon to add a new level!";
        clickedIdx = -1;
        fileDataArrayList = new ArrayList<>();
        //opening file
        File gamesFolder = new File("Games");
        if (gamesFolder.isDirectory()) { //checking if the Games is a folder directory
            for (File file : gamesFolder.listFiles()) { //opening all files, storing them
                fileDataArrayList.add(new FileData(file,file.getName()));
            }
        }
    }
    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }
    public void update() throws IOException, ClassNotFoundException {
        Point mouse = getMousePosition();
        if (mouse == null) mouse = new Point(0,0);
        if (clickedIdx != -1) {
            //options for cancel, play, edit
            if (cancelRect.contains(mouse)) {
                clickedIdx = -1;
            }
            if (playRect.contains(mouse)) {
                mainFrame.switchPanel(mainFrame.LEVELPANEL);
            }
            if (editRect.contains(mouse)) {
                mainFrame.switchPanel(mainFrame.EDITPANEL);
            }
            return;
        }
        if (plusEllipse.contains(mouse)) { //adding new level, goes to editor
            clickedIdx = -1;
            mainFrame.switchPanel(mainFrame.EDITPANEL);
        }
        for (int i = 0; i < dataRects.length; i++) {
            if (dataRects[i].contains(mouse)) {
                clickedIdx = i;
            }
        }
    }
    public void paintComponent(Graphics g) {
        if (dataRects == null) {
            initializeRects(g);
        }
        Point mouse = getMousePosition();
        if (mouse == null) mouse = new Point(0,0);
        g.setColor(LIGHTGREEN);
        g.fillRect(0,0,getWidth(),getHeight()); //background light green
        g.setFont(font45Bold);
        g.setColor(Color.BLACK);
        g.drawString(welcome,EditPanel.ctrPosition(this.getBounds(),welcome,g),100); //drawing the header (welcome)
        g.setFont(font30);
        g.drawString(description,EditPanel.ctrPosition(this.getBounds(),description,g),140); //drawing the instructions/description
        g.setFont(font25Bold);
        if (clickedIdx != -1) { //one of the games has been clicked
            g.setColor(new Color(0,0,0,100));
            g.fillRect(0,0,getWidth(),getHeight()); //shading in background
            g.setColor(Color.WHITE);
            g.fillRect(400,300,400,270); //showing pop up
            g.setColor(Color.BLACK);
            //drawing the three options (cancel, edit, play)
            if (playRect.contains(mouse)) {
                g.setColor(Color.GREEN);
                g.fillRect(playRect.x,playRect.y,playRect.width,playRect.height);
                g.setColor(Color.BLACK);
            }
            else if (editRect.contains(mouse)) {
                g.setColor(Color.BLUE);
                g.fillRect(editRect.x,editRect.y,editRect.width,editRect.height);
                g.setColor(Color.BLACK);
            }
            else if (cancelRect.contains(mouse)) {
                g.setColor(Color.RED);
                g.fillRect(cancelRect.x,cancelRect.y,cancelRect.width,cancelRect.height);
                g.setColor(Color.BLACK);
            }
            g.drawRect(playRect.x,playRect.y,playRect.width,playRect.height);
            g.drawRect(editRect.x,editRect.y,editRect.width,editRect.height);
            g.drawRect(cancelRect.x,cancelRect.y,cancelRect.width,cancelRect.height);
            g.setFont(font25);
            g.drawString("Play",playRect.x+10,playRect.y+30);
            g.drawString("Edit",editRect.x+10,editRect.y+30);
            g.drawString("Cancel",cancelRect.x+10,cancelRect.y+30);
            return;
        }
        for (int i = 0; i < dataRects.length; i++) {
            Rectangle r = dataRects[i];
            if (r.contains(mouse)) {
                g.setColor(Color.PINK);
                g.fillRect(r.x,r.y,r.width,r.height); //drawing the rectangle around the level name
                g.setColor(Color.BLACK);
            }
            FileData fileData = fileDataArrayList.get(i);
            g.drawRect(r.x,r.y,r.width,r.height);
            g.drawString(fileData.getFname().substring(0,fileDataArrayList.get(i).getFname().length()-4),r.x+5,r.y+25); //drawing level name
        }
        //plus button color depending on whether the mouse is on top of the button
        if (plusEllipse.contains(mouse)) {
            g.drawImage(plusClicked,(int)plusEllipse.x,(int)plusEllipse.y,null);
        }
        else {
            g.drawImage(plus,(int)plusEllipse.x,(int)plusEllipse.y,null);
        }

    }
    public void initializeRects(Graphics g) {
        dataRects = new Rectangle[fileDataArrayList.size()];
        int paintX = 100, paintY = 200;
        g.setFont(font25Bold);
        for (int i = 0; i < dataRects.length; i++) {
            //creating rectangle bounds around game name
            dataRects[i] = new Rectangle(paintX,paintY,g.getFontMetrics().stringWidth(fileDataArrayList.get(i).getFname().substring(0,fileDataArrayList.get(i).getFname().length()-4))+10,35);
            paintY += 50;
        }
        g.setFont(font25);
        playRect = new Rectangle(EditPanel.ctrPosition(new Rectangle(400,300,400,270),"Play",g)-10,350,g.getFontMetrics().stringWidth("Play")+20,50);
        editRect = new Rectangle(EditPanel.ctrPosition(new Rectangle(400,300,400,270),"Edit",g)-10,410,g.getFontMetrics().stringWidth("Edit")+20,50);
        cancelRect = new Rectangle(EditPanel.ctrPosition(new Rectangle(400,300,400,270),"Cancel",g)-10,470,g.getFontMetrics().stringWidth("Cancel")+20,50);
    }

    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseClicked(MouseEvent e){}
    public void mousePressed(MouseEvent e) {
        try {
            update();
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
    }
    //method that gets the current file chosen to either play or edit (returns null if nothing was chosen)
    public File getFile() {
        if (clickedIdx != -1) return fileDataArrayList.get(clickedIdx).getFile();
        return null;
    }
}
