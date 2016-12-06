import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import ddf.minim.spi.*; 
import ddf.minim.signals.*; 
import ddf.minim.*; 
import ddf.minim.analysis.*; 
import ddf.minim.ugens.*; 
import ddf.minim.effects.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class Lab_4 extends PApplet {








Minim minim;

Alien newAliens[];
Player thePlayer;
Bullet theBullet;
boolean gameOver = false;
boolean bulletOnScreen = false;
boolean poweredUp;
AudioPlayer shoot;
AudioPlayer themeSong;
AudioPlayer explosion;


public void setup()
{
  minim = new Minim(this);
  
  background(255);
  newAliens = new Alien[12];
  thePlayer = new Player();
  theBullet = new Bullet(thePlayer);
//  shields = new Shield[3];
  //shieldsUp(shields);
  
  init_array(newAliens);
  themeSong = minim.loadFile("bgm.mp3");
  shoot = minim.loadFile("shoot.wav");
  explosion = minim.loadFile("explosion.wav");
  themeSong.loop();
 
}

public void draw()
{
  if(gameOver==false)
  {
    background(255);
    draw_array(newAliens);
    move_array(newAliens);
    checkBombs(newAliens);
    thePlayer.draw();
    thePlayer.move(mouseX);
    playerWinCheck(newAliens);
   // shieldDraw(shields);
    if(poweredUp)
    {
      text("Fired up!", 10, 300); 
      fill(255, 0, 0);
    }
    if(bulletOnScreen)
    {
      bulletOnScreen = theBullet.draw(bulletOnScreen);
      theBullet.move(poweredUp);
      for(int count =0; count<newAliens.length; count++)
      {
       poweredUp = theBullet.collide(newAliens, count, poweredUp);
      }
    }
  }
  else if(thePlayer.dead==true)
  {
    String youSuck = "GAME OVER......";
    text(youSuck, SCREENX/2 - textWidth(youSuck)/2, 15);
  }
  else
  {
    String youRule = "A WINNER IS YOU!";
    text(youRule, SCREENX/2 - textWidth(youRule)/2, 15);
  }
  
  
  
}

public void init_array(Alien theArray[])
{
  for (int  i=0; i<theArray.length; i++)
  {
    theArray[i]  =  new  Alien(PApplet.parseInt(i*20 + ((i-1)*20)), PApplet.parseInt(0));
    
  }
}

public void  draw_array(Alien theArray[])
{  
  for (int  i=0; i<theArray.length; i++)
  {
    //if(i<(int)(theArray.length/4))
    //{
    // theArray[i].superAlien=true;
   // }
    theArray[i].draw();
  }
}
public void  move_array(Alien theArray[])
{
  for (int  i=0; i<theArray.length; i++)
  {
      theArray[i].move();    
  }
}

public void mousePressed()
{
  if(bulletOnScreen==false)
  {
    shoot.play();
    theBullet = new Bullet(thePlayer);
  }
  bulletOnScreen =true;
  
  if(gameOver==true)
  {
    restart();
  }
}

public void endGame()
{
  gameOver = true;
}

public void playerWinCheck(Alien theArray[])
{
  for(int i =0; i<theArray.length; i++)
  {
    if(theArray[i].exploded == false)
    {
      break;
    }
    else if(i==theArray.length - 1 && theArray[i].exploded == true)
    {
      endGame();
    }
  }
}

public void checkBombs(Alien theArray[])
{
  for(int i = 0; i<theArray.length; i++)
  {
     theArray[i].resetBombs();
     if(theArray[i].alienBomb!=null)
     {
       theArray[i].alienBomb.collide(thePlayer);
    //   theArray[i].alienBomb.shieldCollision(shields);
     }
     
  }
}
public void restart()
{
  setup();
  gameOver = false;
}

//void shieldsUp(Shield shieldArray[])
//{
// for(int i = 0; i<shieldArray.length;i++)
// {
//   shieldArray[i] = new Shield((i+1)*100 + (i*100), 500);
// } 
//}

//void shieldDraw(Shield shieldArray[])
//{
// for (int i = 0; i<shieldArray.length; i++)
// {
//  shieldArray[i].draw();
// } 
//}
class Alien
{
  float x=0;
  float y=0;
  float dx=2.5f;
  boolean exploded = false;
  boolean superAlien = false;
  boolean dead = false;
  int superAlienColumns =2;
  int deathTimer=0;
  int superAlienY=0;
  PImage alienImage;
  PImage explodingImage;
  PImage superAlienImage;
  Bomb alienBomb;
  Alien(int xpos, int ypos)
  {
   alienBomb = null;
   x=(float)xpos;
   y=(float)ypos;
   alienImage = loadImage("spacer.GIF");
   explodingImage = loadImage("betterexplosion.gif");
   superAlienImage = loadImage("spacer.png");
  }
  
  public void draw()
  {
    if(exploded && !dead && !superAlien)
    {
     image(explodingImage, x-(deathTimer*dx), y-10);
     deathTimer++;
     if(deathTimer>=10)
     {
       dead = true;
     }
    }
    else if(!dead && !superAlien)
    {
      image(alienImage, x, y);
    }
    else if(superAlien && !exploded)
    {
      superAlienY = (int)(3*sin(x/32)+ (superAlienColumns)*25);
      image(superAlienImage, x, superAlienY );
    }
    else if(superAlien && exploded && !dead)
    {
     image(explodingImage, x-(deathTimer*dx), superAlienY);
     deathTimer++;
     if(deathTimer>=10)
     {
       dead = true;
     }
    }
    
  }
  
  public void move()
  {
    if(!superAlien)
    {
      x=x+dx;
      if(dx<0)
      {
        dx = dx -.0001f;
      }
      else
      {
        dx = dx +.0001f;
      }
      if(x>=(SCREENX-MARGIN))
      {
        y=y+25;
        dx=-dx;
      }
      else if(x<=-5 && dx<0)
      {
        y=y+25;
        dx=-dx;
      }
    }
    else
    {
      x=x+dx;
      if(dx<0)
      {
        dx = dx -.0001f;
      }
      else
      {
        dx = dx +.0001f;
      }
      if(x>=(SCREENX-MARGIN))
      {
        superAlienColumns++;
        dx=-dx;
      }
      else if(x<=-5 && dx<0)
      {
        superAlienColumns++;
        dx=-dx;
      }
    }
    if(alienBomb == null)
    {
      int randomNo = (int)random(0,300);
      if(randomNo == 2)
      {
        alienBomb = new Bomb((int)x+ALIEN_WIDTH, (int)y);
      }
      
    }
  }
  public void explode()
  {
    exploded = true;
  }
  
  public void resetBombs()
  {
   if(alienBomb!=null)
   {
     alienBomb.move();
     alienBomb.draw();
     alienBomb.offScreen();
     if(alienBomb.offScreen==true)
     {
       alienBomb = null;
     }
   }
   
   
  }

}
class Bomb
{
  int xpos;
  int ypos;
  boolean offScreen;
  Bomb(int x, int y)
  {
    xpos = x + ALIEN_WIDTH/2;
    ypos = y;
    offScreen = false;
  } 
  
  public void move()
  {
    ypos +=1;
  }
  
  public void draw()
  {
    fill(255,0,0);
    rect(xpos, ypos, BOMB_WIDTH, BOMB_HEIGHT);
  }
  
  public void offScreen()
  {
    if(ypos>=SCREENY)
    {
      offScreen = true;
    }
  }
  
  public void collide(Player tp)
  {
    if(((ypos+BOMB_HEIGHT)>=(tp.ypos-PLAYER_DIMENSIONS)) && (xpos+BOMB_WIDTH>=(tp.xpos-PLAYER_DIMENSIONS)) && (xpos<=tp.xpos+PLAYER_DIMENSIONS-PLAYER_DIMENSIONS) && (ypos<=tp.ypos+PLAYER_DIMENSIONS-PLAYER_DIMENSIONS))
    {
      explosion.play();
      tp.dead = true;
      tp.gameOver(tp.dead);
    }
  }
  
 // void shieldCollision(Shield shield[])
 // {
 //   for(int i=0; i<shield.length;i++)
  //  {
 //     if(ypos+BOMB_HEIGHT>=shield[i].ypos && xpos+BOMB_WIDTH>=shield[i].xpos && xpos<=shield[i].xpos+SHIELD_DIMENSIONS && ypos<=shield[i].ypos-SHIELD_DIMENSIONS)
 //     {
 //        ypos = -1000;
 //        offScreen = true;
 //     }
 //   }
    
 // }
  
}
class Bullet
{
  float xpos;
  float ypos;
  float dy=5;
  int powerUpNumber =1;
  int paddlecolor=color(0);
  Bullet(Player tp)
  {
    xpos = tp.xpos-PLAYER_DIMENSIONS;
    ypos = tp.ypos-PLAYER_DIMENSIONS;
  }
  
  public boolean draw(boolean bulletOnScreen)       
  {

    fill(paddlecolor);
    rect(xpos+(PLAYER_DIMENSIONS/2), ypos-PLAYER_DIMENSIONS, BULLET_WIDTH, BULLET_LENGTH);
    if(ypos+BULLET_LENGTH<0)
    {
      bulletOnScreen=false;
    }
    if(poweredUp)
    {
       text("Fired up!", 10, 300); 
       fill(0, 102, 153);
    }
    return bulletOnScreen;
  
  }
  
  public void move(boolean poweredUp)
  {
    if(poweredUp == false)
    {
       ypos=ypos-dy;
    }
    else
    {
      ypos = ypos -(2*dy);
    }
  }
  public boolean collide(Alien alienArray[], int count, boolean poweredUp)
  {
      if((((xpos+BULLET_WIDTH)>=(alienArray[count].x)) && ((xpos+BULLET_WIDTH)<=((alienArray[count].x) + ALIEN_WIDTH))) && ((ypos-BULLET_LENGTH)<=((alienArray[count].y)+ ALIEN_HEIGHT) && (ypos-BULLET_LENGTH>=(alienArray[count].y))))
      {
       if(alienArray[count].exploded==false)
       {
        ypos = ypos - BULLET_LENGTH;
       }
       alienArray[count].exploded = true;
       int powerUpCheck = (int) random(0,2);
       if(powerUpNumber == powerUpCheck)
       {
         poweredUp = true;
       }
      }
      return poweredUp;

    
    
  }
 
  
}
final int SCREENX = 600;
final int SCREENY = 600;
final int MARGIN = 20;
final int SUPERMARGIN = 50;
final int PLAYER_DIMENSIONS = 20;
final int BULLET_WIDTH = 2;
final int BULLET_LENGTH = 20;
final int ALIEN_WIDTH = 30;
final int ALIEN_HEIGHT = 25;
final int BOMB_WIDTH = 4;
final int BOMB_HEIGHT = 15;
final int SHIELD_DIMENSIONS = 40;
class Player
{
  boolean dead;
  int xpos;
  int ypos;
  PImage playerImage;
  Player() 
  { 
    dead = false;
    xpos=SCREENX/2;
    ypos=SCREENY-10;
    playerImage= loadImage("player.png");
    playerImage.resize(0,25);
    playerImage.resize(25,0);
  } 
  
  public void move(int x)
  {
    if(x<=25)
    {
      xpos = 25;
    }
    else
    {
       xpos = x;
    }
   
  }
  public void draw()
  {
    image(playerImage,  xpos-PLAYER_DIMENSIONS, ypos-PLAYER_DIMENSIONS);
  } 
  
  public void gameOver(boolean dead)
  {
    if(dead)
    {
      endGame();
    }
    
  }
}
  public void settings() {  size(600, 600); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "Lab_4" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
