
class ClassDef {

	String className;
	ClassBody classBody;
	
	ClassDef(String s, ClassBody cb){
		
		className = s;
		classBody = cb;
	}
	
	public void printParseTree(String indent) {
		// TODO Auto-generated method stub
		String indent1 = indent + " ";
		
		LexAnalyzer.displayln(indent + indent.length() + " <class def>");
		LexAnalyzer.displayln(indent1 + indent1.length() + " <class name> " + className);
		classBody.printParseTree(indent1);
		
	}

}
