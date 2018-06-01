package salatsoft.rpn;

import java.util.ArrayList;

public class Stack
{
  private ArrayList stack = new ArrayList();
  
  public void clear()
  {
    this.stack.clear();
  }
  
  public String get(int paramInt)
  {
    return (String)this.stack.get(paramInt);
  }
  
  public int getActualSize()
  {
    return this.stack.size();
  }
  
  public boolean isEmpty()
  {
    return this.stack.size() == 0;
  }
  
  public String look()
  {
    if (this.stack.size() > 0) {
      return (String)this.stack.get(this.stack.size() - 1);
    }
    return "EMPTY";
  }
  
  public String pop()
  {
    if (this.stack.size() != 0) {
      return (String)this.stack.remove(this.stack.size() - 1);
    }
    return "EMPTY";
  }
  
  public void push(String paramString)
  {
    this.stack.add(paramString);
  }
  
  public void remove(int paramInt)
  {
    this.stack.remove(paramInt);
  }
}


/* Location:              C:\Users\as67591\Downloads\dex2jar-2.0\classes-dex2jar.jar!\salatsoft\rpn\Stack.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */