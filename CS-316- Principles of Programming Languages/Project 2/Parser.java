import java.util.HashMap;

/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<assignment list> --> <assignment> | <assignment> <assignment list>
<assignment> --> <id> = <E> ";"
<E> --> <term> | <term> + <E> | <term> - <E>
<term> --> <primary> | <primary> * <term> | <primary> / <term>
<primary> --> <id> | <int> | <float> | <floatE> | "(" <E> ")" 

The definitions of the tokens are given in the lexical analyzer class file "LexArithArray.java". 

The following variables/functions of "IO"/"LexArithArray" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.
 
The main function will display the parse tree in linearly indented form.
Each syntactic category name labeling a node is displayed on a separate line, 
prefixed with the integer i representing the node's depth and indented by i blanks. 

**/


public abstract class Parser extends LexAnalyzer
{
	static boolean errorFound = false;
	static Null null_ = new Null();
		
	public static Program program(){
		
		// <program> --> <class def list> <fun def list>
		
		ClassDefList cdl = classDefList();
		FunDefList fdl = funDefList();
		return new Program(cdl,fdl);
		
	}
	
	public static ClassDefList classDefList(){
		
		// <class def list> --> epsilon | <class def> <class def list>
		
		ClassDef cd = classDef();
		
		if( state == State.Keyword_class ){
			
			ClassDefList cdl = classDefList();
			return new ClassDefList(cd, cdl);
			
		}
		else
			return new ClassDefList(cd, null);
	}
	
	public static ClassDef classDef(){
		
		// <class def> --> "class" <class name> <class body>
		// <class name> --> <id>
		
		if(state == State.Keyword_class){
			
			getToken();
			if( state == State.Id){
				String className = t;
				displayln(className);
				getToken();
				//if( state == State.LBrace ){
				//	getToken();
					ClassBody cb = classBody();

					return new ClassDef(className, cb);
					
				/*	if( state == State.RBrace){
						getToken();
						
					}
					else{
						errorMsg(7);
						return null;
					} */
				//}
				//else{
				//	errorMsg(6);
				//	return null;
				//}
			}
			else{
				errorMsg(5, "ClassDef");
				return null;
			}
		}
		else
			//errorMsg(8, "ClassDef");
			return null;
	}
	
	public static ClassBody classBody(){
		
		// <class body> --> "{" <field var list> "}"
		
		if( state == State.LBrace ){
			getToken();
			FieldVarList fvl = fieldVarList();
			
			if( state == State.RBrace ){
				getToken();
				return new ClassBody(fvl);
			}
			else{
				errorMsg(7, "ClassBody");
				return null;
			}
			
		}
		else{
			errorMsg(6, "ClassBody");
			return null;
		}
	}
	
	public static FieldVarList fieldVarList(){
		
		// <field var list> --> epsilon | <field var> <field var list>
		// <field var> --> <id>
		
		if( state == State.Id ){
			String fieldVar = t;
			displayln(fieldVar);
			getToken();
			FieldVarList fvl = fieldVarList();
			return new FieldVarList(fieldVar, fvl);
			
		}
		else
			return null;
	}
	
	public static FunDefList funDefList(){
		
		// <fun def list> → ε | <fun def> <fun def list> 
		
		
		FunDef fd = funDef();
		
		if( state == State.LParen ){
			
			FunDefList fdl = funDefList();
			return new FunDefList(fd, fdl);
			
		}
		else 
			return new FunDefList(fd, null);
	}
	
	public static FunDef funDef(){
		
		// <fun def> → "(" <header> <exp> ")"
		
		if( state == State.LParen ){
			
			getToken();
			Header h = header();
			Exp e = exp();
			
			if( state == State.RParen){
				getToken();
				return new FunDef(h,e);
			}
			else{
				errorMsg(1, "FunDef1");
				return null;
			}
		}
		else
			return null;
	}
	
	public static Header header(){
		
		// <header> --> "(" <fun name> <parameter list> ")"
		// <fun name> --> <id>
		
		if ( state == State.LParen){
			
			getToken();
			if( state == State.Id){
				String funName = t;
				displayln(funName);
				getToken();
				ParameterList parameterList = parameterList();
				
				if( state == State.RParen){
					getToken();
					return new Header(funName, parameterList());
				}
				else{
					errorMsg(1, "Header");
					return null;
				}
			}
			else{
				errorMsg(2, "Header");
				return null;
			}
		}
		else{
			errorMsg(2, "Header");
			return null;
		}
	}

	
	public static ParameterList parameterList(){
		
		// <parameter list> --> epsilon | <parameter> <parameter list>
		// <parameter> --> <id>
		
		if(state == State.Id){
			String parameter = t;
			getToken();
			displayln(parameter);
			ParameterList pl = parameterList();
			return new ParameterList(parameter, pl);
		}
		else
			return null;
	}
	
	public static Exp exp(){
		
		// <exp> --> <id> | <int> | <float> | <floatE> | "null" | "(" <fun exp> ")"
		
		{
			switch(state){
				case Id:
					
					ExpId id = new ExpId(t);
					getToken();
					return id;
					
				case Int:
					
					ExpInt intNum = new ExpInt(parse(t));
					getToken();
					return intNum;
				
				case Float: case FloatE:
					
					Floatf floatInstance = new Floatf(Float.parseFloat(t));
					getToken();
					return floatInstance;
					
				case Keyword_null:
					
					getToken();
					return null_;
					
				case LParen:
					
					getToken();
					FunExp funExp = funExp();
					if(state == State.RParen){
						getToken();
						return funExp;
					}
					else{
						errorMsg(3, "Exp1");
						return null;
					}
				default:
					errorMsg(10, "Exp2");
					return null;
					
					
			}
		}
	}
	
