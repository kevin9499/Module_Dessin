class Text extends Form
{
  float rotate = 0;
  boolean cercleOn,pos1,pos2;
  boolean vrai = false;
  String inputText = "Text";

  public Text(float x, float y, float w, float h) //constructeur
  {
    super(x,y,w,h);
    textFont(createFont("SansSerif", 35));
  }
  
  public void draw()
  {
    fill(125);
    fill(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    strokeWeight(5);
    stroke(255);
    stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    textSize(30);
    text(inputText , x+2,y);
    if(this.selected){  
      noFill();
      PImage edit;
      edit = loadImage("btEditTxt-alt.png");
      image(edit,x-100,y-55);
      rect(x,y-55,350,80);
      //rect(x+400,y-50,100,80);      
    }    
  }
  public void setRotate(float rotate) //rotation
  {
  }
   public void setPosition(float x, float y) //deplacement
  {
    if(this.pos1)
    {
      //modification pos du cercle x y
      this.x = x-150;
      this.y = y;
    }
  }
  public String getName()
  {
    return inputText;
  }
  public float getTextX()
  {
    return this.x;
  }
  public float getTextY()
  {
    return this.y;
  }
  public float getRotate()
  {
    return rotate;
  }
  public void setSize2(float d) //distance
  {
  }
  public float getSize()
  {
    return this.w;
  }
  public void setSize(float d) //distance
  {
  }
  void keyPresseded(char a, int b)
  {
    if(b == BACKSPACE)
    {
      if(inputText.length() > 0)
      {
        inputText = inputText.substring(0,inputText.length()-1);
       }
    }
    else if(b == DELETE)
    {
      inputText = "";
    }
    else if(b != SHIFT && a != ' ')
    {
      inputText = inputText + a;
    }
    else if(a == ' ' )
    {      
      inputText = inputText + ' ';
    }  
 }
  public boolean onOver(float x, float y) // Verification si on est sur l'objet
  {
   float dx = this.x+150 - x;
   float dy = this.y - y;
   if(this.selected)
   {
      if(mouseX > this.x-100 && mouseX < (this.x-100 + 100) && mouseY > this.y-55 && mouseY < (this.y-55 +80))
      {
         if (!keyboard)
         {
            openKeyboard();
            keyboard = true;            
         }
      }
      else
      {
        this.pos1 = false; 
        closeKeyboard();
        keyboard = false;
      }          
   }
   if(sqrt(sq(dx) + sq(dy)) < 180 )
   {
      this.pos1 = true;
      return true;
   }
    else
    {
     this.pos1 = false;
     return false;
    }         
  }
}
