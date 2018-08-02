
class ExpInt extends Exp{

	int intNum;
	
	ExpInt(int n){
		intNum = n;
	}
	
	void printParseTree(String indent){
		super.printParseTree(indent);
		String indent1 = indent+" ";
		LexAnalyzer.displayln(indent1 + indent1.length() + " " + intNum);
	}
	
}
