import javax.swing.*;
import java.awt.*;

public class Enemy extends Sprite  {
    public static final int RIGHT = 0, UP = 1, LEFT = 2, DOWN = 3;
    private String id;
    private int speed;
    private int health;
    private int direction;
    private boolean stationary;
    private int bulletSpeed;
    private ImageIcon[] rightSprites, upSprites, leftSprites, downSprites;
    private ImageIcon[][] sprites;
    public Enemy(String id, int x, int y, ImageIcon image, int speed, int health) {
        super(id,x,y,image);
        if (EditPanel.find(EditPanel.enemyTopDownStrings,id) != -1)
            super.setImg(new ImageIcon(new ImageIcon(String.format("Top-Down/%sL0.png",id)).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
        else
            super.setImg(new ImageIcon(new ImageIcon(String.format("Platform/%sL0.png",id)).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH)));
        this.id = id;
        this.speed = speed;
        this.health = health;
        this.direction = LEFT;
        this.stationary = false;
        super.hitBox = new Rectangle(x+5,y+5,75-10,75-10);
        this.bulletSpeed = 5;
        if (EditPanel.find(EditPanel.enemyTopDownStrings,this.id) != -1) {
            rightSprites = new ImageIcon[3];
            upSprites = new ImageIcon[3];
            leftSprites = new ImageIcon[3];
            downSprites = new ImageIcon[3];
            for (int i = 0; i < 3; i++) {
                rightSprites[i] = new ImageIcon(new ImageIcon(String.format("Top-Down/%sR%d.png",this.id,i)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
                upSprites[i] = new ImageIcon(new ImageIcon(String.format("Top-Down/%sU%d.png",this.id,i)).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
                downSprites[i] = new ImageIcon(new ImageIcon(String.format("Top-Down/%sD%d.png",this.id,i)).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
                leftSprites[i] = new ImageIcon(new ImageIcon(String.format("Top-Down/%sL%d.png",this.id,i)).getImage().getScaledInstance(75,75,Image.SCALE_SMOOTH));
            }
            sprites = new ImageIcon[4][3];
            sprites[RIGHT] = rightSprites;
            sprites[UP] = upSprites;
            sprites[LEFT] = leftSprites;
            sprites[DOWN] = downSprites;
        }
        else {
            rightSprites = new ImageIcon[5];
            leftSprites = new ImageIcon[5];
            upSprites = null;
            downSprites = null;
            for (int i = 0; i < 3; i++) {
                rightSprites[i] = new ImageIcon(new ImageIcon(String.format("Platform/%sR%d.png",this.id,i)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
                leftSprites[i] = new ImageIcon(new ImageIcon(String.format("Platform/%sL%d.png",this.id,i)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
            }
            rightSprites[3] = new ImageIcon(new ImageIcon(String.format("Platform/%sRU.png",this.id)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
            rightSprites[4] = new ImageIcon(new ImageIcon(String.format("Platform/%sRD.png",this.id)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
            leftSprites[3] = new ImageIcon(new ImageIcon(String.format("Platform/%sLU.png",this.id)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
            leftSprites[4] = new ImageIcon(new ImageIcon(String.format("Platform/%sLD.png",this.id)).getImage().getScaledInstance(75,75, Image.SCALE_SMOOTH));
            sprites = new ImageIcon[4][5];
            sprites[0] = rightSprites;
            sprites[1] = null; sprites[3] = null;
            sprites[2] = leftSprites;
        }

    }
    public void init() {super.setInstance(this);}
    public int getHealth() {return this.health;}
    public int getSpeed() {return this.speed; }
    public String getId() {return this.id;}

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public boolean isStationary() {
        return stationary;
    }

    public void setStationary(boolean stationary) {
        this.stationary = stationary;
    }

    public void setHealth(int h) {this.health = h;}
    public void setSpeed(int s) {this.speed = s;}
    public void setId(String i) {this.id = i;}
    public Sprite getSprite() { return super.getSprite(); }
    public int getBulletSpeed() { return this.bulletSpeed; }
    public void setBulletSpeed(int bulletSpeed) { this.bulletSpeed = bulletSpeed; }




}
