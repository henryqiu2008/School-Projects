import java.util.HashMap;

class Gt extends CompExp
{
	// Inherits "ExpList expList" from CompExp

	Gt(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return gtEval(expList, state);
	}

	Val gtEval(ExpList expList, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		
		if( expList == null) //Eval( (> emptyExpList), ƒ¿ ) = true
			return new BoolVal(true);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = gtEval(expList.tailExpList(),state);
		if ( firstExpVal == null )// if e = Ûv then Ûv 
			return null;
		if( !firstExpVal.isNumber() || !tailExpListVal.isNumber()) //else if e is not a number then Ûv 
		{
			System.out.println( "Error: > operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		
		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = tailExpListVal.getClass();
			
		// else true 
		if( exp1Class == IntVal.class && exp2Class == IntVal.class)
		{
			((BoolVal)firstExpVal).val = ((IntVal)firstExpVal).val > ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else if( exp1Class == IntVal.class && exp2Class == FloatVal.class)
		{
			((BoolVal)firstExpVal).val = ((IntVal)firstExpVal).val > ((FloatVal)tailExpListVal).val;
			return firstExpVal;
		}
		else if( exp1Class == FloatVal.class && exp2Class == IntVal.class)
		{
			((BoolVal)firstExpVal).val = ((FloatVal)firstExpVal).val > ((IntVal)tailExpListVal).val;
			return firstExpVal;
		}
		else // ( exp1Class == FloatVal.class && exp2Class == FloatVal.class)
		{
			((BoolVal)firstExpVal).val = ((FloatVal)firstExpVal).val > ((FloatVal)tailExpListVal).val;
			return firstExpVal;
		}
	}
}