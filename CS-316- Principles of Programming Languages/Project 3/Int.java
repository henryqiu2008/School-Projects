import java.util.HashMap;

class Int extends Exp
{
	int intElem;
	
	Int(int i)
	{
		intElem = i;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return new IntVal(intElem);
	}

}