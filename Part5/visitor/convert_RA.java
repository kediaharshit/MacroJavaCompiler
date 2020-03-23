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
public class convert_RA<R,A> implements GJVisitor<R,A> {
   //
   // Auto class visitors--probably don't need to be overridden.
   //
	
	public Hashtable <String, Hashtable <String, String>> reg_map;
	Hashtable <String, String> r_map;
	public Hashtable <String, Integer> fnargs;
	public Hashtable <String, Integer> maxcall;
	public Hashtable <String, Integer> slots;
	public String RA = "";
	public Hashtable <String, Hashtable <String,String>> relabel;
	Hashtable <String, String> re_label;
	String curr_met = "";
	int cnt;
	int atarg=0;
	String get_reg(String tmp) {
		String r = r_map.get(tmp);
//		System.out.println("tmp-reg " + tmp + " "  + r);
		if(r.charAt(0) == 't' || r.charAt(0)== 's')
		{
			return r;
		}
		else
		{
			RA += "ALOAD" + " v0 " +  "SPILLEDARG " + r + "\n"; 
			return "v0";
		}
	}
	
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
            String tmp1 = (String) e.nextElement().accept(this,argu);
            _count++;
            if(atarg == 1)
            {
            	String r = get_reg(tmp1);
            	
            	if(_count>4)
            	{
            		RA += "PASSARG " + Integer.toString(_count-4) + " " + r + "\n";
            	}
            	else
            	{
            		RA += "MOVE a"+Integer.toString(_count-1) + " " + r + "\n";
            	}
            	
            }
         }
         return _ret;
      }
      else
         return null;
   }

   public R visit(NodeOptional n, A argu) {
      if ( n.present() )
      {
    	 String lbl = (String) n.node.accept(this,argu);
    	 
    	 RA+= re_label.get(lbl) + " ";
    	 return (R) lbl;
      }
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

   /**
    * f0 -> "MAIN"
    * f1 -> StmtList()
    * f2 -> "END"
    * f3 -> ( Procedure() )*
    * f4 -> <EOF>
    */
   public R visit(Goal n, A argu) {
      R _ret=null;
//      System.out.println(reg_map);
//      System.out.println(fnargs);
//      System.out.println(slots);
//      System.out.println(maxcall);
//      System.out.println(relabel + "\n");      
      curr_met = (String) n.f0.accept(this, argu);
      re_label = relabel.get(curr_met);
      r_map = reg_map.get(curr_met);
      int b = slots.get(curr_met);
      int c = maxcall.get(curr_met);
      RA+= curr_met + " [0] [" +b+"] "+ "[" + c + "]\n";
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      RA+= "END\n";
      n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      
      return _ret;
   }

   /**
    * f0 -> ( ( Label() )? Stmt() )*
    */
   public R visit(StmtList n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Label()
    * f1 -> "["
    * f2 -> IntegerLiteral()
    * f3 -> "]"
    * f4 -> StmtExp()
    */
   public R visit(Procedure n, A argu) {
      R _ret=null;
      curr_met = (String) n.f0.accept(this, argu);
      r_map = reg_map.get(curr_met);
      re_label = relabel.get(curr_met);
      
      n.f1.accept(this, argu);
      String narg = (String) n.f2.accept(this, argu);
      n.f3.accept(this, argu);
      int b = slots.get(curr_met);
      int c = maxcall.get(curr_met);
      RA += curr_met  + " [" + narg + "] " + "["+b +"] " + "["+c +"]" + "\n";
//      RA+= "BEGIN\n";
      int no = Integer.parseInt(narg);
      
      cnt=no;
      if(cnt>4)
      {
    	  cnt = cnt-4;
      }
      else
    	  cnt = 0;
      
      for(int i=0; i<8; i++)
      {
    	  RA += "ASTORE SPILLEDARG " + Integer.toString(i+cnt) + " s"+Integer.toString(i) + "\n"; 
      }
      
      for(int i=0; i<no; i++)
      {
    	  String tmp = "TEMP"+Integer.toString(i);
    	  String r = r_map.get(tmp);
    	  
    	  if(r.charAt(0)== 't' || r.charAt(0)== 's')
    	  {
    		  if(i<4)
    			  RA += "MOVE "  + r + " a" + Integer.toString(i)  + "\n";
    		  else
    		  {
    			  RA += "ALOAD v1 SPILLEDARG " + Integer.toString(i-4) + "\n";
    			  RA += "MOVE "  + r + " v1\n";
    		  }
    	  }
    	  else
    	  {
    		  RA += "ALOAD v1 SPILLEDARG " + Integer.toString(i-3) + "\n";
    		  RA += "ASTORE SPILLEDARG " + r + " v1\n"; 
    	  }
      }
      
      n.f4.accept(this, argu);
      
      for(int i=0; i<8; i++)
      {
    	  RA += "ALOAD " + "s"+Integer.toString(i) + " SPILLEDARG " + Integer.toString(i+cnt) + "\n"; 
      }
      RA += "END\n";
      return _ret;
   }

   /**
    * f0 -> NoOpStmt()
    *       | ErrorStmt()
    *       | CJumpStmt()
    *       | JumpStmt()
    *       | HStoreStmt()
    *       | HLoadStmt()
    *       | MoveStmt()
    *       | PrintStmt()
    */
   public R visit(Stmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "NOOP"
    */
   public R visit(NoOpStmt n, A argu) {
      R _ret=null;
      RA += "NOOP\n";
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "ERROR"
    */
   public R visit(ErrorStmt n, A argu) {
      R _ret=null;
      RA += "ERROR\n";
      n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "CJUMP"
    * f1 -> Temp()
    * f2 -> Label()
    */
   public R visit(CJumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String tmp = (String) n.f1.accept(this, argu);
      String lbl = (String) n.f2.accept(this, argu);
      
      String r = get_reg(tmp);
      
      RA += "CJUMP " + r + " " + re_label.get(lbl) + "\n"; 
      
      return _ret;
   }

   /**
    * f0 -> "JUMP"
    * f1 -> Label()
    */
   public R visit(JumpStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String lbl = (String) n.f1.accept(this, argu);
      RA+= "JUMP " + re_label.get(lbl) + "\n";
      return _ret;
   }

   /**
    * f0 -> "HSTORE"
    * f1 -> Temp()
    * f2 -> IntegerLiteral()
    * f3 -> Temp()
    */
   public R visit(HStoreStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String tmp1 = (String) n.f1.accept(this, argu);
      String off = (String) n.f2.accept(this, argu);
      String tmp2 = (String) n.f3.accept(this, argu);
      
      String pos = get_reg(tmp1);
      String val = get_reg(tmp2);
      
      RA += "HSTORE " + pos + " " +  off + " "  + val + "\n";
      
      return _ret;
   }

   /**
    * f0 -> "HLOAD"
    * f1 -> Temp()
    * f2 -> Temp()
    * f3 -> IntegerLiteral()
    */
   public R visit(HLoadStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String tmp1 = (String) n.f1.accept(this, argu);
      String tmp2 = (String) n.f2.accept(this, argu);
      String off = (String) n.f3.accept(this, argu);
      
      String pos = get_reg(tmp2);
      RA += "HLOAD v1 " + pos + " " + off + "\n";
      
      String r1 = r_map.get(tmp1);
      if(r1.charAt(0)=='t' || r1.charAt(0)=='s')
      {
    	  //r1 is a register
    	  RA += "MOVE " + r1 + " v1\n";
      }
      else
      {
    	  //not a register, store the value
    	  RA += "ASTORE SPILLEDARG " + r1 + " v1\n";
      }
      
      return _ret;
   }

   /**
    * f0 -> "MOVE"
    * f1 -> Temp()
    * f2 -> Exp()
    */
   public R visit(MoveStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String tmp1 = (String) n.f1.accept(this, argu);
      String tmp2 = (String) n.f2.accept(this, argu);
      
      String r1 = r_map.get(tmp1);
      if(r1.charAt(0)=='t' || r1.charAt(0)=='s')
      {
    	  //r1 is a register
    	  RA += "MOVE " + r1 + " " + tmp2 + "\n";
      }
      else
      {
    	  //not a register, store the value
    	  RA += "ASTORE SPILLEDARG " + r1 + " " +tmp2 + "\n";
      }
      
//      RA += "MOVE " + r1 + " " + tmp2 +  "\n";
      
      return _ret;
   }

   /**
    * f0 -> "PRINT"
    * f1 -> SimpleExp()
    */
   public R visit(PrintStmt n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String r = (String) n.f1.accept(this, argu);
      RA += "PRINT " + r + "\n";
      return _ret;
   }

   /**
    * f0 -> Call()
    *       | HAllocate()
    *       | BinOp()
    *       | SimpleExp()
    */
   public R visit(Exp n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> "BEGIN"
    * f1 -> StmtList()
    * f2 -> "RETURN"
    * f3 -> SimpleExp()
    * f4 -> "END"
    */
   public R visit(StmtExp n, A argu) {
      R _ret=null;
//      RA+= " BEGIN\n";
      n.f0.accept(this, argu);
      n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      String ret = (String) n.f3.accept(this, argu);
      n.f4.accept(this, argu);
      RA += "MOVE v0 " + ret+ "\n";
//      RA+= "RETURN v0" + "\n";
//      RA += "END\n";
      return _ret;
   }

   /**
    * f0 -> "CALL"
    * f1 -> SimpleExp()
    * f2 -> "("
    * f3 -> ( Temp() )*
    * f4 -> ")"
    */
   public R visit(Call n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String tmp1 = (String) n.f1.accept(this, argu);
      n.f2.accept(this, argu);
      
      //save registers
      for(int i=0; i<10; i++)
      {
    	  RA += "ASTORE SPILLEDARG " + Integer.toString(i+8+cnt) + " t"+Integer.toString(i) + "\n"; 
      }
      atarg = 1;
      n.f3.accept(this, argu);
      atarg = 0;
      n.f4.accept(this, argu);

      //pass arguments
      RA += "CALL " + tmp1 + "\n";
      
      //revert registers
      for(int i=0; i<10; i++)
      {
    	  RA += "ALOAD " + "t"+Integer.toString(i) + " SPILLEDARG " + Integer.toString(i+8+cnt) + "\n"; 
      }
      return (R) "v0";
   }

   /**
    * f0 -> "HALLOCATE"
    * f1 -> SimpleExp()
    */
   public R visit(HAllocate n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String s = (String) n.f1.accept(this, argu);
      RA += "MOVE v0 HALLOCATE "  + s  + "\n"; 
      return (R)("v0");
   }

   /**
    * f0 -> Operator()
    * f1 -> Temp()
    * f2 -> SimpleExp()
    */
   public R visit(BinOp n, A argu) {
      R _ret=null;
      String bop = (String) n.f0.accept(this, argu);
      String tmp1 = (String) n.f1.accept(this, argu);
      String tmp2 = (String) n.f2.accept(this, argu);
      
      String r1 = get_reg(tmp1);
      RA += "MOVE v1 "  +  r1 + "\n";
      RA += "MOVE v0 " + bop + " v1 " + tmp2 + "\n"; 
      return (R)"v0";
   }

   /**
    * f0 -> "LE"
    *       | "NE"
    *       | "PLUS"
    *       | "MINUS"
    *       | "TIMES"
    *       | "DIV"
    */
   public R visit(Operator n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> Temp()
    *       | IntegerLiteral()
    *       | Label()
    */
   public R visit(SimpleExp n, A argu) {
      R _ret=null;
      String tmp = (String) n.f0.accept(this, argu);
      if(n.f0.which == 0)
      {
    	  String r = get_reg(tmp);
//    	  RA += "MOVE v0 " + r + "\n";
    	  return (R) r;
      }
      else
      {
    	  RA += "MOVE v0 " + tmp + "\n"; 
    	  return (R) "v0";
      }
   }

   /**
    * f0 -> "TEMP"
    * f1 -> IntegerLiteral()
    */
   public R visit(Temp n, A argu) {
      R _ret=null;
      n.f0.accept(this, argu);
      String l = (String) n.f1.accept(this, argu);
      
      return (R) ("TEMP"+l);
   }

   /**
    * f0 -> <INTEGER_LITERAL>
    */
   public R visit(IntegerLiteral n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

   /**
    * f0 -> <IDENTIFIER>
    */
   public R visit(Label n, A argu) {
      R _ret=null;
      _ret = n.f0.accept(this, argu);
      return _ret;
   }

}
