import java.util.HashMap;

class Cond extends FunExp
{
	Exp exp1;
	Exp exp2;
	Exp exp3;

	Cond(Exp e1, Exp e2, Exp e3)
	{
		exp1 = e1;
		exp2 = e2;
		exp3 = e3;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return condEval(exp1, exp2, exp3, state);
	}

	Val condEval(Exp exp1, Exp exp2, Exp exp3, HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		
		Val firstExpVal = this.exp1.Eval(state);
		Val secondExpVal = this.exp2.Eval(state);
		Val tailExpListVal = this.exp3.Eval(state);
		if( firstExpVal == null ) // if b = Å€v then Å€v 
			return null;
		
		// The result will be float if one or both of the arguments are float.
		
		Class exp1Class = firstExpVal.getClass();
		Class exp2Class = secondExpVal.getClass();
		Class exp3Class = tailExpListVal.getClass();
		
		if( exp1Class != BoolVal.class) //else if b is not boolean then Å€v 
		{
			System.out.println( "Error: 'if' operator cannot be applied to " + firstExpVal.toString() );
			return null;
		}
		else if (exp1Class == BoolVal.class) // else if b = true then Eval(E1, Éø) 
		{
			return secondExpVal;
		}
		else //else Eval(E2, Éø) 
		{
			return tailExpListVal;
		}
	}

}