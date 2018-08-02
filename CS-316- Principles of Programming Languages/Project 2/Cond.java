
class Cond extends FunExp{

	Exp exp1;
	Exp exp2;
	Exp exp3;
	
	Cond(Exp e1, Exp e2, Exp e3){
		
		exp1 = e1;
		exp2 = e2;
		exp3 = e3;
		
	}
	
	void printParseTree(String indent){
		
		super.printParseTree(indent);
		String indent2 = indent + "  ";
		LexAnalyzer.displayln(indent2 + indent2.length() + " cond");
		exp1.printParseTree(indent2);
		exp2.printParseTree(indent2);
		exp3.printParseTree(indent2);
		
	}
	
}
