// Ternary Tree implementation

import java.util.*;
import java.io.*;

public class TernaryTreeExtended {
   class TernaryNode { 
     char        splitchar;
     TernaryLeaf lokid;
     TernaryLeaf eqkid;
     TernaryLeaf hikid;
   }

   class TernaryLeaf extends TernaryNode {
     String s;
     Vector v;  // Will store a vector of strings at each node.
   }

   private synchronized TernaryLeaf insert(String s) {
     s = s + "\0";  // This data structure relies on a sentinel to term. str.s
     return insert1(root, s);
   }


   private synchronized TernaryLeaf insert1(TernaryLeaf p, String s) {
     if (p == null) {
       p = new TernaryLeaf();
       p.v = new Vector(); // Initialize the vector
       p.splitchar = s.charAt(0);
       p.lokid = p.eqkid = p.hikid = null;
     }
     if (s.charAt(0) < p.splitchar) 
       p.lokid = insert1(p.lokid, s);
     else if (s.charAt(0) == p.splitchar) {
       if (s.charAt(0) == 0)
         p.v.addElement(insertstr);
       else
         p.eqkid = insert1(p.eqkid, s.substring(1));
     } 
     else
       p.hikid = insert1(p.hikid, s);
     return p;
   }

   private TernaryLeaf root = null;
   private String insertstr;

   public void loadWords(String file) {
     FileReader r;
     BufferedReader br;
     StreamTokenizer in;
     String token;
     TernaryLeaf temp;
     try {
       r = new FileReader(file);
       br = new BufferedReader(r);  // For efficiency!

/*     // This isn't any faster, just an alternative

       // read tokens by readline
       while ((token = br.readLine() ) != null) {
	 insertstr = token;
         root = insert(token);
       }   
*/

     // read tokens by StreamTokenizer class
       r = new FileReader(file);
       br = new BufferedReader(r);  // For efficiency!
       in = new StreamTokenizer(br);
       in.wordChars('&', '&'); // Add & for things like AT&T
       in.wordChars('\'', '\''); // Add "'" for things like it's
       while (in.nextToken() != StreamTokenizer.TT_EOF) {
         insertstr = in.sval;	// String to be inserted as a node at end
// System.out.println("insertstr =" + insertstr);  // Debugging only
	 root = insert(in.sval); // actually do insert, in.sval will change,
	 			 // thats why we need to store insertstr.
       }
     }
     catch (FileNotFoundException e) {
       System.err.println(e.getMessage() + "\nThe stack trace is:");
       e.printStackTrace();
     }
     catch (IOException e) {
       System.err.println(e.getMessage() + "\nThe stack trace is:");
       e.printStackTrace();
     }
   }

  public void pmsearch(String s) {
    pmsearch(root, s + '\0');
  }

  public void pmsearch(TernaryLeaf p, String s) {
    if (p == null) return;
    // nodecnt++;  // uncomment to keep track of how many nodes touched
    if ((s.charAt(0) == '.') || (s.charAt(0) < p.splitchar))
      pmsearch(p.lokid, s);
    if ((s.charAt(0) == '.') || (s.charAt(0) == p.splitchar))
      if ((p.splitchar != 0) && (s.charAt(0) != 0))
        pmsearch(p.eqkid, s.substring(1));
    if ((s.charAt(0) == 0) && (p.splitchar == 0))
      System.out.println(p.v); // print it out
      //srcharr[srchtop++] = p.v;  // save it off
    if ((s.charAt(0) == '.') || (s.charAt(0) > p.splitchar))
      pmsearch(p.hikid, s);
  }
   

   public boolean rsearch(String s) {
     return rsearch(root, s + "\0");
   }


   public boolean contains(StringBuffer s) {
	   System.out.println("Trying to find: " + s);
     return rsearch(s.toString());
   }

   public boolean contains(String s) {
	   System.out.println("Trying to find: " + s);
     return rsearch(root, s + "\0");
   }

   public boolean rsearch(TernaryLeaf p, String s) {
     if  (p == null) return false;
     if (s.charAt(0) < p.splitchar)
       return rsearch(p.lokid, s);
     else if (s.charAt(0) > p.splitchar)
       return rsearch(p.hikid, s);
     else {
       if (s.charAt(0) == 0)
         return true;
       return rsearch(p.eqkid, s.substring(1)); 
     }
   }

   public void nearsearch(String s, int d) {
     s = s + '\0';
     nearsearch(root, s, d);
   }

   public void nearsearch(TernaryLeaf p, String s, int d) {
     if ((p == null) || (d < 0)) return;
     if ((d > 0) || (s.charAt(0) < p.splitchar))
       nearsearch(p.lokid, s, d);
     if (p.splitchar == 0) {
       if (s.length() <= d)
         // srcharr[srchtop++] = p.s; // to store in array
         System.out.println(p.v); // to print out
     } else
       nearsearch(p.eqkid, (s.charAt(0) == 0) ? s.substring(1) : s,
         (s.charAt(0) == p.splitchar) ? d : d-1);
     if ((d > 0) || (s.charAt(0) > p.splitchar))
       nearsearch(p.hikid, s, d);
   }

   public void traverse() {
     traverse(root);
   }

   public void traverse(TernaryLeaf p) {
     if (p == null) return;
     traverse(p.lokid);
     if (p.splitchar != '\0')
       traverse(p.eqkid);
     else
       System.out.println(p.v);
     traverse(p.hikid);
   }

   public void rtraverse() {
     rtraverse(root);
   }

   public void rtraverse(TernaryLeaf p) {
		 if (p == null) return;
		 rtraverse(p.hikid);
		 if (p.splitchar != '\0')
			 rtraverse(p.eqkid);
		 else
			 System.out.println(p.v);
		 rtraverse(p.lokid);
   }

   public static void main (String argv[]) {
     TernaryTreeExtended ttb = new TernaryTreeExtended();
     //ttb.loadWords("word");  // testing only
     //ttb.loadWords("words");  // testing only
     ttb.loadWords("/usr/dict/words");  // The real thing!
     System.out.println("It's there? : " + ttb.rsearch("abandon"));
     System.out.println("It's there? : " + ttb.rsearch("abdoning"));
     ttb.traverse();  // works!
     //ttb.rtraverse();  // works!
     //ttb.pmsearch("a.an.on");  // works!
     //ttb.nearsearch("abandom", 2);  // works!
   }
}
