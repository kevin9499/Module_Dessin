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
