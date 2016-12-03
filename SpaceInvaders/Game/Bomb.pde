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
  
  void move()
  {
    ypos +=1;
  }
  
  void draw()
  {
    fill(255,0,0);
    rect(xpos, ypos, BOMB_WIDTH, BOMB_HEIGHT);
  }
  
  void offScreen()
  {
    if(ypos>=SCREENY)
    {
      offScreen = true;
    }
  }
  
  void collide(Player tp)
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
