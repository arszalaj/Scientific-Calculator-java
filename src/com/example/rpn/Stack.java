package com.example.rpn;
import java.util.ArrayList;

public class Stack {
    
    public void push(String element)
    {
        stack.add(element);
    }
    
    public String pop()
    {
        if (stack.size()!=0)
        {
            String tmp=(String)stack.remove(stack.size()-1);
            return tmp; 
        }
        else
            return "EMPTY";
    }

    public int getActualSize()
    {
        return stack.size();
    }
    
    public boolean isEmpty()
    {
        if (stack.size()==0)
            return true;
        else
            return false;
    }
    
    public String look()
    {
        if (stack.size()>0)
            return (String)stack.get(stack.size()-1);
        else
            return "EMPTY";
    }
    
    public String get(int n)
    {
    	return (String) stack.get(n);
    }
    
    public void clear()
    {
    	stack.clear();
    }
    
    public void remove(int n)
    {
    	stack.remove(n);
    }
    private ArrayList stack = new ArrayList();            
}
