class Alien
{
  float x=0;
  float y=0;
  float dx=2.5;
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
  
  void draw()
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
  
  void move()
  {
    if(!superAlien)
    {
      x=x+dx;
      if(dx<0)
      {
        dx = dx -.0001;
      }
      else
      {
        dx = dx +.0001;
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
        dx = dx -.0001;
      }
      else
      {
        dx = dx +.0001;
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
  void explode()
  {
    exploded = true;
  }
  
  void resetBombs()
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

