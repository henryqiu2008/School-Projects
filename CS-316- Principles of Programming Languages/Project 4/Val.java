// value objects returned by Eval() function

abstract class Val
{
	abstract Val cloneVal();
	abstract float floatVal(); // conversion to floating-point
	abstract boolean isNumber();
	abstract boolean isZero();
	abstract boolean equalVal(Val that);
}