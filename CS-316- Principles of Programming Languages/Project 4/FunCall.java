import java.util.*;

class FunCall extends MultiArgumentExp
{
	FunName funName;

	// Inherits "ExpList expList" from MultiArgumentExp

	FunCall(FunName f, ExpList e)
	{
		funName = f;
		expList = e;
	}

	Val Eval(HashMap<String,Val> state)
	{
		String funNameId = funName.id.id;

		FunData funData = SemanticChecker.functionSymbolTable.get(funNameId);
		if ( funData == null){
			System.out.println( "Error: undefined function: " + funNameId );
			return null;
		}
		else if ( funData != null ) // funName is a user-defined function
		{
			LinkedList<String> paramList = funData.parameterList;
			ExpList eList = expList;
			HashMap<String,Val> newState = new HashMap<String,Val>();
			HashMap<String,Val> newObject = new HashMap<String, Val>();
			HashMap<String,Val> newSame = new HashMap<String, Val>();

			for ( String parameter: paramList ) // form a new function-call state "newState"
			{
				if ( eList == null ) //this will evaluate all the lines of input that go in
					newState.put(parameter, null);
				else
				{
					Val eVal = eList.firstExp().Eval(state);
					if ( eVal == null )
						return null;
					newState.put(parameter, eVal); // "parameter" is bound to eVal
					eList = eList.tailExpList();
				}
				if(SemanticChecker.classSymbolTable.containsKey(funData)){ //f is identical to a defined class name    
																		   // f is the constructor of this class 
					Val eVal = eList.firstExp().Eval(state); //if f is identical to class name, return ClassObj(parameter(f), newObject(hashmap of evaluated e's keyed to parameter))
					if( eVal == null)
						return null;
					newObject.put(parameter, eVal); // parameter is bound to eVal
					eList = eList.tailExpList();
					return new ClassObj(parameter, newObject); //puts the className(parameter) and the hashmap(newObject) into ClassObj.java
					
				}
				else if(SemanticChecker.functionSymbolTable.containsKey(funData)){ //is a class object and f is identical to a field name of this class    
																				   // f is a getter function of this class 
					Val eVal = eList.firstExp().Eval(state); //if class object is identical to field name, return the first value, e1
					if(eVal == null)
						return null;
					return eVal; //the field of f is the first value of eList
				}
				else{  // f is undefined 
					System.out.println( "Error: undefined function: " + funNameId );
					return null;
				}
			}
		
			return funData.bodyExp.Eval(newState);
		}

		System.out.println( "Error: undefined function: " + funNameId );
		return null;
		
	}
}