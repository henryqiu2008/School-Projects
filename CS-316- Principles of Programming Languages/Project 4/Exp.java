import java.util.*;

abstract class Exp
{
	abstract Val Eval(HashMap<String,Val> state);
}