/**

This class is a top-down, recursive-descent parser for the following syntactic categories:

<program> --> <class def list> <fun def list>
<class def list> --> epsilon | <class def> <class def list>
<class def> --> "class" <class name> <class body>
<class name> --> <id>
<class body> --> "{" <field var list> "}"
<field var list> --> epsilon | <field var> <field var list>
<field var> --> <id>
<fun def list> --> epsilon | <fun def> <fun def list>
<fun def> --> "(" <header> <exp> ")"
<header> --> "(" <fun name> <parameter list> ")"
<fun name> --> <id>
<parameter list> --> epsilon | <parameter> <parameter list>
<parameter> --> <id>
<exp> --> <id> | <int> | <float> | <floatE> | "null" | "(" <fun exp> ")"
<fun exp> --> <multi argument exp> | <cond> | <not>
<multi argument exp> --> <fun call> | <arith exp> | <bool exp> | <comp exp>
<fun call> --> <fun name> <exp list>
<arith exp> --> <arith op> <exp list>
<bool exp> --> <bool op> <exp list>
<comp exp> --> <comp op> <exp list>
<exp list> --> epsilon | <exp> <exp list>
<cond> --> "if" <exp> <exp> <exp>
<not> --> ! <exp>
<arith op> --> + | - | * | /
<bool op> --> "|" | &
<comp op> --> "<" | "<=" | ">" | ">=" | "="

Note: "epsilon" denotes the empty string.
 
The definitions of the tokens are given in the lexical analyzer class file "LexAnalyzer.java". 

The following variables/functions of "IO"/"LexAnalyzer" classes are used:

static void display(String s)
static void displayln(String s)
static void setIO(String inFile, String outFile)
static void closeIO()

static void setLex()
static String t // holds an extracted token
static State state // the current state of the finite automaton
static int getToken() // extracts the next token

An explicit parse tree is constructed in the form of nested class objects.

**/


public abstract class Parser extends LexAnalyzer
{
	static boolean syntaxErrorFound = false;


	public static Program program()
	
	// <program> --> <class def list> <fun def list>

	{
		ClassDefList classDefList = classDefList();
		FunDefList funDefList = funDefList();
		return new Program(classDefList, funDefList);
	}

	public static ClassDefList classDefList()
	
	// <class def list> --> epsilon | <class def> <class def list>
		
	{		
		if ( state == State.Keyword_class )
		{
			ClassDef classDef = classDef();
			ClassDefList classDefList = classDefList();
			return new ClassDefList(classDef, classDefList);
		}
		else  // <class def list> is epsilon
			return null;
	}

	public static ClassDef classDef()

	// <class def> --> "class" <class name> <class body>

	{
		getToken(); // flush "class"
		ClassName className = className();
		ClassBody classBody = classBody();
		return new ClassDef(className, classBody);
	}

	public static ClassName className()

	// <class name> --> <id>

	{
		Id id = id();
		return new ClassName(id);
	}

	public static Id id()
	
	{
		if ( state == State.Id )
		{
			String id = t;
			getToken();
			return new Id(id);
		}
		else
		{
			errorMsg(1);
			return null;
		}
	}

	public static ClassBody classBody()

	// <class body> --> "{" <field var list> "}"

	{
		if ( state == State.LBrace )
		{
			getToken();
			FieldVarList fieldVarList = fieldVarList();
			if ( state == State.RBrace )
			{
				getToken();
				return new ClassBody(fieldVarList);
			}
			else
			{
				errorMsg(2);
				return null;
			}
		}
		else
		{
			errorMsg(3);
			return null;
		}
	}

	public static FieldVarList fieldVarList()
	
	// <field var list> --> epsilon | <field var> <field var list>
		
	{		
		if ( state == State.Id )
		{
			FieldVar fieldVar = fieldVar();
			FieldVarList fieldVarList = fieldVarList();
			return new FieldVarList(fieldVar, fieldVarList);
		}
		else  // <field var list> is epsilon
			return null;
	}

	public static FieldVar fieldVar()

	// <field var> --> <id>

	{
		Id id = id();
		return new FieldVar(id);
	}

	public static FunDefList funDefList()
	
	// <fun def list> --> epsilon | <fun def> <fun def list>
		
	{		
		if ( state == State.LParen )
		{
			FunDef funDef = funDef();
			FunDefList funDefList = funDefList();
			return new FunDefList(funDef, funDefList);
		}
		else // <class def list> is epsilon
			return null;
	}

	public static FunDef funDef()

	// <fun def> --> "(" <header> <exp> ")"

	{
		if ( state == State.LParen )
		{
			getToken();
			Header header = header();
			Exp exp = exp();
			if ( state == State.RParen )
			{
				getToken();
				return new FunDef(header, exp);
			}
			else
			{
				errorMsg(4);
				return null;
			}
		}
		else 
		{
			errorMsg(5);
			return null;
		}
	}

	public static Header header()

	// <header> --> "(" <fun name> <parameter list> ")" 

	{
		if ( state == State.LParen )
		{
			getToken();
			FunName funName = funName();
			ParameterList parameterList = parameterList();
			if ( state == State.RParen )
			{
				getToken();
				return new Header(funName, parameterList);
			}
			else
			{
				errorMsg(4);
				return null;
			}
		}
		else
		{
			errorMsg(5);
			return null;
		}
	}

	public static FunName funName()

