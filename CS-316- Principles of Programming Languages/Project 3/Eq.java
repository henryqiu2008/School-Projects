import java.util.HashMap;

class Eq extends CompExp
{
	// Inherits "ExpList expList" from CompExp

	Eq(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub

		return eqEval(expList, state);
	}

	Val eqEval(ExpList expList, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		
		if( expList == null) //Eval( (= emptyExpList), Éø ) = true 
			return new BoolVal(true);
			
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = eqEval(expList.tailExpList(),state);
		if ( firstExpVal == null || tailExpListVal == null ) //if e1 = Å€v or el = Å€v then Å€v 
			return null;
		if( !firstExpVal.isNumber() )  //else if e1 is not a number then Å€v 
		{
			System.out.println( "Error: = operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = tailExpListVal.getClass();
		
		//else true 
		if( firstExpVal.isNumber() && tailExpListVal.isNumber()){ //check compExp being used is number
			if( firstExpVal.getClass() == IntVal.class && tailExpListVal.getClass() == IntVal.class){
				((BoolVal)firstExpVal).val = ((IntVal)firstExpVal).val == ((IntVal)tailExpListVal).val;
				return firstExpVal;
			}
			else if ( exp1Class == IntVal.class && exp2Class == FloatVal.class )
			{
				((BoolVal)tailExpListVal).val = ((IntVal)firstExpVal).val == ((FloatVal)tailExpListVal).val;
				return tailExpListVal;
			}
			else if ( exp1Class == FloatVal.class && exp2Class == IntVal.class )
			{
				((BoolVal)firstExpVal).val = ((FloatVal)firstExpVal).val == ((IntVal)tailExpListVal).val;
				return firstExpVal;
			}
			else  // ( exp1Class == FloatVal.class && exp2Class == FloatVal.class)
			{
				((BoolVal)firstExpVal).val = ((FloatVal)firstExpVal).val == ((FloatVal)tailExpListVal).val;
				return firstExpVal; 
			}
		}
		//else (e1 = e2) Å» el 
		else //( exp1Class == BoolVal.class && exp2Class == BoolVal.class )
		{
			((BoolVal)firstExpVal).val = ((BoolVal)firstExpVal).val == ((BoolVal)tailExpListVal).val;
			return firstExpVal;
		}

	}
}