
class Program {

	ClassDefList classDefList;
	FunDefList funDefList;
	
	Program(ClassDefList c, FunDefList f){
		
		classDefList = c;
		funDefList = f;
		
	}
	
	void printParseTree(String indent){
		
		IO.display("Display Type of Variables:\n\n\n{ ");
		classDefList.printParseTree(" ");
		funDefList.printParseTree(" ");
		IO.display(" }");
	}
}
