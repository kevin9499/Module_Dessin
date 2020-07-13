class Button
{
  private float w, h;
  private float x, y;
  private int r, g, b;
  private HashMap<String, Integer> c = new HashMap<String, Integer>();
  private HashMap<String, Integer> cText = new HashMap<String, Integer>(); 
  private String text;
  private String type;
  private String typeForm;
  private boolean yes = false;
  private boolean messageClear = false;
  
  public Button(float x, float y, float w, float h) //Constructeur
  {
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;
    this.c.put("red", 255);
    this.c.put("green", 0);
    this.c.put("blue", 0);
    this.cText.put("red", 0);
    this.cText.put("green", 0);
    this.cText.put("blue", 0);
    this.text = "";
  }
  
  public Button setText(String text, int textSize, int r, int g, int b) //text
  {
    this.text = text;
    this.cText.put("red", r);
    this.cText.put("green", g);
    this.cText.put("blue", b);
    return this;
  }
  
  public Button setType(String type, String typeForm)
  {
    this.type = type;
    this.typeForm = typeForm;
    return this;
  }
    public String getType()
  {
     return this.type;    
  }
    public Button setType(String type, int r, int g, int b)
  {
    this.type = type;
    this.r = r;
    this.g = g;
    this.b = b;
    return this;
  }
  
  public Button setType(String type)
  {
    this.type = type;
    return this;
  }
  /*public Button setText(String text, int textSize)
  {
    this.text = text;
    this.textSize = textSize;
    return this;
  }*/  
  public Button setPosition(float x, float y)
  {
    this.x = x;
    this.y = y;
    return this;
  }  
  public Button setColor(int r, int g, int b)
  {
    this.c.clear();
    this.c.put("red", r);
    this.c.put("green", g);
    this.c.put("blue", b);
    return this;
  } 
  public void draw()
  {
        if(messageClear)
    {
      fill(255);
      text("Effacer toutes les formes",width/2-170,135);
      float x = width/2-105;
      float y = 158;
      noStroke();
      textSize(30);
      fill(220,220,220);
      rect(x,y,70,50);
      fill(0);
      text("OUI",width/2-100,195);
      fill(220,220,220);
      rect(x+105,y,70,50);
      fill(0);
      text("NON",width/2,195);
      if(mousePressed)
      {
        if(mouseX > width/2-105 && mouseX < (width/2-105 + 70) && mouseY > 108 && mouseY < (158 + 50))
        {
          OSCclear = 1;
          OscMessage myMessage = new OscMessage("/clear"); // envoie coordonnées souris
          myMessage.add(OSCclear); /* add position souris x to the osc message */
          yes = true;
         /* pg.beginDraw();
          pg.clear();
          pg.endDraw();
          image(pg, 0, 0); */          
          formManager.deleteAll();
          OscP5.flush(myMessage, myRemoteLocation);
          messageClear = false;
        }
        else if(mouseX > width/2 && mouseX < (width/2 + 70) && mouseY > 108 && mouseY < (158 + 50))
        {
          yes = true;
          messageClear = false;
        }
      }
    }
//c'est la ou je fait les ligne du pannel et le design des boutons
    if(width >= 1780  ){
     PImage clear;
    clear = loadImage("btClear.png");
    image(clear,840,10);
    PImage text;
    text = loadImage("btTexte.png");
    image(text,430,5);
    PImage capture;
    capture = loadImage("Capture.png");
    image(capture,575,5);
    PImage arc;
    arc = loadImage("Angle.png");
    image(arc,320,5);
    PImage poubelle;
    poubelle = loadImage("poubelle.png");
    image(poubelle,730,5);
    fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
    noStroke();
    rect(this.x, this.y, this.w, this.h);
    fill(255);
    rect(525,80,5,-80);
    fill(255);
    rect(950,80,5,-80);
    fill(255);
    rect(0,80,2500,5);
    stroke(255);
    noFill();
    ellipse(70,40,50,50);
    fill(255);
    rect(170,35,80,5);
    }
    else if(width <= 1280){
      PImage clear;
    clear = loadImage("btClear.png");
    image(clear,670,10);
    PImage text;
    text = loadImage("btTexte.png");
    image(text,350,5);
    PImage capture;
    capture = loadImage("Capture.png");
    image(capture,470,5);
    PImage arc;
    arc = loadImage("Angle.png");
    image(arc,250,5);
    PImage poubelle;
    poubelle = loadImage("poubelle.png");
    image(poubelle,580,5);
    fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
    noStroke();
    rect(this.x, this.y, this.w, this.h);
    fill(255);
    rect(445,80,5,-80);
    fill(255);
    rect(775,80,5,-80);
    fill(255);
    rect(0,80,2500,5);
    stroke(255);
    noFill();
    ellipse(50,40,50,50);
    fill(255);
    rect(120,35,80,5);
    }
    strokeWeight(7);
    stroke(255,0,0);
    //textSize(this.textSize);
    fill(this.cText.get("red"), this.cText.get("green"), this.cText.get("blue"));
    text(this.text, this.x, this.y+(this.h/2));
  } 
  public boolean onClick(float x, float y)  // si on clique sur l'objet
  {
    if(x > this.x && x < (this.x + this.w) && y > this.y && y < (this.y + this.h))
    {
      println("clicke =0d");
      switch(this.type)
      {
        case "add":
          formManager.createForm(this.typeForm);
          break;
        case "delete":
        if(formManager.getTarget() != null){
          formManager.deleteTarget();
        }
          break;
        case "clear":
        this.messageClear = true;
        if(yes)
        {       
          break;
        }
        case "screen":
          formManager.screen();
          break;
        case "color":
        if(formManager.getTarget() != null){          
          formManager.getTarget().setColorStroke(this.r,this.g,this.b);
        }
          break;
      }
      return true;
    }
    else
    {
      return false;
    }
  }
}
