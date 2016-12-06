import ddf.minim.spi.*;
import ddf.minim.signals.*;
import ddf.minim.*;
import ddf.minim.analysis.*;
import ddf.minim.ugens.*;
import ddf.minim.effects.*;

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


void setup()
{
  minim = new Minim(this);
  size(600, 600);
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

void draw()
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

void init_array(Alien theArray[])
{
  for (int  i=0; i<theArray.length; i++)
  {
    theArray[i]  =  new  Alien(int(i*20 + ((i-1)*20)), int(0));
    
  }
}

void  draw_array(Alien theArray[])
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
void  move_array(Alien theArray[])
{
  for (int  i=0; i<theArray.length; i++)
  {
      theArray[i].move();    
  }
}

void mousePressed()
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

void endGame()
{
  gameOver = true;
}

void playerWinCheck(Alien theArray[])
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

void checkBombs(Alien theArray[])
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
void restart()
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