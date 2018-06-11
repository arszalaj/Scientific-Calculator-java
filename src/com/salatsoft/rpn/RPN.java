 /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salatsoft.rpn;
import java.lang.StringBuffer;
import java.lang.Math;


/**
 *
 * @author Aro
 */
public class RPN {
	
    private int pos;
    private String buffer;
    private int assoc;
    public String rpn;
    public double result;
    public double resultFunction;
    
    private final int ERROR=-1;
    private final int NUMBER=0;
    private final int FUNCTION=1;
    private final int OPERATOR=2;
    private final int LB=3;
    private final int RB=4;
    private final int EOS=5;
    
    private final int RIGHT_ASSOC=1;
    private final int LEFT_ASSOC=0;
    
	public String optimalizedInfix(String infix)
	{
		//String newInfix;
		int i,n,lb=0,rb=0;
		
		if(infix.charAt(0)=='-' || infix.charAt(0)=='+')
		{
			infix='0'+infix;
		}
		
		for(i=0;i<infix.length();i++)
		{
			if(infix.charAt(i)=='(')
				lb++;
			if(infix.charAt(i)==')')
				rb++;
		}
		
		while(rb<lb)
		{
			infix+=")";
			rb++;
		}
		
		boolean repeat;
                
                for(i=0;i<infix.length()-1;i++)
                {
                if((infix.charAt(i)>='0' && infix.charAt(i)<='9') && (infix.charAt(i+1)=='π' || infix.charAt(i+1)=='e') ||
              ((infix.charAt(i+1)>='0' && infix.charAt(i+1)<='9') && (infix.charAt(i)=='π' || infix.charAt(i)=='e')) ||
              ((infix.charAt(i+1)=='π' && infix.charAt(i)=='e') || (infix.charAt(i)=='π' && infix.charAt(i+1)=='e')) ||
              ((infix.charAt(i)=='π' || infix.charAt(i)=='e') && (infix.charAt(i+1)>='a' && infix.charAt(i+1)<='z')))
            		infix=infix.substring(0,i+1)+'*'+infix.substring(i+1,infix.length());
                }
		do
		{
		repeat=false;
		for(i=0;i<infix.length();i++)
		{
			
			if(infix.charAt(i)=='e')
			{
				infix=infix.substring(0,i)+"(2.71828183)"+infix.substring(i+1,infix.length());
				i+=8;
				repeat=true;
			}
			
			if(infix.charAt(i)=='π')
			{
				infix=infix.substring(0,i)+"(3.14159265)"+infix.substring(i+1,infix.length());
				i+=8;
				repeat=true;
			}
		}
		
		}
		while(repeat==true);
		
		for(i=0;i<infix.length()-1;i++)
		{
			if(infix.charAt(i)=='(' && infix.charAt(i+1)=='-')
				infix=infix.substring(0,i+1)+'0'+infix.substring(i+1,infix.length());
			
			if((infix.charAt(i)>='0' && infix.charAt(i)<='9') && infix.charAt(i+1)=='(')
				infix=infix.substring(0,i+1)+'*'+infix.substring(i+1,infix.length());
			if((infix.charAt(i+1)>='0' && infix.charAt(i+1)<='9') && infix.charAt(i)==')')
				infix=infix.substring(0,i+1)+'*'+infix.substring(i+1,infix.length());
			
			if((infix.charAt(i)>='0' && infix.charAt(i)<='9') && (infix.charAt(i+1)>='a' && infix.charAt(i+1)<='z'))
				infix=infix.substring(0,i+1)+'*'+infix.substring(i+1,infix.length());
			
			if(infix.charAt(i)==')' && infix.charAt(i+1)=='(')
				infix=infix.substring(0,i+1)+'*'+infix.substring(i+1,infix.length());
			
			if(infix.charAt(i)=='%' && infix.charAt(i+1)=='*')
				infix=infix.substring(0,i+1)+infix.substring(i+2,infix.length());
			
			if(infix.charAt(i)=='%' && infix.charAt(i+1)==')')
            {
			int j=2;

            while(infix.charAt(i-j)!='+' && infix.charAt(i-j)!='-' && infix.charAt(i-j)!='*' && infix.charAt(i-j)!='/')
                j++;

            infix=infix.substring(0,i-j+1)+'%'+infix.substring(i-j+1,i)+infix.substring(i+1,infix.length());
            }
			
            if(infix.charAt(i)=='%' && (infix.charAt(i+1)=='+' || infix.charAt(i+1)=='-' ))		
            {
				int j=2;
				
				while(infix.charAt(i-j)=='+' || infix.charAt(i-j)=='-' ||infix.charAt(i-j)=='*' ||infix.charAt(i-j)=='/')
					j++;
				
				if(i+1!=infix.length())
					infix=infix.substring(0,i-j)+'%'+infix.substring(i-j,i)+infix.substring(i+1,infix.length());
				else
					infix=infix.substring(0,i-j)+'%'+infix.substring(i-j,i);
			}
            
            
		}
		
		
         
		if(infix.charAt(infix.length()-1)=='%')
        {
            int j=2;
		
            while(infix.charAt(i-j)!='+' && infix.charAt(i-j)!='-' && infix.charAt(i-j)!='*' && infix.charAt(i-j)!='/')
            	j++;
            
            infix=infix.substring(0,i-j+1)+'%'+infix.substring(i-j+1,infix.length()-1);
        }
		
		for(i=0;i<infix.length()-1;i++)
		{
			if(infix.charAt(i)=='*' && infix.charAt(i+1)=='%')
				infix=infix.substring(0,i)+infix.substring(i+1,infix.length());
		}
		
		return infix;
	}
        
