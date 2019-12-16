class Circle extends Form
{
  float rotate = 0; 
    boolean cercleOn,pos1,pos2,pos3;
    float L = 100;
    float c = 40;
    float y2 = y;
  
  public Circle(float x, float y, float d) //constructeur
  {
    super(x, y, d, d);
  }

  public void draw()
  {

    strokeWeight(5);
    stroke(255);
    stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    if(this.selected)
    {
      //si cet objet est selectionné
      ellipse(this.x+300,y2+150,100,100);//cerlcle -
      line(this.x+275,y2+150,this.x+325,y2+150);
      ellipse(this.x+300,y2-150,100,100); //cerlcle +
      line(this.x+275,y2-150,this.x+325,y2-150);
      line(this.x+300,y2-125,this.x+300,y2-175);
      strokeWeight(8);
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    }
    if(!this.selected)
    {
      noFill();
        
    }
    ellipse(this.x, this.y, this.w, this.h);
  }

  public boolean onOver(float x, float y)  // Verification si on est sur l'objet
  { 
    //les calcules nécessaire
   float dx = this.x - x;
   float dy = this.y - y;
   float dr = this.x+300 - x;
   float dt = (y2+100) - y;
   float dt2 = (y2-100) - y;


    if(sqrt(sq(dr) + sq(dt)) < 200/2 )
    {
      //verificatio nsi on est sur le cercle 1
      this.pos1 = true;
      this.pos2 = false;
      this.pos3 = false;
    }
     else if(sqrt(sq(dr) + sq(dt2)) < 200/2 )
    {
      //verificatio nsi on est sur le cercle 2
      this.pos1 = false;
      this.pos2 = true;
      this.pos3 = false;
    }
    else if (sqrt(sq(dx) + sq(dy)) < this.w/2 )
    {
      //verificatio nsi on est sur le cercle pour le déplacé
      this.pos1 = false;
      this.pos2 = false;
      this.pos3 = true;
    }
    if (sqrt(sq(dx) + sq(dy)) < this.w/2 || sqrt(sq(dr) + sq(dt)) < 200/2 || sqrt(sq(dr) + sq(dt2)) < 200/2 )
    {
      //verificatio nsi on est sur l'un des cercle pour tout afficher
      return true;
    }

    else
    {
      this.pos1=false;
      this.pos2=false;
      this.pos3 = false;

      return false;
    }
  }
    public void setPosition(float x, float y) //deplacement
  {
    if(this.pos3)
    {
      //modification pos du cercle x y
      this.x = x;
      this.y = y;
      y2 = y;
    }

  }
  
  public void setSize(float d) //distance
  {

    if(this.pos1)
    {
        this.w = this.w - d;
        this.h = this.h - d;
        if(this.w < 100)
        {
          this.w = 100;
          this.h = 100;
        }
    }
    else if(this.pos2)
    {
        this.w = this.w + d;
        this.h = this.h + d;
         if(this.w > 500)
        {
          this.w = 500;
          this.h = 500;
        }
    }
    }
    public float getSize()
    {
       return this.w;
    }

      public void setSize2(float d) //distance
  {
         if(d < 45 && d > -45)
    {
        this.w = this.w + d;
        this.h = this.h + d;
       if(this.w > 500)
        {
          this.w = 500;
          this.h = 500;
        }
      if(this.w < 100) 
      {
        this.w = 100;
        this.h = 100;
      }
    }
  }

    }
