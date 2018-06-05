package salatsoft.rpn;

import java.io.PrintStream;

public class RPN
{
  private final int EOS = 5;
  private final int ERROR = -1;
  private final int FUNCTION = 1;
  private final int LB = 3;
  private final int LEFT_ASSOC = 0;
  private final int NUMBER = 0;
  private final int OPERATOR = 2;
  private final int RB = 4;
  private final int RIGHT_ASSOC = 1;
  private int assoc;
  private String buffer;
  private int pos;
  public double result;
  public double resultFunction;
  public String rpn;

  public static void main(String [] args)
  {
//    this.evaluateRPN("2+2", false);
  }

  public boolean evaluateFunction(String paramString1, String paramString2, boolean paramBoolean)
  {
    String str = paramString1.substring(0, paramString1.length() - 1);
    paramString1 = paramString2;
    if (isTrigonometric(str))
    {
      paramString1 = paramString2;
      if (paramBoolean) {
        paramString1 = Double.toString(3.14159265D * Double.parseDouble(paramString2) / 180.0D);
      }
    }
    double d = Double.parseDouble(paramString1);
    if (str.equals("sin")) {
      this.resultFunction = Math.sin(d);
    }
    for (;;)
    {
//      return true;
      if (str.equals("cos"))
      {
        this.resultFunction = Math.cos(d);
      }
      else if (str.equals("tan"))
      {
        this.resultFunction = Math.tan(d);
      }
      else if (str.equals("cot"))
      {
        this.resultFunction = (1.0D / Math.tan(d));
      }
      else if (str.equals("asin"))
      {
        if ((d >= -1.0D) && (d <= 1.0D)) {
          this.resultFunction = Math.asin(d);
        } else {
          return false;
        }
      }
      else if (str.equals("acos"))
      {
        if ((d >= -1.0D) && (d <= 1.0D)) {
          this.resultFunction = Math.acos(d);
        } else {
          return false;
        }
      }
      else if (str.equals("atan"))
      {
        this.resultFunction = Math.atan(d);
      }
      else if (str.equals("acot"))
      {
        this.resultFunction = (1.0D / Math.atan(d));
      }
      else if (str.equals("ln"))
      {
        if (d > 0.0D) {
          this.resultFunction = Math.log(d);
        } else {
          return false;
        }
      }
      else if (str.equals("log"))
      {
        if (d > 0.0D) {
          this.resultFunction = Math.log10(d);
        } else {
          return false;
        }
      }
      else if (str.equals("sqrt"))
      {
        if (d > 0.0D) {
          this.resultFunction = Math.sqrt(d);
        } else {
          return false;
        }
      }
      else if (str.equals("abs"))
      {
        this.resultFunction = Math.abs(d);
      }
      else if (str.equals("sinh"))
      {
        this.resultFunction = Math.sinh(d);
      }
      else if (str.equals("cosh"))
      {
        this.resultFunction = Math.cosh(d);
      }
      else if (str.equals("tanh"))
      {
        this.resultFunction = Math.tanh(d);
      }
      else if (str.equals("coth"))
      {
        if (d != 0.0D) {
          this.resultFunction = (1.0D / Math.tanh(d));
        } else {
          return false;
        }
      }
      else if (str.equals("asinh"))
      {
        this.resultFunction = Math.log(Math.sqrt(d * d + 1.0D) + d);
      }
      else if (str.equals("acosh"))
      {
        if (d < 1.0D) {
          return false;
        }
        this.resultFunction = Math.log(Math.sqrt(d * d - 1.0D) + d);
      }
      else if (str.equals("atanh"))
      {
        if ((d >= -1.0D) && (d <= 1.0D)) {
          this.resultFunction = (0.5D * Math.log((1.0D + d) / (d - 1.0D)));
        } else {
          return false;
        }
      }
      else
      {
//        if (!str.equals("acoth")) {
//          break label649;
//        }
        if (d == 0.0D) {
          break;
        }
        this.resultFunction = (2.0D * Math.log((1.0D + d) / (d - 1.0D)));
      }
    }
    return false;
//    label649:
//    return false;
  }
  
