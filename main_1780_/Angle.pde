class Angle extends Form
{
  float angle; 
  float rotate = 2*PI/3;
  boolean cercleOn,pos1,pos2,pos3;
  float x2 = 200+x;
  float y2 = 100+y;
  float res;
  public Angle(float x, float y, float w, float h) //constructeur
  {
    super(x, y, w, h);
  }

  public void draw()
  {
    strokeWeight(5);
    stroke(255);
    stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    if(this.selected)
    {
      //si cet objet est selectionné
      fill(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue")); 
      ellipse(x, y,100,100);  //cerlcle qui utilise la position de x et y
      ellipse(w, h,70,70);  //cerlcle qui utilise la position de w et h
      ellipse(x2, y2,100,100);  //cerlcle qui utilise la position de x2 et y2
      strokeWeight(8);
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    }
    line(this.x,this.y, this.w, this.h);  //ligne qui utilise la position de x, y et w h en commun avec la ligne 2 qui elle utilise x2 y2 et w h
    line(this.x2, this.y2, this.w, this.h);
      // je refait un if selected apres les deux ligne pour que le text se superpose (index plus haut)
     if(this.selected)
     {
      fill(255);
      noStroke();
      rect(this.w-30,this.h-18,60,30);
      fill(255,0,0);
      textSize(30);
      text(int(degrees(res)+""),this.w-28,this.h+9);
     }
  }
      public String getName(){
    return "angle";}
  public void setSize(float d) //taille
  {
  }
    public float getTextX()
  {
    return x;
  }
  public float getTextY()
  {
    return y;
  }
      public float getSize()
    {
       return this.w;
    }
  public boolean onOver(float x, float y)  // Verification si on est sur l'objet
  {
    //les calcules nécessaire
    float dx = this.x - x;
    float dy = this.y - y;
    float dx2 = this.x2 - x;
    float dy2 = this.y2 - y;
    float dh = (this.w) - x;
    float dw = (this.h) - y;
    // théorem d'alkashi
    float lg1 = sq(this.w - this.x)  + sq(this.h - this.y);
    float lg2 = sq(this.w - this.x2) + sq(this.h - this.y2);
    float lg3 = sq(this.x - this.x2) + sq(this.y - this.y2);
    float cos = lg3 - lg1 -lg2;
    float cos1 = - 2* sqrt(lg1)*sqrt(lg2);
    float angle = cos/cos1;
    this.res = acos(angle);
    //conversion de l'angle en degré
    println(degrees(res));
    if(sqrt(sq(dx) + sq(dy)) < 100 )
    {     
      //verification si on est sur le cercle 1
      this.pos1 = true;
      this.pos2=false;
      this.pos3=false;
    }
    else if(sqrt(sq(dx2) + sq(dy2)) < 100 )
    {
      //verification si on est sur le cercle 2
       this.pos2 = true;
       this.pos1=false;
       this.pos3=false;
    }
    else if(sqrt(sq(dw) + sq(dh)) < 100 )
    {        
      //verification si on est sur le cercle 3
       this.pos3 = true;
       this.pos2 = false;
       this.pos1=false;
    }
    if(sqrt(sq(dx) + sq(dy)) < 100 ||sqrt(sq(dx2) + sq(dy2)) < 100 || sqrt(sq(dw) + sq(dh)) < 100 )
    {
      //verification si on est sur l'un des cercle affiche tout les cercles.
      return true;
    }
    else 
    {
      this.pos1=false;
      this.pos2=false;
      this.pos3=false;
      return false;
    }
  }
  void keyPresseded(char a, int b)
  {
  }
  public float getRotate()
  {
    return rotate;
  }
  
  public void setPosition(float x, float y) //deplacement
  {
    if(this.pos1)
    {
      //modification pos du cercle 1 et donc de x et y
      this.x = x;
      this.y = y;
    }
    else if(this.pos2)
    {
      //modification pos du cercle 2 et donc de x2 et y2
      this.x2 = x;
      this.y2 = y;
    }
        else if(this.pos3)
    {
      //modification pos du cercle 3  et donc de w et h
      this.w = x;
      this.h = y;
    }
  }
    public void setSize2(float d) //distance
  {
  }
  public void setRotate(float a) //rotation
  {
  }
}