    public int getNextElement(String exp, boolean bRPN)
    {
        StringBuilder stringBuffer = new StringBuilder();
        buffer=stringBuffer.toString();
        int i=pos, j=0;
        
        while (i<exp.length() && (exp.charAt(i)==' ' || exp.charAt(i)=='\t'))
        {
            i++;
        }
        
        if (i<exp.length() && exp.charAt(i)>='0' && exp.charAt(i)<='9')
        {
            while(i<exp.length() && exp.charAt(i)>='0' && exp.charAt(i)<='9')
            {
                buffer+=exp.charAt(i++);
            }
            
            if (i<exp.length() && exp.charAt(i)=='.')
                {
                   buffer+=exp.charAt(i++);
                }
            else if(i>exp.length() && exp.charAt(i-1)=='.')
        		return ERROR;
            while(i<exp.length() && exp.charAt(i)>='0' && exp.charAt(i)<='9' && i<exp.length())
            {
                buffer+=exp.charAt(i++);
            }
            
            
            buffer+=' ';
            pos=i;
            return NUMBER;
            
        }
        
        if(exp.charAt(pos)=='+' || exp.charAt(pos)=='-' || exp.charAt(pos)=='*' || exp.charAt(pos)=='/'
                || exp.charAt(pos)=='^' || exp.charAt(pos)=='%')
        {
            buffer+=exp.charAt(pos);
            buffer+=' ';
            pos++;
            return OPERATOR;
        }
        
        if(exp.charAt(pos)=='(')
        {
            buffer+=exp.charAt(pos);
            buffer+=' ';
            pos++;
            return LB;
        }
        
        if(exp.charAt(pos)==')')
        {
            buffer+=exp.charAt(pos);
            buffer+=' ';
            pos++;
            return RB;
        }
        
        if((exp.charAt(pos)>='A' && exp.charAt(pos)<='Z') || (exp.charAt(pos)>='a' && exp.charAt(pos)<='z'))
        {
            int lb=-1, rb=-1;
            
            String s = exp.substring(pos,exp.length());
            lb=s.indexOf('(');
            rb=s.indexOf(')');
            
            if(!bRPN)
            {
                if(lb<0 || rb<0 || rb<=lb)
                    return ERROR;
                buffer=s.substring(0,lb);
            }
            
            else
            {
                lb=s.indexOf(' ');
                buffer=s.substring(0,lb);
            }
            

    		if(!isFunction(buffer)) // jezeli funkcja jest nieobslugiwana zwroc BLAD
    			return ERROR;
            
            buffer+=' ';
            if(!bRPN)
		pos += lb; // zwiekszamy pozycje  na nastepna za nazwa funkcji aby moc dalej parsowac
            else
                pos += (lb+1);

            return FUNCTION; // zwracamy sygnal ze pobrano funkcje
        }
        
        else 
        {
            pos++;
            return ERROR;
            
        }
        
        
    }
    