	private static int parse(String t) {
		// TODO Auto-generated method stub

		if(t.charAt(0) == '+' || t.charAt(0) == '-')
			return Integer.parseInt(t.substring(1));
		else
			return Integer.parseInt(t);
		
	}

	public static FunExp funExp(){
		
		// <fun exp> --> <multi argument exp> | <cond> | <not>
		
		switch(state){
			
		case Add: case Sub: case Mul: case Div:
		case Or: case And: case Gt: case Ge:
		case Lt: case Le: case Eq: case Id:
			return multiArgExp();
		case Keyword_if:
			return cond();
		case Not:
			return not();
		default:
			errorMsg(0, "FunExp");
			return null;
		}
			
	}
	
	public static MultiArgExp multiArgExp(){
		
		// <multi argument exp> --> <fun call> | <arith exp> | <bool exp> | <comp exp>
		
		switch(state){
		
		case Id:
			return funCall();
		
		case Add: case Sub: case Mul: case Div:
			return arithExp();
		
		case Or: case And:
			return boolExp();
			
		case Ge: case Gt: case Le: case Lt: case Eq:
			return compExp();
			
		default:
			errorMsg(11, "MultiArgExp");
			return null;
		}
	}
	
	public static FunCall funCall(){
		
		// <fun call> --> <fun name> <exp list>
		
		String funName = t;
		getToken();
		ExpList expList = expList();
		return new FunCall(funName, expList);
	}
	
	public static ArithExp arithExp(){

		// <arith exp> --> <arith op> <exp list>
		// <arith op> → + | − | * | / 
		
		switch(state){
		case Add:
			getToken();
			ExpList expList = expList();
			return new Add(expList);
		case Mul:
			getToken();
			expList = expList();
			return new Mul(expList);
		case Sub:
			getToken();
			expList = expList();
			return new Sub(expList);
		case Div:
			getToken();
			expList = expList();
			return new Div(expList);
		default:
			errorMsg(1,"ArithExp");
			return null;
		}
		
	}
	
	public static BoolExp boolExp(){

		//<bool exp> → <bool op> <exp list>
		//<bool op> → "|" | & 
		
		switch(state){
		case Or:
			getToken();
			ExpList expList = expList();
			return new Or(expList);
		case And:
			getToken();
			expList = expList();
			return new And(expList);
		default:
			errorMsg(0, "BoolExp");
			return null;
		}
		
	}
	
	public static CompExp compExp(){

		//<comp exp> → <comp op> <exp list>
		//<comp op> → "<" | "<=" | ">" | ">=" | "="
		
		switch(state){
		case Gt:
			getToken();
			ExpList expList = expList();
			return new Gt(expList);
		case Ge:
			getToken();
			expList = expList();
			return new Ge(expList);
		case Lt:
			getToken();
			expList = expList();
			return new Lt(expList);
		case Le:
			getToken();
			expList = expList();
			return new Le(expList);
		case Eq:
			getToken();
			expList = expList();
			return new Eq(expList);
		default:
			errorMsg(0, "CompExp");
			return null;
		}
		
	}
	
	public static ExpList expList(){
		
		//<exp list> → ε | <exp> <exp list>
		
		if((state.compareTo(State.Id) >= 0 && state.compareTo(State.LParen) <= 0 ) ||
			state == State.Keyword_null){
			Exp exp = exp();
			ExpList expList = expList();
			return new ExpList(exp, expList);
		}
		else
			return null;
		
	}
	
	public static Cond cond(){
		
		// <cond> --> "if" <exp> <exp> <exp>
		
		if(state == State.Keyword_if){
			getToken();
			Exp exp1 = exp();
			Exp exp2 = exp();
			Exp exp3 = exp();
			return new Cond(exp1, exp2, exp3);
		}
		return null;
	}
	
	public static Not not(){
		
		// <not> --> ! <exp>
		
		if(state == State.Not){
			getToken();
			Exp exp = exp();
			return new Not(exp);
		}
		return null;
	}
	
	
	public static void errorMsg(int i, String p)
	{
		errorFound = true;
		
		display(t + " : Syntax Error, unexpected symbol where");

		switch( i )
		{
		case 0: displayln( " " + t + " arith op, bool op, comp op, cond, or not expected in "+ p); return;
		case 1:	displayln( " " + t + " arith op or ) expected in "+ p); return;
		case 2: displayln( " " + t + " id, int, float, or ( expected in "+ p); return;
		case 3:	displayln( " " + t + " = expected  in "+ p); return;
		case 4:	displayln( " " + t + " ; expected in "+ p); return;
		case 5:	displayln( " " + t + " id expected in "+ p); return;	
		case 6: displayln( " " + t + " { expected in "+ p); return;
		case 7: displayln( " " + t + " } expected in "+ p); return;
		case 8: displayln( " " + t + " class expected in "+ p); return;
		case 9: displayln( " " + t + " id or ) expected in "+ p); return;
		case 10: displayln( " " + t + " id or int or float or floatE or null or ( expected in "+ p); return;
		case 11: displayln( " " + t + " arith op, bool op, comp op, cond, or Id in "+ p); return;
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing an assignment list
		// argv[1]: output file displaying the parse tree
		
		setIO( argv[0], argv[1] );
		setLex();

		getToken();

		//AssignmentList assignmentList = assignmentList(); // build a parse tree
		Program program = program();
		/*if ( ! t.isEmpty() )
			errorMsg(5, "main");
		else if ( ! errorFound )
			program.printParseTree(""); // print the parse tree in linearly indented form in argv[1] file */

		closeIO();
	}
}
