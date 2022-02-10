// Some notes on Java's internal representation:
// After reading a byte (8 bits) and assigning the byte to a char variable
// (16 bits) we need to clear the uppermost 8 bits in the char since the byte
// will sign extend into the char.
public class Test {
    public static void main(String a[]) {
        byte b = -1; // b = 0xFF = 11111111 (8 bits)
        char c = (char)b; // c = 0xFFFF = 1111111111111111 (16 bits sign extension)
        c = (char)(c & 0xFF); // c = 0x00FF = 0000000011111111 (remove the extra bits)
        int t = c; // t = 0x000000FF = 0000-000011111111 (32 bits)
        System.out.println(t); // display 255
        b = (byte)224; // b = 0xFF = 11111111 (8 bits)
        System.out.println(b);
        c = (char)b; // c = 0xFFFF = 1111111111111111 (16 bits sign extension)
        c = (char)(c & 0xFF); // c = 0x00FF = 0000000011111111 (remove extra bits)
        t = c; // t = 0xFF = 000-000011111111 (32 bits)
        System.out.println(t); // displays 255
        System.out.println();
        // In the for loop below, the 16 bits in ch are not converted to an ASCII integer.
        // For example, when ch == 0000000000000000, there is no attempt to convert to ASCII 0
        // The screen may misbehave because some bit sequences are not printable.
        for(int ch = -128; ch < 127; ch++) {
            System.out.print((char)ch+"|"+ ch+"\n");
        }
        System.out.println();
        // In this for loop, however, the integer is converted to an ASCII representation.
        // For example, when smallInt == 65, two ASCII characters are generated: '6' and '5'.
        // No misbehavior will occur here.
        byte m;
        byte n;
        for(int smallInt = 0; smallInt < 4096; smallInt++) {
            n=(byte)(smallInt<<4);
            n=(byte) (n|(smallInt>>>8));
            m=(byte)(smallInt);
            //System.out.print("，" +m+"|"+n+"\n");
            int code = convertSecondInt(n,m);
            if(code != smallInt)
                System.out.println("NO");
            //System.out.println(code+"||"+smallInt);
        }

        System.out.println();
        // Place a char into a String with no conversion.
        String test = "" + (char)0;
        System.out.println("Test = " + test);
        // Place an int into a String with conversion to ASCII.
        String test2 = "" + 0;
        System.out.println("Test2 = " + test2);
        // So, String objects may hold non-printable sequences of bits.

    }
    private static int convertFirstInt(byte first, byte second)
    {
        int temp1 = (char)(first&0xff);
        int temp2 = (char)(second&0xff);
        int code = temp1*16+(temp2>>>4);
        return code;
    }
    private static int convertSecondInt(byte first, byte second)
    {
        int temp = (char)(first&0xff); // c = 0x00FF = 0000000011111111 (remove the extra bits)
        byte b = (byte)(temp<<4);
        int temp1 = (char)(b&0xff);
        int temp2 = (char)(second&0xff);
        int code = temp1*16+temp2;// buffer[1] left shift 4 bits and buffer[2]
        return code;
    }
}
