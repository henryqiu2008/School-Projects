import java.util.HashMap;

class Sub extends ArithExp
{
	// Inherits "ExpList expList" from ArithExp

	Sub(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return subEval(expList, state);
	}

	Val subEval(ExpList expList, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		if ( expList == null ) //Eval( (+ emptyExpList), Éø ) = 0
			return new IntVal(0);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = subEval(expList.tailExpList(), state);
		if ( firstExpVal == null || tailExpListVal == null )
			return null;
		if ( ! firstExpVal.isNumber() )//else if e is not a number then Å€v 
		{
			System.out.println( "Error: - operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}

		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = tailExpListVal.getClass();
		
		
		// else e - el 
		if ( exp1Class == IntVal.class && exp2Class == IntVal.class )
		{
			((IntVal)firstExpVal).val = ((IntVal)firstExpVal).val - ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else if ( exp1Class == IntVal.class && exp2Class == FloatVal.class )
		{
			((FloatVal)tailExpListVal).val = ((IntVal)firstExpVal).val - ((FloatVal)tailExpListVal).val;
			return tailExpListVal;
		}
		else if ( exp1Class == FloatVal.class && exp2Class == IntVal.class )
		{
			((FloatVal)firstExpVal).val = ((FloatVal)firstExpVal).val - ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else // ( exp1Class == FloatVal.class && exp2Class == FloatVal.class )
		{
			((FloatVal)firstExpVal).val = ((FloatVal)firstExpVal).val - ((FloatVal)tailExpListVal).val;
			return firstExpVal;
		}
	}
}