public class Main {
    public static void main(String[] args) {
        String pat = args[0];
        String txt = args[1];
        KMP kmp = new KMP(pat);
        System.out.println("text: " + txt);
        int offset = kmp.search(txt);
        int numOfEntries = kmp.count(txt);
        System.out.print("patt: ");
        for (int i = 0; i < offset; i++)
            System.out.print(" ");
        System.out.println(pat);
        System.out.println("number of entries: " + numOfEntries);
    }
}
