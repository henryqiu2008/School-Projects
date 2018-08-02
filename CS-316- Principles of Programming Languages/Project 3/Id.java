import java.util.HashMap;

class Id extends Exp
{
	String id;
	
	Id(String s)
	{
		id = s;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		Val idVal = state.get(id);
		if ( idVal != null )
			return idVal.cloneVal();
		else
		{
			System.out.println( "variable "+id+" does not have a value" );
			return null;
		}
	}
}