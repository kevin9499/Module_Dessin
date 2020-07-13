package baprod.fr;

import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import oscP5.*; 
import netP5.*; 
import android.view.MotionEvent; 
import ketai.ui.*; 
import java.util.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class main_1780_ extends PApplet {

 /*          Création apk : 
 
 1)  créer et mettre fichiers icon-36.png, icon-48.png, icon-72.png, icon-96.png, icon-144.png, icon-192.png dans répertoire du sketch
 2)  Editer AndroidManifest.xml : package="baprod.fr"> et android:label="Nom de l'appli">
 3)  Créer apk (Fichier/Export Signed Package) dans dossier "Build" du sketch
 Attention : bien cocher "INTERNET" dans le menu Android/Sketch permissions avant la compilation sur Android ou rajouter
 <uses-permission android:name="android.permission.INTERNET"/> à la fin du fichier AndroidManifest.xml

             Modification IP: 
 Bien penser à changer IP (ligne 33 et 34) en fonction de l'IP du PC Camcapt
 
             Reponsivité :
  1) Modifier les valeurs de tout les buttons dans le main(setup) en fonction des besoins
  2) Modifier les valeurs  des différents objets,images,formes dans l'onglet Button(draw) en fonction des besoins
  */
//PGraphics pg;
 // à installer
 // déjà dans processing
float OSCmouseX, OSCmouseY,OSCmouseD,
OSCmouseXM,OSCmouseYM,
OSCmouseXS,OSCmouseYS,
OSCmouseXR, OSCmouseYR,
OSCd,OSCa,OSCSize,OSCclear;
OscP5 oscP5;
String msg_out;
NetAddress myRemoteLocation,myRemoteLocations;
 //lib motionevent
 // lib ketai
 // lib java
KetaiGesture gesture;
ArrayList<Button> buttons = new ArrayList<Button>(); // bouton
FormManager formManager = new FormManager(); //Appel du formanager
boolean pinch = false; //delcaration de pinch (2 doight) en false
float pinchRealY = 0; // initialisé à 0
int DISPLAY_DURATION = 2000;
int startTime = 0;
boolean bDisplayMessage = false;
boolean keyboard = false;
String OSCText;

public void setup() {
  frameRate(30);
  myRemoteLocation = new NetAddress("192.168.1.11", 9006); // IP machine CamCapt port Processing local
  myRemoteLocations = new NetAddress("192.168.1.11", 9001); // IP machine CamCapt port Pd
  gesture = new KetaiGesture(this);
  
  if(width == 1780){
  Button buttonCircle = new Button(25, 5, 125, 70);
  buttonCircle.setType("add", "circle");
  buttonCircle.setColor(0,0,0);
  buttons.add(buttonCircle);
  Button buttonLine = new Button(145, 5, 125, 70);
  buttonLine.setType("add", "line");
  buttonLine.setColor(0, 0, 0);
  buttons.add(buttonLine);
  Button buttonArrow = new Button(420, 5, 100, 70);
  buttonArrow.setType("add", "text");
  buttonArrow.setColor(0, 0, 0);
  buttons.add(buttonArrow);
  Button buttonAngle = new Button(300, 5, 100, 70);
  buttonAngle.setType("add", "angle");
  buttonAngle.setColor(0, 0, 0);
  buttons.add(buttonAngle);
  Button buttonSend = new Button(540, 5, 125, 70);
  buttonSend.setType("screen");
  buttonSend.setColor(0, 0, 0);
  buttons.add(buttonSend);
  Button buttonSupprimer = new Button(720, 5, 100, 70);
  buttonSupprimer.setType("delete");
  buttonSupprimer.setColor(0, 0, 0);
  buttons.add(buttonSupprimer);
  Button buttonClear = new Button(850, 5, 125, 70);
  buttonClear.setType("clear");
  buttonClear.setColor(0, 0, 0);
  buttons.add(buttonClear);
  Button buttonRed = new Button(1050, 5, 150, 70);
  buttonRed.setType("color", 255, 0, 0);
  buttonRed.setColor(255, 0, 0);
  buttons.add(buttonRed);
  Button buttonGreen = new Button(1300, 5, 150, 70);
  buttonGreen.setType("color", 0, 128, 0);
  buttonGreen.setColor(0, 128, 0);
  buttons.add(buttonGreen);
  Button buttonBlue = new Button(1550, 5, 150, 70);
  buttonBlue.setType("color", 0, 0, 255);
  buttonBlue.setColor(0, 0, 255);
  buttons.add(buttonBlue);
  }
  else if(width == 1139){
  Button buttonCircle = new Button(0, 5, 100, 70);
  buttonCircle.setType("add", "circle");
  buttonCircle.setColor(0,0,0);
  buttons.add(buttonCircle);
  Button buttonLine = new Button(110, 5, 100, 70);
  buttonLine.setType("add", "line");
  buttonLine.setColor(0,0,0);
  buttons.add(buttonLine);
  Button buttonArrow = new Button(330, 5, 100, 70);
  buttonArrow.setType("add", "text");
  buttonArrow.setColor(0,0,0);
  buttons.add(buttonArrow);
  Button buttonAngle = new Button(230, 5, 100, 70);
  buttonAngle.setType("add", "angle");
  buttonAngle.setColor(0,0,0);
  buttons.add(buttonAngle);
  Button buttonSend = new Button(460, 5, 100, 70);
  buttonSend.setType("screen");
  buttonSend.setColor(0,0,0);
  buttons.add(buttonSend);
  Button buttonSupprimer = new Button(570, 5, 80, 70);
  buttonSupprimer.setType("delete");
  buttonSupprimer.setColor(0,0,0);
  buttons.add(buttonSupprimer);
  Button buttonClear = new Button(660, 5, 110, 70);
  buttonClear.setType("clear");
  buttonClear.setColor(0,0,0);
  buttons.add(buttonClear);
  Button buttonRed = new Button(795, 5, 100, 70);
  buttonRed.setType("color",255,0,0);
  buttonRed.setColor(255,0,0);
  buttons.add(buttonRed);
  Button buttonGreen = new Button(909, 5, 100, 70);
  buttonGreen.setType("color",0,128,0);
  buttonGreen.setColor(0,128,0);
  buttons.add(buttonGreen);
  Button buttonBlue = new Button(1022, 5, 100, 70);
  buttonBlue.setType("color",0,0,255);
  buttonBlue.setColor(0,0,255);
  buttons.add(buttonBlue);
  }
}

public boolean surfaceTouchEvent(MotionEvent event)//methode qui permet de savoir si on touche l'ecran
{
  //permet de savoir le nombre de doigt etc comment on touche avec le tactle
  super.surfaceTouchEvent(event);
  return gesture.surfaceTouchEvent(event);
}

public void draw()
{
  background(0);
  ArrayList<Form> forms = formManager.getForms();
  for(Form f : forms) //Dessine une form en fonction de la form demandé via le bouton
  {
    f.draw();
  }
  for(Button b : buttons)  //Dessine une bouton en fonction des bouttons déclaré
  {
    b.draw();
  }
    if (bDisplayMessage)
  {
    Form target = formManager.getTarget();
    if(target != null)
    {
      formManager.target.endSelect();
      fill(255);
      textSize(60);
      text("CAPTURE D'ECRAN REUSSIE",width/2-405,200);
      
      if (millis() - startTime > DISPLAY_DURATION)
      {
        bDisplayMessage = false;
      }
    }
    else
    {
      fill(255);
      textSize(60);
      text("CAPTURE D'ECRAN REUSSIE",width/2-405,200);
      
      if (millis() - startTime > DISPLAY_DURATION)
      {
        bDisplayMessage = false;
      }
    }
  }
}

public void keyPressed(){
  ArrayList<Form> forms = formManager.getForms();
  Form targett = formManager.getTarget();
    if(keyCode == BACKSPACE){
      targett.keyPresseded(key,keyCode);
    }  
    else if(keyCode == DELETE){
      targett.keyPresseded(key,keyCode);
    }
    else if(keyCode != SHIFT && key != ' '){
      targett.keyPresseded(key,keyCode);
    }
    else if(key == ' ' ){
      targett.keyPresseded(key,keyCode);
    }
    OSCText = formManager.target.getName() ;
    OscMessage myMessage = new OscMessage("/keyboard");
    myMessage.add(OSCText); /* envoie données keyboard */ 
    //println(formManager.target.getName());
    OscP5.flush(myMessage, myRemoteLocation);
}

public void mousePressed()//Si on clique sur un bouton alors créer la form en fonction du boutton cliqué
{  
  ArrayList<Form> forms = formManager.getForms();
  Form targettt = formManager.getTarget();
  OSCmouseX = mouseX;
  OSCmouseY = mouseY;
  OscMessage myMessage = new OscMessage("/mouse"); // envoie coordonnées souris
  myMessage.add(OSCmouseX); /* add position souris x to the osc message */
  myMessage.add(OSCmouseY); /* add position souris y to the osc message */
  Form target = null;
  boolean buttonClick = false;
    for(Button b : buttons)
    {      
      buttonClick = b.onClick(mouseX, mouseY);
      if(buttonClick)
      {
        if(b.getType() == "add")
        {
          for(int i = forms.size() - 1; i >= 0; i--)
          {
            Form f = forms.get(i);
            if(f.onOver(mouseX, mouseY) ||buttonClick)
             {
               target = f;
               formManager.setTarget(target);
               break;
             }
             else
             {
               formManager.target.endSelect();
             }
          }
        }
        if(b.getType() == "screen")
        {
          bDisplayMessage = true;
          startTime = millis();
        } 
     }
    } 
     Form targett = formManager.getTarget();
     if(targett != null && targett.onOver(mouseX, mouseY))
      {
        float d= 10;
        targett.setSize(d);
        OSCSize = targett.getSize();
        myMessage.add(OSCSize); /* add  size d to the osc message */
      }
      OscP5.flush(myMessage, myRemoteLocation);
      for(int i = forms.size() - 1; i >= 0; i--)
      {
         Form f = forms.get(i);
         if(f.onOver(mouseX, mouseY))
         {
           target = f;
           formManager.setTarget(target);
           break;
         }
      }
}

public void mouseDragged()//Si on clique sur une form alors la déplace en fonction de la position du doigt
{
  boolean pinch = false; //delcaration de pinch (2 doight) en false        
  if(!pinch)
  {
    Form target = formManager.getTarget();
    if(target != null && target.onOver(mouseX, mouseY))
    {
        OSCmouseXM = mouseX;
        OSCmouseYM = mouseY;
        OscMessage myMessage = new OscMessage("/move"); // envoie coordonnées souris
        myMessage.add(OSCmouseXM); /* add position souris x to the osc message */
        myMessage.add(OSCmouseYM); /* add position souris y to the osc message */
        target.setPosition(mouseX, mouseY);        
        OscP5.flush(myMessage, myRemoteLocation);
    }
  }
}
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
      text(PApplet.parseInt(degrees(res)+""),this.w-28,this.h+9);
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
  public void keyPresseded(char a, int b)
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
      float x = width/2-105;
      float y = 108;
      noStroke();
      textSize(30);
      fill(220,220,220);
      rect(x,y,70,50);
      fill(0);
      text("OUI",width/2-100,145);
      fill(220,220,220);
      rect(x+105,y,70,50);
      fill(0);
      text("NON",width/2,145);
      if(mousePressed)
      {
        if(mouseX > width/2-105 && mouseX < (width/2-105 + 70) && mouseY > 108 && mouseY < (108 + 50))
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
        else if(mouseX > width/2 && mouseX < (width/2 + 70) && mouseY > 108 && mouseY < (108 + 50))
        {
          yes = true;
          messageClear = false;
        }
      }
    }
