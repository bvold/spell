public class Omission {
  public String translate (String word) {
	  String OMIT_ORDER = new String ("rstnlchdpgmfbywvzxqkj"); // reverse of
		                                                         // omit freq.

	  StringBuffer temp = new StringBuffer(word.toLowerCase());
		int len = word.length();


//System.out.println("OMIT_ORDER=" + OMIT_ORDER);
		switch (len) {
		  case 0:
				return ""; // Or should this be null?
			case 1:
			  return temp.toString();
		}

		StringBuffer c = new StringBuffer();
		StringBuffer v = new StringBuffer();
    boolean found;
		for (int i = 0; i < len; i++) {
		  found = false;
			char tempchar = temp.charAt(i);
//System.out.println("char before switch: "+tempchar);
			switch (tempchar) {
			  case 'a':
				case 'e':
				case 'i':
				case 'o':
				case 'u': {
				  for (int j = 0; j < v.length(); j++) {
				    if (v.charAt(j) == tempchar) { 
						  found = true;
						}
					}
					if (found == false) {
					  v.append(tempchar);
					}
//System.out.println("v = "+v);
				}
				break;
				default: {
					for (int j = 0; j < c.length(); j++) {
						if (c.charAt(j) == tempchar) {
							found = true;
							break; // for efficiency
						}
					}
					if (found == false) {
						c.append(tempchar);
					}
//System.out.println("c = "+c);
				}
			}
		}

		StringBuffer nc = new StringBuffer();

//System.out.println("c = " + c); 
		for (int i = 0; i < OMIT_ORDER.length(); i++) {
//System.out.println("OMIT_ORDER = " + OMIT_ORDER.charAt(i)); 
		  if ((c.toString().indexOf(OMIT_ORDER.charAt(i))) > -1) {
//System.out.println("c = " + c); 
//System.out.println("char = " + c.toString().indexOf(OMIT_ORDER.charAt(i))); 
			  nc.append(OMIT_ORDER.charAt(i));
//System.out.println("nc = " + nc); 
			}
		}

//System.out.println("c = "+c+",v = "+v);
    // is it faster to use inverse of OMIT_ORDER and not reverse here?
	  return new String(nc.reverse() + v.toString());
	}
	public static void main (String argv[]) {
	  Omission test = new Omission();
		System.out.println("Should be: \"\"");
		System.out.println("       is: " + test.translate(""));
		System.out.println("Should be: c");
		System.out.println("       is: " + test.translate("C"));
		System.out.println("Should be: mclntsrioe");
		System.out.println("       is: " + test.translate("microelectronics"));
		System.out.println("Should be: mclntsriua");
		System.out.println("       is: " + test.translate("circumstantial"));
		System.out.println("Should be: mclntsuie");
		System.out.println("       is: " + test.translate("luminescent"));
		System.out.println("Should be: mclntuiea");
		System.out.println("       is: " + test.translate("multinucleate"));
		System.out.println("Should be: mclntuieo");
		System.out.println("       is: " + test.translate("multinucleon"));
		System.out.println("Should be: mclnue");
		System.out.println("       is: " + test.translate("cumulene"));
		System.out.println("Should be: mclnuiae");
		System.out.println("       is: " + test.translate("luminance"));
	}
}
