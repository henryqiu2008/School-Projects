import java.util.HashMap;

class Not extends FunExp
{
	Exp exp;

	Not(Exp e)
	{
		exp = e;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return notEval(exp, state);
	}

	Val notEval(Exp exp, HashMap<String, Val> state) {
		// TODO Auto-generated method stub

		Val firstExpVal = this.exp.Eval(state);
		if ( firstExpVal == null) //if e = Å€v then Å€v 
			return null;
	
		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
			
		if( exp1Class != BoolVal.class ) // else if e is not boolean then Å€v 
		{
			System.out.println( "Error: ! operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		//else Å  e 
		else // ( exp1Class == BoolVal.class )
		{
			((BoolVal)firstExpVal).val = !((BoolVal)firstExpVal).val;
			return firstExpVal;
		}
		
	
	}
}