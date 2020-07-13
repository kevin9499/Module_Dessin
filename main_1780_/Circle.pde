class Circle extends Form
{
  float rotate = 0; 
    boolean cercleOn,pos1,pos2,pos3;
    float L = 100;
    float c = 40;
    boolean cercle = false;
    float x2 = this.w/2;
    float y2 = 0;
  public Circle(float x, float y, float d) //constructeur
  {
    super(x, y, d, d);
  }

  public void draw()
  {
    noFill();
    strokeWeight(5);
    stroke(255);
    stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    if(this.selected)
    {
      //si cet objet est selectionné
      fill(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
      ellipse(this.x+x2,this.y+y2,100,100);
      noFill();
      //ellipse(this.x+300,y2+150,100,100);//cerlcle -
      //line(this.x+275,y2+150,this.x+325,y2+150);
      //ellipse(this.x+300,y2-150,100,100); //cerlcle +
      //line(this.x+275,y2-150,this.x+325,y2-150);
      //line(this.x+300,y2-125,this.x+300,y2-175);
      strokeWeight(8);
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    }
    if(!this.selected)
    {
      noFill();       
    }
    ellipse(this.x, this.y, this.w, this.h);
  }
  public String getName(){
    return "circle";
  }
  void keyPresseded(char a, int b){}
  public boolean onOver(float x, float y)  // Verification si on est sur l'objet
  { 
    //les calcules nécessaire
   float dx = this.x - x;
   float dy = this.y - y;
   float dr = (this.x + this.x2) - x;
   float dt = this.y - y;
    if(sqrt(sq(dr) + sq(dt)) < 100/2 )
    {
      //verificatio nsi on est sur le cercle 1        
       this.pos1 = true;
       this.pos3 = false;
       return true;
    }
    if (sqrt(sq(dx) + sq(dy)) < this.w/2 )
    {     
      //verificatio nsi on est sur le cercle pour le déplacé
      this.pos3 = true;
      this.pos1 = false;
      return true;
    }
    else
    {
      this.pos1=false;
      this.pos3 = false;
      return false;
    }
  }
    public void setPosition(float x, float y) //deplacement
  {
    
    if(this.pos1){
      float oldX = this.x2;
      float oldX2 = this.x;
      float oldW = this.w;
      this.x2 = x - this.x;
      padSize(oldW,x,oldX2,oldX);
    }
     else{ 
      //modification pos du cercle x y
      this.x = x;
      this.y = y;    
      }
     
    
  }
  public void padSize(float oldX2, float x, float oldX, float oldw){
        float d = (this.x - x)*3;
        this.w = abs(oldw + d);
        this.h = abs(oldw + d);    
   }  

  
  public void setSize(float d) //distance
  {
  }
    public float getSize()
    {
       return this.w;
    }
  public float getTextX()
  {
    return x;
  }
  public float getTextY()
  {
    return y;
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
