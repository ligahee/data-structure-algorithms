import java.util.LinkedList;
import java.util.PriorityQueue;
/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class prim {
    static int total = graph.totalNum;
    public static double MAX = Double.MAX_VALUE;
    public static double mileage = 0;
    public static boolean[] visited = new boolean[total];
    public static double[] weight =  new double[total];
    public static boolean[][] mst = new boolean[total][total];
    public static int[] preorder = new int[(total+1)];
    private static int count =0;


    public prim()
    {
        for(int i=0;i<total;i++)
        {
            for(int j=0;j<total;j++)
                mst[i][j] = false;
        }

    }

    /**
     * @pre_condition
     * graph is constructed.
     * @post_condtion
     * 1. Select a vertex r e V[G] to be a root vertex
     * 2. Compute a minimum spanning tree T for G from root r using MST-Prim(G,c,r)
     * 3. Let L be the list of vertices visited in a preorder tree walk of T
     * 4. Return the Hamiltonian cycle H that visits the vertices in the order L
     * @param G graph
     */

    public static void MST(graph G)
    {
        minHeap que = new minHeap(total);
        for(int j=0;j<total;j++)
        {
            weight[j] = Double.MAX_VALUE;
            visited[j] = false;
        }

        Node rooNode= G.getLabel(0);
        weight[0] = 0;//root to visit

        for(int i=0;i<total;i++)
        {
            que.add(graph.getLabel(i),weight[i]);
        }
        while(!que.isEmpty())
        {
            minHeap.Vertex w= que.deleteMin();//return and delete the minimum distance
            int num = graph.getNum(w.node);
            preorder[count]=num;
            count++;
            if(w.node.parent != null) {
                int parent = graph.getNum(w.node.parent);
                mst[parent][num]=true;
            }
            if(!visited[num]){//if vertex is not visited
                visited[num] = true;
                for(int i=0;i<total;i++)
                {
                    Node v = G.getLabel(i);
                    if(!visited[i] && (G.isEdge(num,i)< weight[i]) )
                    {
                        weight[i]=(G.isEdge(num,i));
                        v.setParent(w.node);
                        que.addAgain(i,weight[i]);
                    }
                }
            }

        }
    }


    public static double getMileage()
    {
        preorder[count] = 0;
        for(int i=0;i<total;i++)
        {
            mileage+=graph.isEdge(preorder[i],preorder[i+1]);
        }
        mileage=mileage*0.00018939;
        return mileage;

    }


}
