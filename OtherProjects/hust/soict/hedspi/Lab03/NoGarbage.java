public class NoGarbage {
    public static void main(String[] args) {
        StringBuilder sb = new StringBuilder();
        
        // Simulating reading a large file and concatenating its contents
        for (int i = 0; i < 100000; i++) {
            sb.append("This is a line of text that will be concatenated. ");
        }
        
        String result = sb.toString();
        System.out.println("Concatenation completed without excessive garbage.");
    }
}