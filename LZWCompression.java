import java.io.*;
/**
* @author
 * lijiayi
 * data structure and algorithms
 * Assignment 5
**/
import static java.lang.String.valueOf;

public class LZWCompression {
    private int key;
    private int outputSize;
    private int inputsize;
    private int inCount ;
    private int outCount;
    private int Max_Table = (int)Math.pow(2,12);
    private int tableSize = 255;
    HashMap dictionary ;
    HashTable outDictionary;

    public LZWCompression()
    {
        key = 0;
        outputSize = 0;
        inputsize = 0;
        inCount = 0;
        outCount = 0;
    }

    /**
     * it can read ASCII and binary data type, read the byte and convert them
     * to 12 bits code,
     * 0 0 0 0 0 1 0 0 0 0 0 1
     * 0 0 0 0 0 1 0 0 0 0 1 0
     * then store every two 12bits-byte in two chunk, like this
     * 0 0 0 0 0 1 0 0
     * 0 0 0 1 0 0 0 0
     * 0 1 0 0 0 0 1 0
     * @param input file input path
     * @param output file output path
     * @throws IOException
     */
    public void compress(String input, String output) throws IOException {
            //read the input file
            DataInputStream readFile = new DataInputStream(new BufferedInputStream(new FileInputStream(input)));
            //write the output file
            DataOutputStream writeFile = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(output)));
            //initialize the dictionary with all possible one byte
            // In this for loop, however, the integer is converted to an ASCII representation.
            // For example, when smallInt == 65, two ASCII characters are generated: '6' and '5'.
            // No misbehavior will occur here.
            dictionary=new HashMap();
            for(int i=-128;i<128;i++)
            {
                // Place a char into a String with no conversion.
                dictionary.put(String.valueOf((char)i),i+128);
            }
            key = 256;
            String S="";
            int bufferLeft = 0;
            byte[] buffer = new byte[3];
            try{
            while(true)
            {
                inputsize++;
                char c = (char)readFile.readByte();
                String newline= S+c;
                if(dictionary.containsKey(newline))
                {
                    S=newline;
                }
                else
                {
                    int codeword = dictionary.get(S);
                    //convert the integer to 12 bits
                    if(bufferLeft==0)
                    {
                        //only take the higher 8 bits from 12 bits, and the higher 5 bits in buffer[0] are 0
                        buffer[0]=(byte)(codeword>>>4);//right shift 4 bits
                        //take the lower 4 bits from 12 bits and add"0000" at the end
                        buffer[1]=(byte)(codeword<< 4);//remove the higher 4 bits, left shift 4 bits
                        bufferLeft++;
                    }
                    else {
                        // this is the second chunk
                        //only take the higher 4 bits from 12 bits, and the higher 4 bits of buffer[1] are the lower 4 bits of last byte
                        buffer[1] = (byte)(buffer[1] | (codeword >>> 8));//right shift 8 bits
                        buffer[2] = (byte)(codeword);  // only take the lower 8 bits

                        for(int i=0;i<buffer.length;i++)
                        {
                            writeFile.writeByte(buffer[i]);
                            outputSize++;
                        }
                        bufferLeft=0;
                    }

                    if(key<Max_Table) {
                        dictionary.put(newline,key++);
                    }else{//when the size is larger than the max dictionary size, then expand a new dictionary
                        dictionary=new HashMap();
                        for(int i=-128;i<128;i++)
                        {
                            dictionary.put(String.valueOf((char)i),i+128);
                        }
                        key = 256;
                    }
                    S=""+c;
                }

            }

        }  catch (EOFException e) {
                int codeword = dictionary.get(S);
                //there will be two chunk left to input
                if(bufferLeft==0)
                {
                    buffer[0]=(byte)(codeword&0x7F);
                    buffer[1]=(byte)(codeword<< 4);

                    for(int i=0;i<2;i++)
                    {
                        writeFile.writeByte(buffer[i]);
                        outputSize++;
                    }
                }
                else {
                    // this is the second chunk
                    buffer[1] = (byte)(buffer[1] | (codeword >>> 8));
                    buffer[2] = (byte)(codeword);  // the higher 4 bits will be chopped off from codeword using byte casting

                    for(int i=0;i<buffer.length;i++)
                    {
                        writeFile.writeByte(buffer[i]);
                        outputSize++;
                    }
                }
                readFile.close();
                writeFile.close();
                System.out.println("bytes read = " + inputsize + ", bytes written = " + outputSize);
        }
    }

    /**
     * get the every two 12 bits from every three byte from zipped file
     * and find the corresponding byte
     * @param in input zipped file
     * @param out output unzipped file
     * @throws IOException
     */

    public void decompress(String in, String out) throws IOException {
        outDictionary=new HashTable();
        for(int i=-128;i<128;i++)
        {
            outDictionary.put(i+128,String.valueOf((char)i));
        }
        DataInputStream input = new DataInputStream(new BufferedInputStream(new FileInputStream(in)));
        DataOutputStream output = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(out)));
        int deCompressKey = 256;
        byte[] buffer = new byte[3];
        int priorCodeword;
        int bufferLeft = 0;
        int codeword = 0;
        try {
            //https://github.com/gauthamk89/LZW-Compression/blob/master/src/LZWCompression.java
            buffer[0] = input.readByte();
            inCount++;
            buffer[1] = input.readByte();
            inCount++;
            bufferLeft++;

            //take the lower
            priorCodeword= convertFirstInt(buffer[0],buffer[1]);
            char[] prChar = outDictionary.get(priorCodeword).toCharArray();
            for(int i=0;i<prChar.length;i++) {
                output.writeByte(prChar[i]);
                outCount++;}
            while(true)
            {//read every three byte, and convert them into two int codewords
                if(bufferLeft==0)
                {
                    buffer[0]=input.readByte();
                    inCount++;
                    buffer[1]=input.readByte();
                    inCount++;
                    codeword = convertFirstInt(buffer[0],buffer[1]);
                    bufferLeft++;
                }
                else
                {
                    buffer[2] = input.readByte();
                    inCount++;
                    codeword = convertSecondInt(buffer[1],buffer[2]);
                    bufferLeft=0;
                }

                if(codeword>=deCompressKey)//codeword not in the table
                {//special case may happen when the last char is same as the first char,
                    //Input: “ABABABA”
                    String newline = outDictionary.get(priorCodeword)+outDictionary.get(priorCodeword).charAt(0);
                    if(deCompressKey<Max_Table)
                    {
                       outDictionary.put(deCompressKey++,newline);
                    }else{
                        outDictionary=new HashTable();
                        for(int i=-128;i<128;i++)
                        {
                            outDictionary.put(i+128,String.valueOf((char)i));
                        }
                        deCompressKey=256;
                    }
                    char[] newChar = newline.toCharArray();
                    for(int i=0;i<newChar.length;i++) {
                        output.writeByte(newChar[i]);
                        outCount++;
                    }
                }
                else
                {
                    String curline = outDictionary.get(priorCodeword)+outDictionary.get(codeword).charAt(0);
                    if(deCompressKey<Max_Table)
                    {
                        outDictionary.put(deCompressKey++,curline);
                    }else{
                        outDictionary=new HashTable();
                        for(int i=-128;i<128;i++)
                        {
                            outDictionary.put(i+128,String.valueOf((char)i));
                        }
                        deCompressKey=256;
                    }
                    char[] newChar = outDictionary.get(codeword).toCharArray();
                    for(int i=0;i<newChar.length;i++) {
                        output.writeByte(newChar[i]);
                        outCount++;
                    }
                }
                priorCodeword=codeword;
            }


        } catch (EOFException e) {
            input.close();
            output.close();
            System.out.println("bytes read = " + inCount + ", bytes written = " + outCount);
        }

    }

    /**
     * convert two byte into 12 bits, then convert to one byte
     * @param zero the first byte of first chunk
     * @param first the second byte of first and second chunk
     * @return
     */
    private static int convertFirstInt(byte zero, byte first)
    {
        int temp1 = (char)(zero&0xff); // c = 0x00FF = 0000000011111111 (remove the extra bits)
        int temp2 = (char)(first&0xff);
        int code = temp1*16+(temp2>>>4);// buffer[0] left shift 4 bits and buffer[1] right shift 4 bits

        return code;
    }

    /**
     * convert two byte into 12 bits, then convert to one byte
     * @param first the second byte of first and second chunk
     * @param second the third byte
     * @return
     */
    private static int convertSecondInt(byte first, byte second)
    {
        int temp = (char)(first&0xff); // c = 0x00FF = 0000000011111111 (remove the extra bits)
        byte b = (byte)(temp<<4);
        int temp1 = (char)(b&0xff);
        int temp2 = (char)(second&0xff);
        int code = temp1*16+temp2;// buffer[1] left shift 4 bits add buffer[2]
        return code;
    }








}
