import java.util.*;

class Id extends Exp
{
	String id;
	
	Id(String s)
	{
		id = s;
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		Val idVal = state.get(id);
		if ( idVal != null )
			return idVal.cloneVal();
		System.out.println( "Error: variable "+id+" does not have a value" );
		return null;
	}
}