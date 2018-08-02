import java.util.*;

class Floatp extends Exp
{
	float floatElem;
	
	Floatp(float f)
	{
		floatElem = f;
	}
	
	Val Eval(HashMap<String,Val> state)
	{
		return new FloatVal(floatElem);
	}
}