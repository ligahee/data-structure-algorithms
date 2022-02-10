import java.util.ArrayList;

public class HashTable {
    static final int ArrayCapacity = 127;
    public static ArrayList<HashTable.Node>[] map;
    public static int SIZE=ArrayCapacity;

    private static class Node {
        public int key;
        public String val;

        public Node(int key, String val) {
            this.key = key;
            this.val = val;

        }

    }

    public HashTable(){
        map = new ArrayList[SIZE];
        for(int i=0;i<SIZE;i++)
        {
            map[i]=new ArrayList<HashTable.Node>();
        }
    }

    private static int  getHash(int key)
    {
        int hashCode=Math.abs((Integer.hashCode(key) )%SIZE);
        return hashCode;
    }


    public void put(int key,String val)  {
        int bucket = getHash(key);

        for(HashTable.Node temp : map[bucket])
        {
            if(temp.key==key)
            { temp.val=val;
                return;}
        }
        HashTable.Node newNode= new HashTable.Node(key, val);
        map[bucket].add(newNode);

    }

    public boolean containsKey(int key){
        return get(key)!= null;
    }

    public static String get(int key){
        int bucket= getHash(key);
        ArrayList<HashTable.Node> oldList = map[bucket];
        //System.out.println(key);
        for(int i=0;i<oldList.size();i++)
        {
            if(oldList.get(i).key==key) {

                return oldList.get(i).val;
            }
        }
        return null;
    }

}