    public void wypisz()
    {
        System.out.println(pos);
    }
    
    public String getBuffer()
    {
        return buffer;
    }
    
    public boolean isFunction(String f)
    {
        if (f.equals("sin") || f.equals("cos") || f.equals("asin") || f.equals("acos") ||
        	f.equals("tan") || f.equals("cot") || f.equals("atan") || f.equals("acot") ||
        	f.equals("sinh") || f.equals("cosh") || f.equals("asinh") || f.equals("acosh") ||
        	f.equals("tanh") || f.equals("coth") || f.equals("atanh") || f.equals("acoth") ||
        	f.equals("sqrt") || f.equals("ln") || f.equals("log") || f.equals("abs"))
            return true;
        else
            return false;
    }
    
    public int getPrior(String op)
    {
        if (isFunction(op))
            {
              assoc=RIGHT_ASSOC;
              return 5;
            }
        
        assoc=LEFT_ASSOC;
        if (op.equals("*") || op.equals("/"))
            return 3;
        if (op.equals("+") || op.equals("-"))
            return 2;
        if (op.equals("%") || op.equals("^"))
            return 4;
        
        return 0;
    }
    
    public boolean isTrigonometric(String f)
    {
    	if(f.equals("sin") || f.equals("cos") || f.equals("tan") || f.equals("cot") ||
    	   f.equals("asin") || f.equals("acos") ||f.equals("atan") || f.equals("acot") ||
    	   f.equals("sinh") || f.equals("cosh") || f.equals("asinh") || f.equals("acosh") ||
       		f.equals("tanh") || f.equals("coth") || f.equals("atanh") || f.equals("acoth"))
    		return true;
    	else
    		return false;
    }
    
    public boolean evaluateFunction(String f,String arg,boolean units)
    {
    	f=f.substring(0,f.length()-1);
    	
    	if(isTrigonometric(f) && units )
    	{
    		double a;
    		a=Double.parseDouble(arg);
    		a=a*3.14159265/180;
    		arg=Double.toString(a);
    	}
    	
        
        double a=Double.parseDouble(arg);
        if(f.equals("sin"))
            resultFunction=Math.sin(a);
        else if (f.equals("cos"))
            resultFunction=Math.cos(a);
        else if (f.equals("tan"))
            resultFunction=Math.tan(a);
        else if (f.equals("cot"))
            resultFunction=1/Math.tan(a);
        else if(f.equals("asin"))
        {
        	if(a>=-1 && a<=1)
        		resultFunction=Math.asin(a);
        	else
        		return false;
        }
        else if (f.equals("acos"))
        {
        	if(a>=-1 && a<=1)
        		resultFunction=Math.acos(a);
        	else
        		return false;
        }
        else if (f.equals("atan"))
            resultFunction=Math.atan(a);
        else if (f.equals("acot"))
            resultFunction=1/Math.atan(a);
        else if(f.equals("ln"))
        {
        	if(a>0)
        		resultFunction=Math.log(a);
        	else
        		return false;
        }
        else if (f.equals("log"))
        {
        	if(a>0)
        		resultFunction=Math.log10(a);
        	else
        		return false;
        }
        else if (f.equals("sqrt"))
        {
        	if(a>0)
        		resultFunction=Math.sqrt(a);
        	else
        		return false;
        }
        else if (f.equals("abs"))
            resultFunction=Math.abs(a);
        else if (f.equals("sinh"))
            resultFunction=Math.sinh(a);
        else if (f.equals("cosh"))
        		resultFunction=Math.cosh(a);
        else if (f.equals("tanh"))
        	resultFunction=Math.tanh(a);
        else if (f.equals("coth"))
        {
        	if(a!=0)
        		resultFunction=1/Math.tanh(a);
        	else
        		return false;
        }
        else if(f.equals("asinh"))
            resultFunction=Math.log(a + Math.sqrt(a*a + 1.0));
        else if (f.equals("acosh"))
        {
        	if(a<1)
        			return false;
        	else
        		resultFunction=Math.log(a + Math.sqrt(a*a - 1.0));
        }
        else if (f.equals("atanh"))
        {
        	if(a>=-1 && a<=1)
        		resultFunction=0.5*Math.log( (a + 1.0) / (a - 1.0) );
        	else
        		return false;
        }
            
        else if (f.equals("acoth"))
        {
        	if(a!=0)
        		resultFunction=1/0.5*Math.log( (a + 1.0) / (a - 1.0) );
        	else
        		return false;
        }
        	
        else
        	return false;
        
        return true;
    }
    
