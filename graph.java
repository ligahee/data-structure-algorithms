import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.NoSuchElementException;
/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class graph {
    public static int totalNum ;
    private static Object[] labels;
    public static double[][] edges;




    public graph(int v)
    {
        totalNum = v;
        edges = new double[v][v];
        for(int i=0;i<v;i++ ){
            for(int j=0;j<v;j++)
                edges[i][j] = 0;
        }
        labels = new Object[v];

    }


    /**
     * @pre-condition
     * vertex exists
     * @post-condition
     * the number of course v get the name n
     * @param v
     * course number of vertex
     * @param n
     * course name of vertex
     */

    public static void setLabel(int v, Node n){
        labels[v] = n;
    }
    /**
     * @pre-condition
     * vertex exists
     * @post-condition
     * get the vertex's  course name
     * @param v
     * course number of vertex
     * @return
     */
    public static Node getLabel(int v){
        return (Node) labels[v];
    }



    public static void checkGraph(int number, String start, String end) {

        int n = 0;
        try {
            BufferedReader br = new BufferedReader(new FileReader(( "src/CrimeLatLonXY1990.csv")));
            String line = br.readLine();
            while((line =br.readLine()) != null)
            {
                String[] element = line.split(",");
                String date = element[5];
                if(date.compareTo(end)<=0 && date.compareTo(start)>=0)
                {
                    double x = Double.parseDouble(element[0]);
                    double y = Double.parseDouble(element[1]);
                    Node newNode = new Node(x,y);
                    newNode.data = line;
                    setLabel(n,newNode);
                    n++;
                }

            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        addEdge();

    }



    public static double isEdge(int s,int t){
        return edges[s][t];
    }

    public static void addEdge()
    {
        for(int i = 0; i<totalNum;i++)
        {
            for(int j = 0;j<totalNum;j++)
            {
                if(j != i) {
                    edges[i][j] = Node.distanceTo(i,j);
                    edges[j][i] = Node.distanceTo(i,j);
                }
            }
        }
    }
    public static int getNum(Node node)
    {
        for(int i=0;i<totalNum;i++)
        {
            Node temp= getLabel(i);
            if(temp.data.equals(node.data))
            {
                return i;
            }

        }
        throw new NoSuchElementException();
    }

    /**
     * @pre-condition
     * vertex with course number v exists
     * @post-condition
     * return the course number that is adjacent to v
     * @param v
     * course number v
     * @return
     */
    public static int[] neighbors(int v){
        int count = 0;
        int[] answer;
        for(int i = 0; i < labels.length; i++)
        {
            if(edges[v][i] != 0)
                count++;
        }
        answer =new int[count];
        count =0;
        for(int j  =0; j < labels.length; j++){
            if(edges[v][j] != 0)
                answer[count++] = j;
        }
        return answer;
    }






}
