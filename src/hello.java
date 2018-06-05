import com.example.rpn.RPN;

public class hello {

	static RPN rpn = new RPN();
	public static void main(String [] args)
	  {
		rpn.infixToRPN("ln(10)");
		rpn.evaluateRPN(rpn.rpn, true);
		System.out.println(rpn.result);
	  }

}