	// <fun name> --> <id>

	{
		Id id = id();
		return new FunName(id);
	}

	public static ParameterList parameterList()
	
	// <parameter list> --> epsilon | <parameter> <parameter list>
		
	{		
		if ( state == State.Id )
		{
			Parameter parameter = parameter();
			ParameterList parameterList = parameterList();
			return new ParameterList(parameter, parameterList);
		}
		else  // <parameter list> is epsilon
			return null;
	}

	public static Parameter parameter()

	// <parameter> --> <id>

	{
		Id id = id();
		return new Parameter(id);
	}

	public static Exp exp()

	// <exp> --> <id> | <int> | <float> | <floatE> | "null" | "(" <fun exp> ")"
	
	{
		switch ( state )
		{
			case Id:
				return id();
				
			case Int:
				Int intElem = new Int(Integer.parseInt(t));
				getToken();
				return intElem;
				
			case Float: case FloatE:
				Floatp floatElem = new Floatp(Float.parseFloat(t));
				getToken();
				return floatElem;

			case Keyword_null:
				getToken();
				return Null.nullVal;

			case LParen:
				getToken();
				FunExp funExp = funExp();
				if ( state == State.RParen )
				{
					getToken();
					return funExp;
				}
				else
				{
					errorMsg(4);
					return null;
				}
			
			default:
				errorMsg(6);
				return null;
		}
	}

	public static FunExp funExp()

	// <fun exp> --> <multi argument exp> | <cond> | <not>

	{
		if ( state.compareTo(State.Id) <= 0 && state != State.Not )
			return multiArgumentExp();
		else if ( state == State.Keyword_if )
			return cond();
		else if ( state == State.Not )
			return not();
		else
		{
			errorMsg(7);
			return null;
		}
	}

	public static MultiArgumentExp multiArgumentExp()

	// <multi argument exp> --> <fun call> | <arith exp> | <bool exp> | <comp exp>

	{
		switch ( state )
		{
			case Id:
				return funCall();

			case Add: case Sub: case Mul: case Div:
				return arithExp();

			case Or: case And:
				return boolExp();

			default: // case Lt: case Le: case Gt: case Ge: case Eq:
				return compExp();
		}
	}

	public static FunCall funCall()

	// <fun call> --> <fun name> <exp list>

	{
		FunName funName = funName();
		ExpList expList = expList();
		return new FunCall(funName, expList);
	}

	public static ArithExp arithExp()

	// <arith exp> --> <arith op> <exp list>
	// <arith op> --> + | - | * | /

	{
		State arithOp = state;
		getToken();
		ExpList expList = expList();

		switch ( arithOp )
		{
			case Add: return new Add(expList);
			case Mul: return new Mul(expList);
			case Sub: return new Sub(expList);
			default:  return new Div(expList); // case Div:
		}
	}

	public static BoolExp boolExp()

	// <bool exp> --> <bool op> <exp list>
	// <bool op> --> "|" | &

	{
		State boolOp = state;
		getToken();
		ExpList expList = expList();

		switch ( boolOp )
		{
			case Or: return new Or(expList);
			default: return new And(expList); // case And:
		}
	}

	public static CompExp compExp()

	// <comp exp> --> <comp op> <exp list>
	// <comp op> --> "<" | "<=" | ">" | ">=" | "="

	{
		State compOp = state;
		getToken();
		ExpList expList = expList();

		switch ( compOp )
		{
			case Lt: return new Lt(expList);
			case Le: return new Le(expList);
			case Gt: return new Gt(expList);
			case Ge: return new Ge(expList);
			default: return new Eq(expList); // case Eq:
		}
	}

	public static Cond cond()

	// <cond> --> "if" <exp> <exp> <exp>

	{
		getToken(); // flush "if"
		Exp exp1 = exp();
		Exp exp2 = exp();
		Exp exp3 = exp();
		return new Cond(exp1, exp2, exp3);
	}

	public static Not not()

	// <not> --> ! <exp>

	{
		getToken(); // flush "!"
		Exp exp = exp();
		return new Not(exp);
	}

	public static ExpList expList()

	// <exp list> --> epsilon | <exp> <exp list>

	{
		switch ( state )
		{
			case Id: case Int: case Float: case FloatE: case Keyword_null: case LParen:
				Exp exp = exp();
				ExpList expList = expList();
				return new ExpList(exp, expList);
			
			default: // <exp list> is epsilon
				return null;
		}
	}

	public static void errorMsg(int i)
	{
		syntaxErrorFound = true;

		display(t + " : Syntax Error, unexpected symbol where");

		switch ( i )
		{
		case 1: displayln(" identifier expected"); return;
		case 2: displayln(" } expected"); return;
		case 3: displayln(" { expected"); return;
		case 4: displayln(" ) expected"); return;
		case 5:	displayln(" ( expected"); return;
		case 6: displayln(" identifier, integer, float, null, or ( expected"); return;
		case 7: displayln(" identifier, arithmetic/boolean/comparison operator, if, or ! expected"); return;
		}
	}

	public static void main(String argv[])
	{
		// argv[0]: input file containing a program
		// argv[1]: output file displaying error messages, if any 

		setIO( argv[0], argv[1] );
		setLex();

		getToken();
		Program program = program(); // build a parse tree
		if ( ! t.isEmpty() )
			displayln(t + " : Syntax Error, unexpected symbol");

		closeIO();
	}
}