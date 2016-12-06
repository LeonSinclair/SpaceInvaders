class Bullet
{
  float xpos;
  float ypos;
  float dy=5;
  int powerUpNumber =1;
  color paddlecolor=color(0);
  Bullet(Player tp)
  {
    xpos = tp.xpos-PLAYER_DIMENSIONS;
    ypos = tp.ypos-PLAYER_DIMENSIONS;
  }
  
  boolean draw(boolean bulletOnScreen)       
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
  
  void move(boolean poweredUp)
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
  boolean collide(Alien alienArray[], int count, boolean poweredUp)
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
