import java.util.*;

// parameter list and body expression indexed by function name
// HashMap<String, FunData> constructed by the semantic checker then used by Eval() for function-call expressions

class FunData
{
	LinkedList<String> parameterList;
	Exp bodyExp;

	public String toString()
	{
		return parameterList.toString();
	}
}