  public int evaluateRPN(String paramString, boolean paramBoolean)
  {
    if (paramString.length() == 0) {
      return 0;
    }
    Stack localStack = new Stack();
    this.pos = 0;
    for (;;)
    {
      if (this.pos >= paramString.length())
      {
        this.result = Float.parseFloat(localStack.pop());
        this.result = Math.round(this.result * 1.0E7D);
        this.result /= 1.0E7D;
        return 0;
      }
      switch (getNextElement(paramString, true))
      {
      default: 
        break;
      case 0: 
        localStack.push(this.buffer);
        break;
      case 2: 
        if (this.buffer.charAt(0) == '+') {
          localStack.push(Float.toString((float)(Float.parseFloat(localStack.pop()) + Float.parseFloat(localStack.pop()))));
        }
        double d1;
        if (this.buffer.charAt(0) == '-')
        {
          d1 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)(Float.parseFloat(localStack.pop()) - d1)));
        }
        if (this.buffer.charAt(0) == '×') {
          localStack.push(Float.toString((float)(Float.parseFloat(localStack.pop()) * Float.parseFloat(localStack.pop()))));
        }
        if (this.buffer.charAt(0) == '÷')
        {
          d1 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)(Float.parseFloat(localStack.pop()) / d1)));
        }
        if (this.buffer.charAt(0) == '^')
        {
          d1 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)Math.pow(Float.parseFloat(localStack.pop()), d1)));
        }
        if (this.buffer.charAt(0) == '%')
        {
          d1 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)(Float.parseFloat(localStack.pop()) * (d1 / 100.0D))));
        }
        double d2;
        if (this.buffer.charAt(0) == '$')
        {
          d1 = Float.parseFloat(localStack.pop());
          d2 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)(d1 / 100.0D * d2 + d2)));
        }
        if (this.buffer.charAt(0) == '#')
        {
          d1 = Float.parseFloat(localStack.pop());
          d2 = Float.parseFloat(localStack.pop());
          localStack.push(Float.toString((float)(-d2 * (d1 / 100.0D) + d2)));
        }
        break;
      case 1: 
        String str = localStack.pop();
        if (!str.equals("EMPTY"))
        {
          if (!evaluateFunction(this.buffer, str, paramBoolean)) {
            return -1;
          }
        }
        else {
          return -1;
        }
        localStack.push(Double.toString(this.resultFunction));
      }
    }
  }
  
  public String getBuffer()
  {
    return this.buffer;
  }
  
  public int getNextElement(String paramString, boolean paramBoolean)
  {
    this.buffer = new StringBuilder().toString();
    int j = this.pos;
//    label82:
    int i = 0;
//    label168:
    StringBuilder localStringBuilder;
    if ((j >= paramString.length()) || ((paramString.charAt(j) != ' ') && (paramString.charAt(j) != '\t')))
    {
      if ((j >= paramString.length()) || (paramString.charAt(j) < '0') || (paramString.charAt(j) > '9')) {
//        break label559;
      }
      if ((j < paramString.length()) && (paramString.charAt(j) >= '0') && (paramString.charAt(j) <= '9')) {
//        break label410;
      }
      if ((j >= paramString.length()) || (paramString.charAt(j) != '.')) {
//        break label449;
      }
      this.buffer += paramString.charAt(j);
      i = j + 1;
      if ((i < paramString.length()) && (paramString.charAt(i) >= '0') && (paramString.charAt(i) <= '9') && (i < paramString.length())) {
//        break label479;
      }
      j = i;
      if (i < paramString.length())
      {
        j = i;
        if (paramString.charAt(i) == 'E')
        {
          localStringBuilder = new StringBuilder(String.valueOf(this.buffer));
          j = i + 1;
          this.buffer = String.valueOf(paramString.charAt(i));
          i = j;
          if (paramString.charAt(j) != '-') {
//            break label556;
          }
          localStringBuilder = new StringBuilder(String.valueOf(this.buffer));
          i = j + 1;
          this.buffer = String.valueOf(paramString.charAt(j));
        }
      }
    }
//    label410:
//    label449:
//    label479:
//    label515:
//    label556:
    for (;;)
    {
      j = i;
      if (i < paramString.length())
      {
        j = i;
        if (paramString.charAt(i) >= '0')
        {
          j = i;
          if (paramString.charAt(i) <= '9')
          {
            if (i < paramString.length()) {
//              break label515;
            }
            j = i;
          }
        }
      }
      this.buffer += ' ';
      this.pos = j;
      return 0;
      j += 1;
      break;
      this.buffer += paramString.charAt(j);
      j += 1;
      break label82;
      i = j;
      if (j <= paramString.length()) {
        break label168;
      }
      i = j;
      if (paramString.charAt(j - 1) != '.') {
        break label168;
      }
      return -1;
      this.buffer += paramString.charAt(i);
      i += 1;
      break label168;
      localStringBuilder = new StringBuilder(String.valueOf(this.buffer));
      j = i + 1;
      this.buffer = String.valueOf(paramString.charAt(i));
      i = j;
    }
    label559:
    if ((paramString.charAt(this.pos) == '+') || (paramString.charAt(this.pos) == '-') || (paramString.charAt(this.pos) == '×') || (paramString.charAt(this.pos) == '÷') || (paramString.charAt(this.pos) == '^') || (paramString.charAt(this.pos) == '%') || (paramString.charAt(this.pos) == '#') || (paramString.charAt(this.pos) == '$'))
    {
      this.buffer += paramString.charAt(this.pos);
      this.buffer += ' ';
      this.pos += 1;
      return 2;
    }
    if (paramString.charAt(this.pos) == '(')
    {
      this.buffer += paramString.charAt(this.pos);
      this.buffer += ' ';
      this.pos += 1;
      return 3;
    }
    if (paramString.charAt(this.pos) == ')')
    {
      this.buffer += paramString.charAt(this.pos);
      this.buffer += ' ';
      this.pos += 1;
      return 4;
    }
    if (((paramString.charAt(this.pos) >= 'A') && (paramString.charAt(this.pos) <= 'Z')) || ((paramString.charAt(this.pos) >= 'a') && (paramString.charAt(this.pos) <= 'z')))
    {
      paramString = paramString.substring(this.pos, paramString.length());
      i = paramString.indexOf('(');
      j = paramString.indexOf(')');
      if (!paramBoolean) {
        if ((i < 0) || (j < 0) || (j <= i)) {
          return -1;
        }
      }
      for (this.buffer = paramString.substring(0, i); !isFunction(this.buffer); this.buffer = paramString.substring(0, i))
      {
        return -1;
        i = paramString.indexOf(' ');
      }
      this.buffer += ' ';
      if (!paramBoolean) {}
      for (this.pos += i;; this.pos += i + 1) {
        return 1;
      }
    }
    this.pos += 1;
    return -1;
  }
  
  public int getPrior(String paramString)
  {
    int i = 0;
    if (isFunction(paramString))
    {
      this.assoc = 1;
      i = 5;
    }
    do
    {
//      return i;
      this.assoc = 0;
      if ((paramString.equals("×")) || (paramString.equals("÷"))) {
        return 3;
      }
      if ((paramString.equals("+")) || (paramString.equals("-"))) {
        return 2;
      }
    } while ((!paramString.equals("%")) && (!paramString.equals("^")) && (!paramString.equals("#")) && (!paramString.equals("$")));
    return 4;
  }
  
  public String getRpn()
  {
    return this.rpn;
  }
  
  public int infixToRPN(String paramString)
  {
    int j = -1;
    String str2 = optimalizedInfix(paramString);
    this.rpn = "";
    Stack localStack = new Stack();
    this.pos = 0;
    if (this.pos >= str2.length()) {}
    for (;;)
    {
      if (localStack.look() == "EMPTY")
      {
        int i = 0;
        return i;
        i = j;
        switch (getNextElement(str2, false))
        {
        case -1: 
        default: 
          break;
        case 0: 
          this.rpn += this.buffer;
          break;
        case 1: 
          localStack.push(this.buffer);
          break;
        case 2: 
          do
          {
            paramString = localStack.pop();
            this.rpn += paramString;
          } while (((getPrior(this.buffer.substring(0, this.buffer.length() - 1)) <= getPrior(localStack.look().substring(0, localStack.look().length() - 1))) && (this.assoc == 0)) || ((getPrior(this.buffer.substring(0, this.buffer.length() - 1)) < getPrior(localStack.look().substring(0, localStack.look().length() - 1))) && (this.assoc == 1)));
          localStack.push(this.buffer);
          break;
        case 3: 
          localStack.push(this.buffer);
          break;
        case 4: 
          paramString = "0";
          for (;;)
          {
            if (paramString.charAt(0) == '(') {
              break label391;
            }
            String str1 = localStack.pop();
            i = j;
            if (str1.equals("EMPTY")) {
              break;
            }
            paramString = str1;
            if (str1.charAt(0) != '(')
            {
              this.rpn += str1;
              paramString = str1;
            }
          }
          label391:
          break;
        }
      }
      this.rpn += localStack.pop();
    }
  }
  
  public boolean isFunction(String paramString)
  {
    return (paramString.equals("sin")) || (paramString.equals("cos")) || (paramString.equals("asin")) || (paramString.equals("acos")) || (paramString.equals("tan")) || (paramString.equals("cot")) || (paramString.equals("atan")) || (paramString.equals("acot")) || (paramString.equals("sinh")) || (paramString.equals("cosh")) || (paramString.equals("asinh")) || (paramString.equals("acosh")) || (paramString.equals("tanh")) || (paramString.equals("coth")) || (paramString.equals("atanh")) || (paramString.equals("acoth")) || (paramString.equals("sqrt")) || (paramString.equals("ln")) || (paramString.equals("log")) || (paramString.equals("abs"));
  }
  
  public boolean isTrigonometric(String paramString)
  {
    return (paramString.equals("sin")) || (paramString.equals("cos")) || (paramString.equals("tan")) || (paramString.equals("cot")) || (paramString.equals("asin")) || (paramString.equals("acos")) || (paramString.equals("atan")) || (paramString.equals("acot")) || (paramString.equals("sinh")) || (paramString.equals("cosh")) || (paramString.equals("asinh")) || (paramString.equals("acosh")) || (paramString.equals("tanh")) || (paramString.equals("coth")) || (paramString.equals("atanh")) || (paramString.equals("acoth"));
  }
  
  public boolean lastIsAnOperator(String paramString)
  {
    if (paramString.length() == 0) {}
    while ((paramString.charAt(paramString.length() - 1) != '-') && (paramString.charAt(paramString.length() - 1) != '+') && (paramString.charAt(paramString.length() - 1) != '×') && (paramString.charAt(paramString.length() - 1) != '÷') && (paramString.charAt(paramString.length() - 1) != '^')) {
      return false;
    }
    return true;
  }
  
  public String optimalizedInfix(String paramString)
  {
    int j = 0;
    int i = 0;
    String str;
    if (paramString.charAt(0) != '-')
    {
      str = paramString;
      if (paramString.charAt(0) != '+') {}
    }
    else
    {
      str = '0' + paramString;
    }
    int k = 0;
    if (k >= str.length())
    {
      paramString = str;
      label64:
      if (i < j) {
        break label371;
      }
      i = 0;
      label71:
      if (i < paramString.length() - 1) {
        break label399;
      }
      str = paramString;
      label92:
      do
      {
        j = 0;
        k = 0;
        paramString = str;
        if (k < paramString.length()) {
          break;
        }
        str = paramString;
      } while (j != 0);
      i = 0;
      if (i < paramString.length() - 1) {
        break label788;
      }
      str = paramString;
      if (paramString.charAt(paramString.length() - 1) == '%')
      {
        j = 2;
        label140:
        if ((paramString.charAt(i - j) != '+') && (paramString.charAt(i - j) != '-') && (paramString.charAt(i - j) != '×') && (paramString.charAt(i - j) != '÷')) {
          break label1646;
        }
        str = paramString.substring(0, i - j + 1) + '%' + paramString.substring(i - j + 1, paramString.length() - 1);
      }
      i = 0;
    }
    for (;;)
    {
      if (i >= str.length() - 1)
      {
        return str;
        int m = j;
        if (str.charAt(k) == '(') {
          m = j + 1;
        }
        int n = i;
        if (str.charAt(k) == ')') {
          n = i + 1;
        }
        paramString = str;
        if (str.charAt(k) == '/') {
          paramString = str.substring(0, k) + "÷" + str.substring(k + 1, str.length());
        }
        k += 1;
        j = m;
        i = n;
        str = paramString;
        break;
        label371:
        paramString = paramString + ")";
        i += 1;
        break label64;
        label399:
        if (((paramString.charAt(i) < '0') || (paramString.charAt(i) > '9') || ((paramString.charAt(i + 1) != 'π') && (paramString.charAt(i + 1) != 'e'))) && ((paramString.charAt(i + 1) < '0') || (paramString.charAt(i + 1) > '9') || ((paramString.charAt(i) != 'π') && (paramString.charAt(i) != 'e'))) && ((paramString.charAt(i + 1) != 'π') || (paramString.charAt(i) != 'e')) && ((paramString.charAt(i) != 'π') || (paramString.charAt(i + 1) != 'e')))
        {
          if (paramString.charAt(i) != 'π')
          {
            str = paramString;
            if (paramString.charAt(i) != 'e') {}
          }
          else
          {
            str = paramString;
            if (paramString.charAt(i + 1) >= 'a')
            {
              str = paramString;
              if (paramString.charAt(i + 1) > 'z') {}
            }
          }
        }
        else {
          str = paramString.substring(0, i + 1) + '×' + paramString.substring(i + 1, paramString.length());
        }
        i += 1;
        paramString = str;
        break label71;
        i = k;
        str = paramString;
        if (paramString.charAt(k) == 'e')
        {
          str = paramString.substring(0, k) + "(2.71828183)" + paramString.substring(k + 1, paramString.length());
          i = k + 8;
          j = 1;
        }
        k = i;
        paramString = str;
        if (str.charAt(i) == 'π')
        {
          paramString = str.substring(0, i) + "(3.14159265)" + str.substring(i + 1, str.length());
          k = i + 8;
          j = 1;
        }
        k += 1;
        break label92;
        label788:
        str = paramString;
        if (paramString.charAt(i) == '(')
        {
          str = paramString;
          if (paramString.charAt(i + 1) == '-') {
            str = paramString.substring(0, i + 1) + '0' + paramString.substring(i + 1, paramString.length());
          }
        }
        paramString = str;
        if (str.charAt(i) >= '0')
        {
          paramString = str;
          if (str.charAt(i) <= '9')
          {
            paramString = str;
            if (str.charAt(i + 1) == '(') {
              paramString = str.substring(0, i + 1) + '×' + str.substring(i + 1, str.length());
            }
          }
        }
        str = paramString;
        if (paramString.charAt(i + 1) >= '0')
        {
          str = paramString;
          if (paramString.charAt(i + 1) <= '9')
          {
            str = paramString;
            if (paramString.charAt(i) == ')') {
              str = paramString.substring(0, i + 1) + '×' + paramString.substring(i + 1, paramString.length());
            }
          }
        }
        paramString = str;
        if (str.charAt(i) >= '0')
        {
          paramString = str;
          if (str.charAt(i) <= '9')
          {
            paramString = str;
            if (str.charAt(i + 1) >= 'a')
            {
              paramString = str;
              if (str.charAt(i + 1) <= 'z') {
                paramString = str.substring(0, i + 1) + '×' + str.substring(i + 1, str.length());
              }
            }
          }
        }
        str = paramString;
        if (paramString.charAt(i) == ')')
        {
          str = paramString;
          if (paramString.charAt(i + 1) == '(') {
            str = paramString.substring(0, i + 1) + '×' + paramString.substring(i + 1, paramString.length());
          }
        }
        paramString = str;
        if (str.charAt(i) == '%')
        {
          paramString = str;
          if (str.charAt(i + 1) == '×') {
            paramString = str.substring(0, i + 1) + str.substring(i + 2, str.length());
          }
        }
        str = paramString;
        if (paramString.charAt(i) == '%')
        {
          str = paramString;
          if (paramString.charAt(i + 1) == ')')
          {
            j = 2;
            label1309:
            if ((paramString.charAt(i - j) != '+') && (paramString.charAt(i - j) != '-') && (paramString.charAt(i - j) != '×') && (paramString.charAt(i - j) != '÷')) {
              break label1589;
            }
            str = paramString.substring(0, i - j + 1) + '%' + paramString.substring(i - j + 1, i) + paramString.substring(i + 1, paramString.length());
          }
        }
        paramString = str;
        if (str.charAt(i) == '%') {
          if (str.charAt(i + 1) != '+')
          {
            paramString = str;
            if (str.charAt(i + 1) != '-') {}
          }
          else
          {
            j = 2;
            label1461:
            if ((str.charAt(i - j) == '+') || (str.charAt(i - j) == '-') || (str.charAt(i - j) == '×') || (str.charAt(i - j) == '÷')) {
              break label1596;
            }
            if (i + 1 == str.length()) {
              break label1603;
            }
          }
        }
        label1589:
        label1596:
        label1603:
        for (paramString = str.substring(0, i - j) + '%' + str.substring(i - j, i) + str.substring(i + 1, str.length());; paramString = str.substring(0, i - j) + '%' + str.substring(i - j, i))
        {
          i += 1;
          break;
          j += 1;
          break label1309;
          j += 1;
          break label1461;
        }
        label1646:
        j += 1;
        break label140;
      }
      paramString = str;
      if (str.charAt(i) == '+')
      {
        paramString = str;
        if (str.charAt(i + 1) == '%') {
          paramString = str.substring(0, i) + '$' + str.substring(i + 2, str.length());
        }
      }
      str = paramString;
      if (paramString.charAt(i) == '-')
      {
        str = paramString;
        if (paramString.charAt(i + 1) == '%') {
          str = paramString.substring(0, i) + '#' + paramString.substring(i + 2, paramString.length());
        }
      }
      paramString = str;
      if (str.charAt(i) == '×')
      {
        paramString = str;
        if (str.charAt(i + 1) == '%') {
          paramString = str.substring(0, i) + str.substring(i + 1, str.length());
        }
      }
      i += 1;
      str = paramString;
    }
  }
  
  public void wypisz()
  {
    System.out.println(this.pos);
  }
}


/* Location:              C:\Users\as67591\Downloads\dex2jar-2.0\classes-dex2jar.jar!\salatsoft\rpn\RPN.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       0.7.1
 */