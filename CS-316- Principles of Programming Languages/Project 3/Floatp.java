import java.util.HashMap;

class Floatp extends Exp
{
	float floatElem;
	
	Floatp(float f)
	{
		floatElem = f;
	}

	Val Eval(HashMap<String, Val> state) {
		// TODO Auto-generated method stub
		return new FloatVal(floatElem);
	}
}