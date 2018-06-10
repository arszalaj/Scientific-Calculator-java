import java.util.Scanner;

import com.example.rpn.RPN;

public class hello {

	static RPN rpn = new RPN();
	public static void main(String [] args)
	  {
		System.out.println("Write your expression down, please.");
		Scanner input = new Scanner(System.in);
		String expression = input.next();
		rpn.infixToRPN(expression);
		rpn.evaluateRPN(rpn.rpn, true);
		System.out.println(rpn.result);
		System.out.println(rpn.rpn);
	  }

}