//c'est la ou je fait les ligne du pannel et le design des boutons
    if(width == 1780){
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
    else if(width == 1139){
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
    noFill();
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
  public String getName(){
    return "circle";
  }
  public void keyPresseded(char a, int b){}
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
       return true;
    }
     else if(sqrt(sq(dr) + sq(dt2)) < 200/2 )
    {      
      //verificatio nsi on est sur le cercle 2
      this.pos1 = false;
      this.pos2 = true;
      this.pos3 = false;
      return true;
    }
    if (sqrt(sq(dx) + sq(dy)) < this.w/2 )
    {     
      //verificatio nsi on est sur le cercle pour le déplacé
      this.pos3 = true;
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
abstract class Form
{
  protected float x, y;
  protected float w, h;
  protected boolean selected;
  protected HashMap<String, Integer> c = new HashMap<String, Integer>();
  protected HashMap<String, Integer> cs = new HashMap<String, Integer>();
  
  public Form(float x, float y, float w, float h)
  {   
    this.x = x;
    this.y = y;
    this.w = w;
    this.h = h;       
    this.c.put("red", 0);
    this.c.put("green", 0);
    this.c.put("blue", 0);
    this.cs.put("red", 200);
    this.cs.put("green", 200);
    this.cs.put("blue", 200);
  }
  
  public abstract void draw();
  public abstract boolean onOver(float x, float y);//defini si on est sur la form ou non
  public abstract void setPosition(float x, float y);//modifie position
  public abstract void setSize(float d);//modifie taille
  public abstract void setSize2(float d);//modifie taille
  public abstract float getSize();
  public abstract void keyPresseded(char a, int b);
  public abstract String getName();
  //abstract float GetAngle();//rotation
  //abstract void setRotate(float a);//rotation
  //abstract float getRotate();//rotation
  
  public void onSelect()
  {
    this.selected = true;
  }  
  public void endSelect()
  {
    this.selected = false;
  }  
  public void setColor(int r, int g, int b)
  {
    this.c.put("red",r);
    this.c.put("green",g);
    this.c.put("blue",b);
  }
    public void setColorStroke(int r, int g, int b)
  {
    this.cs.put("red",r);
    this.cs.put("green",g);
    this.cs.put("blue",b);    
  }
    public float getWidth()
  {
    return this.w;
  }  
  public Form setWidth(float w)
  {
    this.w = w;
    return this;
  }
  public float getHeight()
  {
    return this.h;
  }  
  public Form setHeight(float h)
  {
    this.h = h;
    return this;
  } 
  public Form setWidthHeight(float w, float h)
  {
    this.w = w;
    this.h = h;
    return this;
  }
}
class FormManager
{
  private ArrayList<Form> forms;
  private Form target;
  
  public FormManager()
  { 
    this.forms = new ArrayList<Form>();
  }
  
  public void createForm(String type) 
 {
    switch(type)
    {
      case "circle":
        this.forms.add(this.forms.size(), new Circle(width/2, height/2, 300));
        break;
      case "line":
        this.forms.add(this.forms.size(), new Line(width/2, height/2, width/2+200,height/2));
        break;
      case "angle":
        this.forms.add(this.forms.size(), new Angle(width/2, height/2,width/2+200,height/2));
        break;
      case "text":
        this.forms.add(this.forms.size(), new Text(width/2, height/2,width/2+200,height/2));
        break;
      /*case "arrow":
        this.forms.add(this.forms.size(), new Arrow(width/2, height/2,0,0));
        break;*/
    }
  }
  
  public Form getTarget()//cible form
  {
    return this.target;
  }
  
  public void setTarget(Form target)
  {
    if(this.target != null && target != this.target )
    {
      this.target.endSelect();
    }
    this.target = target;
    this.target.onSelect();
  }
  
  public void deleteTarget()//delete form
  {

    /*if(this.target.getName() == "arrow"){
    pg.beginDraw();
    pg.clear();
    pg.endDraw();
    image(pg, 0, 0); 
    this.forms.remove(this.target);
    this.target = null;
    }else{*/
    println("DELETE", this.target);
    this.forms.remove(this.target);
    this.target = null;
    //}
  }
  
  public void deleteAll()
  {
     this.forms.clear();
  }
  public void screen()
  {
  OscMessage myMessage = new OscMessage("/record/push_capture"); // envoie coordonnées souris
  myMessage.add("capture"); /* add position souris x to the osc message */
  OscP5.flush(myMessage, myRemoteLocations);
  }
  public Form getForm(int i)
  {
    return this.forms.get(i);
  }
  
  public ArrayList<Form> getForms()//récupere tout le tab via un boutton dans l'arraylist
  {
    return this.forms;
  }
}
class Line extends Form
{
  
  float rotate = 0;
  boolean cercleOn,pos1,pos2;

  public Line(float x, float y, float w, float h) //constructeur
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
      fill(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
      ellipse(x, y,100,100);//cerlcle qui utilise la position de x et y
      ellipse(w, h,100,100);//cerlcle qui utilise la position de w et h
      strokeWeight(8);
      stroke(this.cs.get("red"), this.cs.get("green"), this.cs.get("blue"));
    }
    line(this.x,this.y, this.w, this.h);
  }
  public void setRotate(float rotate) //rotation
  {
  }
    public String getName(){
    return "line";
  }
    public float getTextX()
  {
    return x;
  }
  public float getTextY()
  {
    return y;
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
  public void keyPresseded(char a, int b)
  {
  }
  public float getRotate()
  {
    return rotate;
  }
    public float getSize()
    {
       return this.w;
    }
  public void setSize(float d) //distance
  {
  }
  public void setSize2(float d) //distance
  {
  }
  public boolean onOver(float x, float y) // Verification si on est sur l'objet
  {
     //les calcules nécessaire
   float dx = this.x - x;
   float dy = this.y - y;
   float dx2 = this.w - x;
   float dy2 = this.h - y;
   
    if(sqrt(sq(dx) + sq(dy)) < 100 )
    {
      //verificatio nsi on est sur le cercle 1
      this.pos1 = true;
      this.pos2=false;
    }
    else if(sqrt(sq(dx2) + sq(dy2)) < 100 )
    {
      //verificatio nsi on est sur le cercle 2
       this.pos2 = true;
       this.pos1=false;
    }
    if(sqrt(sq(dx) + sq(dy)) < 100 ||sqrt(sq(dx2) + sq(dy2)) < 100)
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
}
class Text extends Form
{
  float rotate = 0;
  boolean cercleOn,pos1,pos2;
  boolean vrai = false;
  String inputText = "Text";

  public Text(float x, float y, float w, float h) //constructeur
  {
    super(x,y,w,h);
    textFont(createFont("SansSerif", 35 * displayDensity));
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
  public void keyPresseded(char a, int b)
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
  public void settings() {  size(1780,1080); }
}
