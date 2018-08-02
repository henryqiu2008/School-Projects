
class FunDefList {
		
	FunDef funDef;
	FunDefList funDefList;
	
	FunDefList(FunDef f, FunDefList fl){
		
		funDef = f;
		funDefList = fl;
		
	}
	
	void printParseTree(String indent){
		
		FunDefList p = this;

		do
		{
			p.funDef.printParseTree(indent);
			p = p.funDefList;
		} while ( p != null );
		
		//funDef.printParseTree(indent);
		//funDefList.printParseTree(indent);
	}
	
}
