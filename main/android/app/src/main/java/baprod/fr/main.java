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

public class main extends PApplet {

 /*Création apk : 
 1)  créer et mettre fichiers icon-36.png, icon-48.png, icon-72.png, icon-96.png, icon-144.png, icon-192.png dans répertoire du sketch
 2)  Editer AndroidManifest.xml : package="baprod.fr"> et android:label="Nom de l'appli">
 3)  Créer apk (Fichier/Export Signed Package) dans dossier "Build" du sketch
 Attention : bien cocher "INTERNET" dans le menu Android/Sketch permissions avant la compilation sur Android ou rajouter
 <uses-permission android:name="android.permission.INTERNET"/> à la fin du fichier AndroidManifest.xml
 
 Bien penser à changer IP (ligne 33 et 34) en fonction de l'IP du PC Camcapt
 */
 // à installer
 // déjà dans processing
float OSCmouseX, OSCmouseY,OSCmouseD,
OSCmouseXM,OSCmouseYM,
OSCmouseXS,OSCmouseYS,
OSCmouseXR, OSCmouseYR,
OSCd,OSCa;
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

public void setup()
{
  frameRate(60);
  
   myRemoteLocation = new NetAddress("192.168.0.13", 9006); // IP machine CamCapt port Processing local
   myRemoteLocations = new NetAddress("192.168.0.13", 9001); // IP machine CamCapt port Pd
   gesture = new KetaiGesture(this);
  
  /*
  Bouton , type de bouton et leur couleur
  */
  Button buttonCircle = new Button(25, 5, 125, 70);
  buttonCircle.setType("add", "circle");
  buttonCircle.setColor(0,0,0);
  buttons.add(buttonCircle);
  Button buttonLine = new Button(175, 5, 125, 70);
  buttonLine.setType("add", "line");
  buttonLine.setColor(0,0,0);
  buttons.add(buttonLine);
 /* Button buttonArrow = new Button(300, 5, 150, 70);
  buttonArrow.setType("add", "arrow");
  buttonArrow.setColor(0,0,0);
  buttons.add(buttonArrow);*/
  Button buttonAngle = new Button(350, 5, 125, 70);
  buttonAngle.setType("add", "angle");
  buttonAngle.setColor(0,0,0);
  buttons.add(buttonAngle);
  Button buttonSend = new Button(600, 5, 125, 70);
  buttonSend.setType("screen");
  buttonSend.setColor(0,0,0);
  buttons.add(buttonSend);
  Button buttonSupprimer = new Button(725, 5, 100, 70);
  buttonSupprimer.setType("delete");
  buttonSupprimer.setColor(0,0,0);
  buttons.add(buttonSupprimer);
  Button buttonRed = new Button(900, 5, 100, 70);
  buttonRed.setType("color",255,0,0);
  buttonRed.setColor(255,0,0);
  buttons.add(buttonRed);
  Button buttonGreen = new Button(1025, 5, 100, 70);
  buttonGreen.setType("color",0,128,0);
  buttonGreen.setColor(0,128,0);
  buttons.add(buttonGreen);
  Button buttonBlue = new Button(1150, 5, 100, 70);
  buttonBlue.setType("color",0,0,255);
  buttonBlue.setColor(0,0,255);
  buttons.add(buttonBlue);
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

public void mousePressed()//Si on clique sur un bouton alors créer la form en fonction du boutton cliqué
{
  OSCmouseX = mouseX;
  OSCmouseY = mouseY;
  OscMessage myMessage = new OscMessage("/mouse"); // envoie coordonnées souris
  myMessage.add(OSCmouseX); /* add position souris x to the osc message */
  myMessage.add(OSCmouseY); /* add position souris y to the osc message */
  
  OscP5.flush(myMessage, myRemoteLocation);

  ArrayList<Form> forms = formManager.getForms();
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
        Form targett = formManager.getTarget();
        if(targett != null && targett.onOver(mouseX, mouseY))
        {
            float  d= 5;
            targett.setSize(d);
        }
      }
    
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
        float  d= 5;
        OSCmouseXM = mouseX;
        OSCmouseYM = mouseY;
        OSCmouseD = d;
        OscMessage myMessage = new OscMessage("/move"); // envoie coordonnées souris
        myMessage.add(OSCmouseXM); /* add position souris x to the osc message */
        myMessage.add(OSCmouseYM); /* add position souris y to the osc message */
        myMessage.add(OSCmouseD); /* add  size d to the osc message */
        OscP5.flush(myMessage, myRemoteLocation);
        target.setPosition(mouseX, mouseY);        
        //target.setSize(d);
    }
  }
}
/*void onPinch (float x, float y, float d)//Si on clique avec deux doight sur une forme la déplace et l'agrandi ou la rétrécie en fonction de la position des doight
{
    try
    {
        OSCmouseXS = mouseX;
        OSCmouseYS = mouseY;
        OSCd = d;
        OscMessage myMessage = new OscMessage("/size"); // envoie coordonnées souris
        myMessage.add(OSCmouseXS); /* add position souris x to the osc message */
        //myMessage.add(OSCmouseYS); /* add position souris y to the osc message */
       // myMessage.add(OSCd); /* add  size d to the osc message */
        /*OscP5.flush(myMessage, myRemoteLocation);
    }
      catch(Exception e)
    { 
    msg_out="Error: " + e.getMessage();
    println(msg_out);
    }
  Form target = formManager.getTarget();
  println(target, x , y, d);
  if(target != null)
  {
    pinch = true;
    //println("pinch", d);
    if(d == 0)
    {
      target.setPosition(x, pinchRealY);
    } else
    {
      target.setPosition(x, y);
      pinchRealY = y;
    }
    //todo rename
    target.setSize2(d);
  }

    

}*/
/*
void onRotate  (float x, float y, float angle)//Si on clique avec deux doigts sur une forme la rotate en fonction de la position des doight
{
  Form target = formManager.getTarget();
  if(target != null)
  {
    target.setRotate(angle);
  }
}
}*/
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
    //text(int(res)+"°",this.w+5,this.h);
    //fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
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
     if(this.selected)
    {
      // je refait un if selected apres les deux ligne pour que le text se superpose (index plus haut)
      fill(255);
      noStroke();
      rect(this.w-30,this.h-18,60,30);
      fill(255,0,0);
      textSize(30);
      text(PApplet.parseInt(degrees(res)+""),this.w-28,this.h+9);
    }

  }
  
  public void setSize(float d) //taille
  {
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
  {}
  public void setRotate(float a) //rotation
  {
  }
}
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
    image(capture,600,5);
    PImage arc;
    arc = loadImage("Angle.png");
    image(arc,350,5);
    PImage poubelle;
    poubelle = loadImage("poubelle.png");
    image(poubelle,725,5);
    fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
    noStroke();
    rect(this.x, this.y, this.w, this.h);
    fill(255);
    rect(525,80,5,-80);
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
 //abstract boolean onOver2(float w, float h);//defini si on est sur la form ou non
   //abstract boolean onOver3(float x, float y);//defini si on est sur la form ou non
  public abstract void setPosition(float x, float y);//modifie position
  //abstract void setPosition2(float w, float h);//modifie position
  public abstract void setSize(float d);//modifie taille
  public abstract void setSize2(float d);//modifie taille
  //abstract float GetAngle();//rotation
  //abstract void setRotate(float a);//rotation
 // abstract float getRotate();//rotation
  
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
      /*case "arrow":
        this.forms.add(this.forms.size(), new Arrow(width/2, height/2,width/2+200,height/2));
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
    println("DELETE", this.target);
    this.forms.remove(this.target);
    this.target = null;
  }
  public void screen()
  {
  OscMessage myMessage = new OscMessage("/record/push_capture"); // envoie coordonnées souris
  myMessage.add("1"); /* add position souris x to the osc message */
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
    //fill(this.c.get("red"), this.c.get("green"), this.c.get("blue"));
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
       public void setSize2(float d) //distance
  {}
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
  public void settings() {  fullScreen(); }
}
