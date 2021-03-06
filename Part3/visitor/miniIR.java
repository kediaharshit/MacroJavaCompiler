//
// Generated by JTB 1.3.2
//

package visitor;
import syntaxtree.*;
import java.util.*;

/**
 * Provides default methods which visit each node in the tree in depth-first
 * order.  Your visitors may extend this class.
 */
public class miniIR<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public Hashtable <String, Vector> myclasses;
	public Hashtable <String, Vector> classfuncs;
	public Hashtable <String, String> famtree;
	public Hashtable <String, Integer> fnargs;
	public Hashtable <String, Hashtable<String, String>> myfuncs;
	public int count=20;
	public int label = 1;
	public String current = "";
	public String IR= "";
	public int methodflag = 0;
	public int lhs = 0;
	public int rhs = 0;
	int arrlookup = 0;
	String currvar = "";
   public R visit(NodeList n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeListOptional n, A argu) {
      if ( n.present() ) {
         R _ret=null;
         int _count=0;
         for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
            e.nextElement().accept(this,argu);
            _count++;
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
         return n.node.accept(this,argu);
      else
         return null;
   }

   public R visit(NodeSequence n, A argu) {
      R _ret=null;
      int _count=0;
      for ( Enumeration<Node> e = n.elements(); e.hasMoreElements(); ) {
         e.nextElement().accept(this,argu);
         _count++;
      }
      return _ret;
   }

   public R visit(NodeToken n, A argu) { return (R) n.tokenImage; }

   //
   // User-generated visitor methods below
   //

   public String newvar()
   {
	   count++;
	   return ("TEMP " + Integer.toString(count));
   }
   public String newlabel()
   {
	   label++;
	   return ("L"+ Integer.toString(label));
   }
   /**
    * f0 -> MainClass()
    * f1 -> ( TypeDeclaration() )*
    * f2 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
      
      build_table prs = (build_table) argu;
      
      myclasses = (Hashtable<String, Vector>) prs.myclasses.clone();
      classfuncs = (Hashtable<String, Vector>) prs.classfuncs.clone();
      famtree = (Hashtable<String, String>) prs.famtree.clone();
      fnargs = (Hashtable<String, Integer>) prs.fnargs.clone();
      myfuncs = (Hashtable<String, Hashtable<String, String>>) prs.myfuncs.clone();
//      System.out.println(myclasses);
//      System.out.println(classfuncs);
//      System.out.println(famtree);
//      System.out.println(fnargs);
//      System.out.println(myfuncs);
      argu = (A) "";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> "public"
    * f4 -> "static"
    * f5 -> "void"
    * f6 -> "main"
    * f7 -> "("
    * f8 -> "String"
    * f9 -> "["
    * f10 -> "]"
    * f11 -> Identifier()
    * f12 -> ")"
    * f13 -> "{"
    * f14 -> PrintStatement()
    * f15 -> "}"
    * f16 -> "}"
    */
   public R visit(MainClass n, A argu) {
      R _ret=null;
      
      IR = IR + "MAIN\n";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      n.f13.accept(this, argu);
      n.f14.accept(this, argu);
      n.f15.accept(this, argu);
      n.f16.accept(this, argu);
      IR = IR + "END\n\n\n";
      return _ret;
   }

   /**
    * f0 -> ClassDeclaration()
    *       | ClassExtendsDeclaration()
    */
   public R visit(TypeDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "{"
    * f3 -> ( VarDeclaration() )*
    * f4 -> ( MethodDeclaration() )*
    * f5 -> "}"
    */
   public R visit(ClassDeclaration n, A argu) {
      R _ret=null;
      
      n.f0.accept(this, argu);
      String cname = (String) n.f1.accept(this, argu);
      argu = (A) cname;
      current = cname;
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      current = "";
      return _ret;
   }

   /**
    * f0 -> "class"
    * f1 -> Identifier()
    * f2 -> "extends"
    * f3 -> Identifier()
    * f4 -> "{"
    * f5 -> ( VarDeclaration() )*
    * f6 -> ( MethodDeclaration() )*
    * f7 -> "}"
    */
   public R visit(ClassExtendsDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String cname = (String) n.f1.accept(this, argu);
      argu = (A) cname;
      current = cname;
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      current = "";
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    * f2 -> ";"
    */
   public R visit(VarDeclaration n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "public"
    * f1 -> Type()
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( FormalParameterList() )?
    * f5 -> ")"
    * f6 -> "{"
    * f7 -> ( VarDeclaration() )*
    * f8 -> ( Statement() )*
    * f9 -> "return"
    * f10 -> Expression()
    * f11 -> ";"
    * f12 -> "}"
    */
   public R visit(MethodDeclaration n, A argu) {
      R _ret=null;
      String cname = (String) argu;
//      current = cname;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      String fname = (String) n.f2.accept(this, argu);
      String func = (fname + "_" + cname);
      
      IR += func + " [ " + fnargs.get(func) +" ]\n" + "BEGIN\n";
      
      argu = (A) func;
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      n.f6.accept(this, argu);
      n.f7.accept(this, argu);
      
//      System.out.println("in methods Statements start:");
      methodflag = 1;
      n.f8.accept(this, argu);
      n.f9.accept(this, argu);
      
      IR += "RETURN\n ";
      
      n.f10.accept(this, argu);
      n.f11.accept(this, argu);
      n.f12.accept(this, argu);
      methodflag = 0;
      IR += "\nEND\n\n";
      return _ret;
   }

   /**
    * f0 -> FormalParameter()
    * f1 -> ( FormalParameterRest() )*
    */
   public R visit(FormalParameterList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Type()
    * f1 -> Identifier()
    */
   public R visit(FormalParameter n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> FormalParameter()
    */
   public R visit(FormalParameterRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ArrayType()
    *       | BooleanType()
    *       | IntegerType()
    *       | Identifier()
    */
   public R visit(Type n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    * f1 -> "["
    * f2 -> "]"
    */
   public R visit(ArrayType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "boolean"
    */
   public R visit(BooleanType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "int"
    */
   public R visit(IntegerType n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Block()
    *       | AssignmentStatement()
    *       | ArrayAssignmentStatement()
    *       | IfStatement()
    *       | WhileStatement()
    *       | PrintStatement()
    */
   public R visit(Statement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "{"
    * f1 -> ( Statement() )*
    * f2 -> "}"
    */
   public R visit(Block n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "="
    * f2 -> Expression()
    * f3 -> ";"
    */
   public R visit(AssignmentStatement n, A argu) {
      R _ret=null;
      lhs = 1;
      IR += "\tMOVE ";
      String id = (String) n.f0.accept(this, argu);
//      if (id==null)
//    	  id = "";
//      int spcount=0;
//      int l = id.length();
//      for(int i=0; i<l; i++)
//      {
//    	  if( id.charAt(i) == ' ')
//    		  spcount++;
//      }
//      if (spcount == 2)
//      {
//    	  IR += "\tHSTORE " + id;
//      }
//      else
//      {
//    	  IR +=  "\tMOVE " + id;
//      }
      
      lhs = 0;
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      IR += "\n";
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> "["
    * f2 -> Expression()
    * f3 -> "]"
    * f4 -> "="
    * f5 -> Expression()
    * f6 -> ";"
    */
   public R visit(ArrayAssignmentStatement n, A argu) {
      R _ret=null;
      lhs = 1;
      String t1 = currvar;
      String t2 = newvar();
//      IR += "HLOAD " + t1 + " ";
      n.f0.accept(this, argu);
//      IR += "\n";
      n.f1.accept(this, argu);
      IR += "MOVE " + t2 + " ";
      lhs = 0;
      n.f2.accept(this, argu);
      IR += "\n";
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      IR +=  "MOVE "  + t2 + " TIMES "  + "PLUS " + t2 + " 1" + " 4\n"  ;
      IR += "MOVE "+ t1  + " PLUS " + t1 + " " + t2 + "\n";
      IR += "HSTORE " + t1 + " 0"  + " ";
      n.f5.accept(this, argu);
      IR += "\n";
      n.f6.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> IfthenElseStatement()
    *       | IfthenStatement()
    */
   public R visit(IfStatement n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(IfthenStatement n, A argu) {
      R _ret=null;
      String l1 = newlabel();
      IR += "CJUMP ";
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      
      IR += " "  + l1 + "\n";
      
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      IR += "\n" + l1 + " NOOP\n";
      return _ret;
   }

   /**
    * f0 -> "if"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    * f5 -> "else"
    * f6 -> Statement()
    */
   public R visit(IfthenElseStatement n, A argu) {
      R _ret=null;
      String l1 = newlabel();
      String l2 = newlabel();
      IR += "\tCJUMP ";
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      IR += " " + l1 + "\n";
      
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      IR += "\tJUMP "  + l2 + "\n";
      IR += "\n" +l1 + " NOOP\n";
      n.f6.accept(this, argu);
      
      IR +="\n" + l2 + " NOOP\n";
      return _ret;
   }

   /**
    * f0 -> "while"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> Statement()
    */
   public R visit(WhileStatement n, A argu) {
      R _ret=null;
      String l1= newlabel();
      String l2= newlabel();
      
      IR += l1 + " NOOP\n";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      IR += "CJUMP " ;
      n.f2.accept(this, argu);
      IR += l2 + "\n";
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      IR += "JUMP " + l1 + "\n";
      IR +=  "\n" + l2 + " NOOP\n";
      return _ret;
   }

   /**
    * f0 -> "System.out.println"
    * f1 -> "("
    * f2 -> Expression()
    * f3 -> ")"
    * f4 -> ";"
    */
   public R visit(PrintStatement n, A argu) {
      R _ret=null;
      
      IR += "\tPRINT ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      IR += "\n";
      return _ret;
   }

   /**
    * f0 -> OrExpression()
    *       | AndExpression()
    *       | CompareExpression()
    *       | neqExpression()
    *       | PlusExpression()
    *       | MinusExpression()
    *       | TimesExpression()
    *       | DivExpression()
    *       | ArrayLookup()
    *       | ArrayLength()
    *       | MessageSend()
    *       | PrimaryExpression()
    */
   public R visit(Expression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "&&"
    * f2 -> PrimaryExpression()
    */
   public R visit(AndExpression n, A argu) {
      R _ret=null;
      IR +=  " TIMES ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "||"
    * f2 -> PrimaryExpression()
    */
   public R visit(OrExpression n, A argu) {
      R _ret=null;
      IR += " PLUS "; 
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "<="
    * f2 -> PrimaryExpression()
    */
   public R visit(CompareExpression n, A argu) {
      R _ret=null;
      IR += "LE ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "!="
    * f2 -> PrimaryExpression()
    */
   public R visit(neqExpression n, A argu) {
      R _ret=null;
      IR += "NE ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "+"
    * f2 -> PrimaryExpression()
    */
   public R visit(PlusExpression n, A argu) {
      R _ret=null;
      IR += "PLUS ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "-"
    * f2 -> PrimaryExpression()
    */
   public R visit(MinusExpression n, A argu) {
      R _ret=null;
      IR += "MINUS ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "*"
    * f2 -> PrimaryExpression()
    */
   public R visit(TimesExpression n, A argu) {
//	   System.out.println("mult");
      R _ret=null;
      IR += "TIMES ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "/"
    * f2 -> PrimaryExpression()
    */
   public R visit(DivExpression n, A argu) {
      R _ret=null;
      IR += "DIV";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "["
    * f2 -> PrimaryExpression()
    * f3 -> "]"
    */
   public R visit(ArrayLookup n, A argu) {
      R _ret=null;
      arrlookup = 1;
      String t1 = newvar();
      String t2 = newvar();
      IR += "\nBEGIN\n";
      IR += "MOVE " + t1 + " ";
      n.f0.accept(this, argu);
      IR += "\n";
      arrlookup = 0;
      n.f1.accept(this, argu);
      
      IR += "HLOAD " + t2 + " PLUS " + t1  +  " TIMES " + " PLUS ";
      n.f2.accept(this, argu);
      IR += " 1 4 0\n";
      n.f3.accept(this, argu);
      IR += "RETURN\n " + t2+ "\n";
	  IR += "END\n";
	  
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> "length"
    */
   public R visit(ArrayLength n, A argu) {
      R _ret=null;
      String t1 = newvar();
      IR += "BEGIN ";
      IR += " HLOAD " + t1 + " ";
      n.f0.accept(this, argu);
      IR += " 0\n";
      IR += "MOVE " + t1 + " " + " DIV "  + t1 + " 4";
      
      
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      IR += "\nRETURN " + t1 + "\nEND\n";
      return _ret;
   }

   /**
    * f0 -> PrimaryExpression()
    * f1 -> "."
    * f2 -> Identifier()
    * f3 -> "("
    * f4 -> ( ExpressionList() )?
    * f5 -> ")"
    */
   public R visit(MessageSend n, A argu) {
      R _ret=null;
      String cname = (String) argu;
      IR += "CALL \nBEGIN \n"; 
      String t1 = newvar(); //stores class details
      String t2 = newvar(); //stores function table pointer
      String t3 = newvar(); //stores function label
      
      IR += "\tMOVE " + t1 ;
      String tempcname = current;
      
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      
      int tempflag = methodflag;
      methodflag = 0;
      String func = (String) n.f2.accept(this, argu);
      methodflag = tempflag;
      if(methodflag == 0)
    	  func += "_" + current;
      else
    	  func += "_" + current; 
      
//      System.out.println("msg_send:" +func);
      Vector fns = (Vector) classfuncs.get(current).clone();
      int pos = 0;
      while(pos<fns.size())
      {
    	  if( func.equals(fns.get(pos)) )
    	  {
    		  break;
    	  }
    	  pos++;
      }
      IR += "\tHLOAD " + t2 +  " " + t1 +" 0\n";
      IR += "\tHLOAD " + t3 + " " + t2 + " " + Integer.toString(pos*4) +"\n"; 
      IR += "RETURN \n" + t3 +"\n";
      n.f3.accept(this, argu);
      
      current = tempcname;
      
      IR += "END\n";
      IR +=  "( "+ t1 + " ";
      
      n.f4.accept(this, argu);
      n.f5.accept(this, argu);
      IR +=  ")\n";
      
      return _ret;
   }

   /**
    * f0 -> Expression()
    * f1 -> ( ExpressionRest() )*
    */
   public R visit(ExpressionList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Expression()
    */
   public R visit(ExpressionRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> IntegerLiteral()
    *       | TrueLiteral()
    *       | FalseLiteral()
    *       | Identifier()
    *       | ThisExpression()
    *       | ArrayAllocationExpression()
    *       | AllocationExpression()
    *       | NotExpression()
    *       | BracketExpression()
    */
   public R visit(PrimaryExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      
      String i = (String) n.f0.accept(this, argu);
//      System.out.println("Found int " + n.f0.tokenImage);
      IR += i + " ";
      return _ret;
   }

   /**
    * f0 -> "true"
    */
   public R visit(TrueLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      IR += "1" + " ";
      return _ret;
   }

   /**
    * f0 -> "false"
    */
   public R visit(FalseLiteral n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      IR += "0" + " ";
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Identifier n, A argu) {
	   R _ret=null;
	   _ret = n.f0.accept(this, argu);
	  if (methodflag == 0) 
      {
    	  return _ret;
      }
      if(methodflag == 1)
      {
    	  String fname= (String) argu;
    	  String var = (String) _ret + "_" + fname;
    	  Hashtable <String, String> ht = (Hashtable<String, String>) myfuncs.get(fname).clone();
//    	  System.out.println("Searching "+var);
    	  if(ht.containsKey(var))
    	  {
    		  String t1 =  ht.get(var);
    		  String t2 = newvar();
    		  if(arrlookup == 1)
    		  {
    			  IR += "BEGIN\n" + "MOVE " + t2 + " " + t1 + "\nRETURN " + t2 + "\nEND\n";
    			  
    			  return _ret;
    		  }
	    	  
//	    	  return (R) t1;
	    	  IR += t1  + " ";
    	  }
    	  else
    	  {
//    		  System.out.println("reached");
			  Vector v = myclasses.get(current);
    		  String vname = (String) _ret + "_" + current;
    		  
    		  if( v.contains(vname))
    		  {
    			  String t1= newvar();
    			  int pos = v.indexOf(vname);
    			  if (lhs == 1)
    			  {
    				  IR += "BEGIN\n";
    				  IR += "MOVE " + t1 + " PLUS " + " TEMP 0 " + Integer.toString(pos*4 + 4) +"\n";
    				  IR += " RETURN "  + t1 + " END\n";
    				  currvar = t1;
//    				  return (R) ("TEMP 0 " +  Integer.toString(pos*4 + 4));
    			  }
    			  else if(arrlookup == 1)
    			  {
    				  IR += "BEGIN \n" + "MOVE\n" + t1 + " PLUS " + "TEMP 0 " +  Integer.toString(pos*4 + 4);
    				  IR += "\nRETURN\n" + t1 + "\nEND\n";
    			  }
    		    			  
    			  else 
    			  {
	    			  IR += "\nBEGIN\n " +" HLOAD " +  t1 + " TEMP 0 " + Integer.toString(pos*4 + 4) + "\n";
	    			  IR += "RETURN\n "+ t1 + "\nEND\n";
	    			  currvar = t1;
    			  }
    		  } 

    	  }
      }
      return (R) null;
   }

   /**
    * f0 -> "this"
    */
   public R visit(ThisExpression n, A argu) {
      R _ret=null;
      
      n.f0.accept(this, argu);
      IR += " TEMP 0 \n";
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> "int"
    * f2 -> "["
    * f3 -> Expression()
    * f4 -> "]"
    */
   public R visit(ArrayAllocationExpression n, A argu) {
      R _ret=null;
      String t1 = newvar();
      String t2 = newvar();
      IR += "BEGIN \n";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
     
      n.f2.accept(this, argu);
      IR += "MOVE " + t1 ; 
      IR += " HALLOCATE TIMES PLUS ";
      n.f3.accept(this, argu);
      IR += " 1 4 \n";
      n.f4.accept(this, argu);
      
      String l1 = newlabel();
      String l2 = newlabel();
      IR += "MOVE "  + t2  + " 4\n";
      IR += l1 + " NOOP\n" + " CJUMP LE " + t2 + " MINUS TIMES PLUS ";
      n.f3.accept(this, argu);
      IR += " 1 4 1 " + l2 + "\n";
      IR += "HSTORE PLUS " + t1 + " " + t2 + " 0 0\n";
      IR += "MOVE " + t2  + " PLUS " + t2 + " 4\n";
      IR += "JUMP " + l1 + "\n";
      IR += l2 + " NOOP\n" + " HSTORE " + t1 + " 0 TIMES ";
      n.f3.accept(this, argu);
      IR += " 4\n";
      IR += "RETURN \n" + t1 + "\n";
      IR += "END\n";
      return _ret;
   }

   /**
    * f0 -> "new"
    * f1 -> Identifier()
    * f2 -> "("
    * f3 -> ")"
    */
   public R visit(AllocationExpression n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String cname = (String) n.f1.accept(this, argu);
      
      Vector fds = (Vector) myclasses.get(cname).clone();
      Vector fns = (Vector) classfuncs.get(cname).clone();
      
      current = cname;
      
      String t1 = newvar();
      String t2 = newvar();
//      String t3 = newvar();
      
      IR += "\nBEGIN\n";
      IR += "\tMOVE " + t1 + " HALLOCATE " + Integer.toString(fns.size() * 4) + "\n";
      IR += "\tMOVE " + t2 + " HALLOCATE " + Integer.toString(fds.size()*4 + 4)+ "\n";
      Vector f = (Vector) classfuncs.get(cname).clone(); 
      int i=0;
      // put functions in t1
      while (i < fns.size())
      {
    	  IR += "\tHSTORE " + t1 + " " + Integer.toString(i*4) + " " + f.get(i) + "\n";
    	  i++;
      }
      //put fields in t2
      
      IR += "\tHSTORE " + t2 + " 0 " + t1 + "\n";
      
      Vector fd = (Vector) myclasses.get(cname);
      i=0;
      while(i<fds.size())
      {
    	  IR += "\tHSTORE " + t2 + " " + Integer.toString(i*4+4) + " 0" + "\n";
    	  i++;
      }
      IR += "RETURN \n" + t2 + "\n";
      IR += "END\n";
      
      n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "!"
    * f1 -> Expression()
    */
   public R visit(NotExpression n, A argu) {
      R _ret=null;
      String t1 = newvar();
      String l1 = newlabel();
      String l2 = newlabel();
      IR += "BEGIN\n";
      IR += "MOVE " + t1 +" ";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      IR += " CJUMP NE " + t1 + " 0 " + l2;
      IR += "\n MOVE " + t1 + " 0\n";
      IR += "JUMP "  + l1 + "\n";
      
      IR +=  l2 + " NOOP\n" + "MOVE "  + t1 + " 1\n";
      IR += l1 + " NOOP\n";
      IR += "RETURN "  + t1 + " END\n";
      return _ret;
   }

   /**
    * f0 -> "("
    * f1 -> Expression()
    * f2 -> ")"
    */
   public R visit(BracketExpression n, A argu) {
      R _ret=null;
      String t1 = newvar();
      IR += "\nBEGIN\n MOVE " + t1 + " "; 
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      IR += "\nRETURN\n " + t1 +"\n";
      IR += "END ";
      return _ret;
   }

   /**
    * f0 -> Identifier()
    * f1 -> ( IdentifierRest() )*
    */
   public R visit(IdentifierList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> ","
    * f1 -> Identifier()
    */
   public R visit(IdentifierRest n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      return _ret;
   }

}
