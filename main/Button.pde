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
    //c'est la ou je fait les ligne du pannel et le design des boutons
    PImage capture;
    capture = loadImage("Capture.png");
    image(capture,525,5);
    PImage arc;
    arc = loadImage("Angle.png");
    image(arc,350,5);
    PImage poubelle;
    poubelle = loadImage("poubelle.png");
    image(poubelle,650,5);
    
    fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
    noStroke();
    rect(this.x, this.y, this.w, this.h);
    fill(255);
    textSize(30);
    text("CLEAR",740,50);
    fill(255);
    rect(475,80,5,-80);
    fill(255);
    rect(850,80,5,-80);
    fill(255);
    rect(0,80,2500,5);
    stroke(255);
    noFill();
    ellipse(100,40,50,50);
    fill(255);
    rect(200,35,80,5);
    //fill(255);
    //rect(350,35,80,5);
    //fill(255);
    //rect(480,50,50,5);
    //fill(255);
    //rect(530,55,5,-50);
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
          formManager.deleteTarget();
          break;
        case "clear":
          formManager.deleteAll();
          break;
        case "screen":
          formManager.screen();
          break;
        case "color":
          formManager.getTarget().setColorStroke(this.r,this.g,this.b);
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
