/*class Arrow extends Form
{
  
  float rotate = 0;
  boolean cercleOn,pos1,pos2;

  public Arrow(float x, float y, float w, float h) //constructeur
  {
    super(x, y, w, h);
  }
  
  public void draw()
  {
    //fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
    strokeWeight(5);
    stroke(255);
    stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    if(this.selected)
    {
      fill(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
      ellipse(x, y,200,200);//cerlcle qui utilise la position de w et h
      ellipse(w, h,200,200);//cerlcle qui utilise la position de w et h
      strokeWeight(8);
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    }
    if(this.y > this.h-100 && this.y < this.h +100 && this.x < this.w)
    {
    triangle(this.x-50, this.y, this.x, this.y+50, this.x, this.y-50);
    }
    if(this.y > this.h-100 && this.y < this.h +100 && this.x > this.w)
    {
    triangle(this.x+50, this.y, this.x, this.y+50, this.x, this.y-50);
    }
    line(this.x,this.y, this.w, this.h);
  }
public void setRotate(float rotate) //rotation
  {
  }
   public void setPosition(float x, float y) //deplacement
  {
    if(this.pos1)
    {
      //modification pos du cercle x y
      this.x = x;
      this.y = y;
    }
    else if(this.pos2)
    {
      //modification pos du cercle w h 
      this.w = x;
      this.h = y;
    }
  }

  public float getRotate()
  {
    return rotate;
  }

  public void setSize(float d) //distance
  {
  }
 
  public boolean onOver(float x, float y) // Verification si on est sur l'objet
  {
     //les calcules nécessaire
   float dx = this.x - x;
   float dy = this.y - y;
   float dx2 = this.w - x;
   float dy2 = this.h - y;
   
    if(sqrt(sq(dx) + sq(dy)) < 200 )
    {
      //verificatio nsi on est sur le cercle 1
      this.pos1 = true;
      this.pos2=false;
    }
    else if(sqrt(sq(dx2) + sq(dy2)) < 200 )
    {
      //verificatio nsi on est sur le cercle 2
       this.pos2 = true;
       this.pos1=false;
    }
    if(sqrt(sq(dx) + sq(dy)) < 100 ||sqrt(sq(dx2) + sq(dy2)) < 200)
    {
      //verificatio nsi on est sur l'un des cercle pour tout afficher
      this.cercleOn = true;
      return true;
    }
    else 
    {
      this.cercleOn = false;
      this.pos1=false;
      this.pos2=false;
      return false;
    }
  }
}*/
