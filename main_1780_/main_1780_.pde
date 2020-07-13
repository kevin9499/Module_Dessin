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
import oscP5.*; // à installer
import netP5.*; // déjà dans processing
float OSCmouseX, OSCmouseY,OSCmouseD,
OSCmouseXM,OSCmouseYM,OSCmouseS,
OSCmouseXS,OSCmouseYS,
OSCmouseXR, OSCmouseYR,
OSCd,OSCa,OSCSize,OSCclear;
OscP5 oscP5;
String msg_out;
NetAddress myRemoteLocation,myRemoteLocations;
import android.view.MotionEvent; //lib motionevent
import ketai.ui.*; // lib ketai
import java.util.*; // lib java
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


void setup() {
  /*Changer le premier parametre de size suivant la résolution de la tablette
    Exemple :   une tablette 1920,   1920 - 140 = 1780     on fait -140 quelque soit
    la résolution pour avoir une résolution qui va pour Vmix.
  */
  size(1780, 1080);
  frameRate(30);
  myRemoteLocation = new NetAddress("192.168.1.110", 9006); // IP machine CamCapt port Processing local
  myRemoteLocations = new NetAddress("192.168.1.110", 9001); // IP machine CamCapt port Pd
  gesture = new KetaiGesture(this);

  if(width >= 1780){
  Button buttonCircle = new Button(25, 5, 125, 70);
  buttonCircle.setType("add", "circle");
  buttonCircle.setColor(0,0,0);
  buttons.add(buttonCircle);
  Button buttonLine = new Button(145, 5, 125, 70);
  buttonLine.setType("add", "line");
  buttonLine.setColor(0, 0, 0);
  buttons.add(buttonLine);
  Button buttonText = new Button(420, 5, 100, 70);
  buttonText.setType("add", "text");
  buttonText.setColor(0, 0, 0);
  buttons.add(buttonText);
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
  else if(width <= 1280){
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

boolean surfaceTouchEvent(MotionEvent event)//methode qui permet de savoir si on touche l'ecran
{
  //permet de savoir le nombre de doigt etc comment on touche avec le tactle
  super.surfaceTouchEvent(event);
  return gesture.surfaceTouchEvent(event);
}

void draw()
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

void keyPressed(){
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

void mousePressed()//Si on clique sur un bouton alors créer la form en fonction du boutton cliqué
{  
  ArrayList<Form> forms = formManager.getForms();
  Form targettt = formManager.getTarget();
  OSCmouseX = mouseX;
  OSCmouseY = mouseY;
  OscMessage myMessage = new OscMessage("/mouse"); // envoie coordonnées souris
  myMessage.add(OSCmouseX); /* add position souris x to the osc message */
  myMessage.add(OSCmouseY);
  /* add position souris y to the osc message */
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
        else if(mouseY > 80){
          try{
            formManager.target.endSelect();
          }
          catch(NullPointerException  e){
            println(e);
          }
      }
  }
}

void mouseDragged()//Si on clique sur une form alors la déplace en fonction de la position du doigt
{
  boolean pinch = false; //delcaration de pinch (2 doight) en false        
  if(!pinch)
  {
    Form target = formManager.getTarget();
    if(target != null && target.onOver(mouseX, mouseY))
    {
        OSCmouseXM = mouseX;
        OSCmouseYM = mouseY;
        OSCmouseS = target.getSize();
        OscMessage myMessage = new OscMessage("/move"); // envoie coordonnées souris
        myMessage.add(OSCmouseXM); /* add position souris x to the osc message */
        myMessage.add(OSCmouseYM); /* add position souris y to the osc message */
        myMessage.add(OSCmouseS); /* add position souris y to the osc message */
        target.setPosition(mouseX, mouseY);        
        OscP5.flush(myMessage, myRemoteLocation);
    }
  }
}
