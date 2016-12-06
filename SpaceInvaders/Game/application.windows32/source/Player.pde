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
  
  void move(int x)
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
  void draw()
  {
    image(playerImage,  xpos-PLAYER_DIMENSIONS, ypos-PLAYER_DIMENSIONS);
  } 
  
  void gameOver(boolean dead)
  {
    if(dead)
    {
      endGame();
    }
    
  }
}