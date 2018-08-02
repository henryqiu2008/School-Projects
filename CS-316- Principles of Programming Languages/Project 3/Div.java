import java.util.HashMap;

class Div extends ArithExp
{
	// Inherits "ExpList expList" from ArithExp

	Div(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return divEval(expList, state);
	}

	Val divEval(ExpList expList, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		if ( expList == null ) //Eval( (+ emptyExpList), Éø ) = 1
			return new IntVal(1);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = divEval(expList.tailExpList(), state);
		if ( firstExpVal == null || tailExpListVal == null )
			return null;
		if ( ! firstExpVal.isNumber() ) //else if e is not a number then Å€v 
		{
			System.out.println( "Error: / operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		if( ((IntVal)tailExpListVal).val == 0) // else if e1 is a zero or not
		{
			System.out.println( "Error: / operator cannot be applied to " + tailExpListVal.toString() + ", cannot divide by 0!");
			return null;
		}

		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = tailExpListVal.getClass();
		
		
		// else e / el 
		if ( exp1Class == IntVal.class && exp2Class == IntVal.class )
		{
			((IntVal)firstExpVal).val = ((IntVal)firstExpVal).val / ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else if ( exp1Class == IntVal.class && exp2Class == FloatVal.class )
		{
			((FloatVal)tailExpListVal).val = ((IntVal)firstExpVal).val / ((FloatVal)tailExpListVal).val;
			return tailExpListVal;
		}
		else if ( exp1Class == FloatVal.class && exp2Class == IntVal.class )
		{
			((FloatVal)firstExpVal).val = ((FloatVal)firstExpVal).val / ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else // ( exp1Class == FloatVal.class && exp2Class == FloatVal.class )
		{
			((FloatVal)firstExpVal).val = ((FloatVal)firstExpVal).val / ((FloatVal)tailExpListVal).val;
			return firstExpVal;
		}
	}
	
}