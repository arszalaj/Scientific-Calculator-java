import java.util.Scanner;

import com.salatsoft.rpn.RPN;

public class hello {

	static RPN rpn = new RPN();
	public static void main(String [] args)
	  {
		while(true) {
			System.out.println("Write your expression down, please.");
			Scanner input = new Scanner(System.in);
			String expression = input.next();
			rpn.infixToRPN(expression);
			rpn.evaluateRPN(rpn.rpn, true);
			System.out.println("result:\t" + rpn.result);
			System.out.println("RPN:\t" + rpn.rpn);
		}
	  }

}
