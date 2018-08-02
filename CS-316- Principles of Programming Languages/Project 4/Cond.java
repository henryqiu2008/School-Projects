import java.util.*;

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

	Val Eval(HashMap<String,Val> state)
	{
		Val boolVal = exp1.Eval(state);
		if ( boolVal == null )
			return null;
		if ( boolVal.getClass() != BoolVal.class )
		{
			System.out.println( "Error: boolean condition of if-expression evaluated to non-boolean value: " + boolVal.toString() );
			return null;
		}

		if ( ((BoolVal)boolVal).val  )
			return exp2.Eval(state);
		else
			return exp3.Eval(state);
	}
}