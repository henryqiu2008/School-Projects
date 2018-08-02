
class Floatf extends Exp{

	float floatString;
	
	Floatf(float f){
		floatString = f;
	}
	
	void printParseTree(String indent){
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + floatString);
	}
}
