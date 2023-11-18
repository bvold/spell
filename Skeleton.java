// From Automatic Spelling Correction in Scientific and Scholarly Text
// Joseph J. Pollock and Antonio Zamora
// Communications of the ACM April 1984  Volume 27 Number 4 p. 358-368
// Might be possible to speed things up a bit by going through alphabe
// and checking for existence through indexOf() method  (faster?)
public class Skeleton {
    public String translate(String word) {
        // Create a new copy, and make sure the chars are correct
        // Perhaps we want to create a second index that is the length
        // of the original word and store whether or not each letter is
        // capitalized or not.  This would be an array of boolean for each
        // character position.  What is not clear is that if there are capitalized
        // letters in the middle of the string, if a replacement word is a
        // different length, how does one determine the correct upper/lower case
        // combination.
        StringBuffer temp = new StringBuffer(word.toLowerCase());
        int len = word.length();
        
        switch (len) {
            // If there is a zero-length word, this is probably an error, but it
            // could probably be ignored (which is sort of what this does).  It is
            // unclear as to what would happen if this were to return a "null"
            // character.
            case 0:
                return ""; // Or should this be null?
                // If this is a 1-char word, we probably assume that it is correct.
                // Ideally, this should probably be configurable in an application.
            case 1:
                return temp.toString();
        }
        
        // Consonants
        StringBuffer c = new StringBuffer();
        // Vowels
        StringBuffer v = new StringBuffer();
        // Whether we found it or not
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
                case 'u':
                case 'y': {
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
                        }
                    }
                    if (found == false) {
                        c.append(tempchar);
                    }
                    //System.out.println("c = "+c);
                }
            }
        }
        
        //System.out.println("c = "+c+",v = "+v);
        return new String(c.toString() + v.toString());
    }
    public static void main(String argv[]) {
        Skeleton test = new Skeleton();
        System.out.println("Should be: CHMGNEOI");
        System.out.println("       is: " + test.translate("CHEMOGENIC"));
        System.out.println("Should be: CHMGNTEOAI");
        System.out.println("       is: " + test.translate("CHEMOMAGNETIC"));
        System.out.println("Should be: CHMLEA");
        System.out.println("       is: " + test.translate("CHEMCAL"));
        System.out.println("Should be: CHMLEIA");
        System.out.println("       is: " + test.translate("CHEMCIAL"));
        System.out.println("Should be: CHMLEIA");
        System.out.println("       is: " + test.translate("CHEMICAL"));
        System.out.println("Should be: CHMLEIA");
        System.out.println("       is: " + test.translate("CHEMICIAL"));
        System.out.println("Should be: CHMLIA");
        System.out.println("       is: " + test.translate("CHIMICAL"));
    }
}
