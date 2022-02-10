import java.util.ArrayList;
/**
 * @author
 * lijiayi
 * data structure and algorithms
 * Assignment 5
 **/
public class HashMap {
    static final int ArrayCapacity = 127;
    public static ArrayList<Node>[] map;
    public static int SIZE=ArrayCapacity;

    private static class Node {
        public String key;
        public int val;

        public Node(String key, int val) {
            this.key = key;
            this.val = val;

        }

    }

    public HashMap(){
        map = new ArrayList[SIZE];
        for(int i=0;i<SIZE;i++)
        {
            map[i]=new ArrayList<Node>();
        }
    }

    private static int  getHash(String key)
    {
        int hashCode=Math.abs((key.hashCode() )%SIZE);
        return hashCode;
    }


    public void put(String key,int val)  {
        int bucket = getHash(key);

       for(Node temp : map[bucket])
       {
           if(temp.key.equals(key))
           { temp.val=val;
              return;}
       }
        Node newNode= new Node(key, val);
        map[bucket].add(newNode);

    }

    public boolean containsKey(String key){
        return get(key)!= -1;
    }

    public static int get(String key){
        int bucket= getHash(key);
        ArrayList<Node> oldList = map[bucket];
        //System.out.println(key);
        for(int i=0;i<oldList.size();i++)
        {
            if(oldList.get(i).key.equals(key)) {

                return oldList.get(i).val;
            }
        }
        return -1;
    }



}