// copy a binary or text file
import java.io.*;
import java.util.Scanner;

public class CopyBytes {
    public static void main( String args[]) throws IOException {
        LZWCompression LZW = new LZWCompression();
        Scanner sc = new Scanner(System.in);
        System.out.println("java LZWCompression -c –v shortword.txt zippedFile.txt");
        System.out.println("java LZWCompression -d –v zippedFile.txt unzippedFile.txt");
        while(sc.hasNext())
        {
            String line =sc.nextLine();
            String[] arg =line.split(" ");
            if(arg[2].equals("-c"))
            {
                LZW.compress(arg[4],arg[5]);
            }
            if(arg[2].equals("-d"))
            {
                LZW.decompress(arg[4],arg[5]);
            }

        }sc.close();
    }
}