    public int infixToRPN(String infix)
    {
    	infix=optimalizedInfix(infix);
    	
        //StringBuffer stringBuffer = new StringBuffer();
        rpn="";
        Stack stack = new Stack();
        int ret;
        pos=0;
        String buffer2,tmp;
        
        while(pos<infix.length())
        {
            ret = getNextElement(infix,false);
            
            switch(ret)
            {
                case NUMBER:
                    rpn+=buffer;
                    break;
                case FUNCTION:
                    stack.push(buffer);
                    break;
                case OPERATOR:
                    while (//!stack.look().equals("EMPTY") &&
                          ((getPrior(buffer.substring(0,buffer.length()-1)) <= getPrior(stack.look().substring(0,stack.look().length()-1))
                           && assoc==LEFT_ASSOC) ||
                            (getPrior(buffer.substring(0,buffer.length()-1)) < getPrior(stack.look().substring(0,stack.look().length()-1))
                           && assoc==RIGHT_ASSOC) )
                            )
                    {
                        tmp=stack.pop();
                        rpn+=tmp;
                    }
                    stack.push(buffer);
                    break;
                case LB:
                    stack.push(buffer);
                    break;
                case RB:
                    buffer2="0";
                    
                        while(buffer2.charAt(0)!='(')
                        {
                            buffer2=stack.pop();
                            if(buffer2.equals("EMPTY"))
                            {
                                return ERROR;
                            }
                            if(buffer2.charAt(0)!='(')
                                rpn+=buffer2;  
                        }
                        break;
                case ERROR:
                	return ERROR;
            }
        }
        
        while(stack.look()!="EMPTY")
        {
            rpn+=stack.pop();
        }
        return 0;
    }
    
    public int evaluateRPN(String RPN, boolean units)
    {
        if (RPN.length()==0)
            return 0;
        
        Stack stack = new Stack();
        String tmp, buffer2;
        int res;
        pos=0;
        double a,b,c;
        
        while(pos<RPN.length())
        {
            res=getNextElement(RPN,true);
            
            switch(res)
            {
                case NUMBER:
                    stack.push(buffer);
                    break;
                    
                case OPERATOR:
                    if(buffer.charAt(0)=='+')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=a+b;
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    if(buffer.charAt(0)=='-')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=b-a;
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    if(buffer.charAt(0)=='*')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=a*b;
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    if(buffer.charAt(0)=='/')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=b/a;
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    if(buffer.charAt(0)=='^')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=Math.pow(b,a);
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    if(buffer.charAt(0)=='%')
                    {
                        buffer2=stack.pop();
                        a=Float.parseFloat(buffer2);
                        buffer2=stack.pop();
                        b=Float.parseFloat(buffer2);
                        c=b*(a/100);
                        buffer2=Float.toString((float)c);
                        stack.push(buffer2);
                    }
                    
                    break;
                    
                case FUNCTION:
                	
                    buffer2=stack.pop();
                    if(!buffer2.equals("EMPTY"))
                    {
                        if(!evaluateFunction(buffer,buffer2,units))
                            return ERROR;
                       // else;
                    }
                    else
                        return ERROR;
                    tmp=Double.toString(resultFunction);
                    stack.push(tmp);
                    break;
                case ERROR:
                	//return ERROR;
            }
        }
        
        buffer2=stack.pop();
        result=Float.parseFloat(buffer2);
        result=Math.round(result*10000000);
        result=result/10000000;
        return 0;
    }
    
    public String getRpn()
    {
        return rpn;
    }
}
