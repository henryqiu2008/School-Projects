import java.util.HashMap;

class Or extends BoolExp
{
	// Inherits "ExpList expList" from BoolExp

	Or(ExpList e)
	{
		expList = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return orEval(expList, state);
	}

	Val orEval(ExpList expList, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		if ( expList == null ) //Eval( (| emptyExpList), Éø ) = false 
			return new BoolVal(false);
		
		Val firstExpVal = expList.firstExp().Eval(state);
		Val tailExpListVal = orEval(expList.tailExpList(),state);
		if ( firstExpVal == null || tailExpListVal == null ) //if e = Å€v or el = Å€v then Å€v 
			return null;
		
		// The result will be float if one or both of the arguments are float.
	
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = tailExpListVal.getClass();
		
		if ( exp1Class != BoolVal.class) //else if e is not boolean then Å€v 
		{
			System.out.println( "Error: | operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		//else e Å… el
		else // ( exp1Class == BoolVal.class && exp2Class == BoolVal.class )  
		{
			((BoolVal)firstExpVal).val = ((BoolVal)firstExpVal).val || ((BoolVal)tailExpListVal).val;
			return firstExpVal;
		}
	
	}
}