
class ClassDefList {

	ClassDef classDef;
	ClassDefList classDefList;
	
	ClassDefList(ClassDef c, ClassDefList cl){
		
		classDef = c;
		classDefList = cl;
		
	}
	
	void printParseTree(String indent){
		
		ClassDefList p = this;
		do{
			p.classDef.printParseTree(indent);
			p = p.classDefList;
		} while(p != null);
		
	}
	
}
