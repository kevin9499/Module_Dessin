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
  
  abstract void draw();
  abstract boolean onOver(float x, float y);//defini si on est sur la form ou non
  abstract void setPosition(float x, float y);//modifie position
  abstract void setSize(float d);//modifie taille
  abstract void setSize2(float d);//modifie taille
  abstract float getSize();
  abstract void keyPresseded(char a, int b);
  abstract String getName();
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
