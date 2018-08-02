
class ClassBody {

	FieldVarList fieldVarList;
	
	ClassBody(FieldVarList fvl){
		
		fieldVarList = fvl;
		
	}
	
	void printParseTree(String indent) {
		// TODO Auto-generated method stub
		
		IO.display(" <class body>");
		fieldVarList.printParseTree(indent);
		
	}

}
