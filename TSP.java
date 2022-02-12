import javax.xml.transform.TransformerException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.StringTokenizer;
/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class TSP {
    static String file = "src/CrimeLatLonXY1990.csv";
    public static int num=0;
    public static Node root;
    public String[] multiline = new String[1000];
    public static String start;
    public static String end;

    public TSP()
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter start date");
        start = sc.nextLine();
        System.out.println("Enter end data");
        end = sc.nextLine();
        System.out.println("Crime records between "+ start+" and "+ end);
        sc.close();
        buildTree(start, end);
    }

    /**
     * @pre-condition
     * root exists
     * @post-condition
     * return the node of root
     * @return
     */

    public Node getRoot()
    {
        return  this.root;
    }

    public void buildTree(String start, String end)
    {
        try {
            BufferedReader br = new BufferedReader(new FileReader((file)));
            String line = br.readLine();
            while((line =br.readLine()) != null) {
                String[] data = line.split(",");
                String date = data[5];
                if (date.compareTo(end) <= 0 && date.compareTo(start) >= 0)
                {
                    System.out.println(line);
                    multiline[num] = line;
                    num++;
                }
            }
            String[] data = multiline[0].split(",");
            double x = Double.parseDouble(data[0]);
            double y = Double.parseDouble(data[1]);
            this.root = new Node(x, y);
            this.root.data = multiline[0];
            for(int i=1;i<num;i++)
            {
                line = multiline[i] ;
                String[] element = line.split(",");
                x = Double.parseDouble(element[0]);
                y = Double.parseDouble(element[1]);
                this.insert(x,y,multiline[i]);

            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    /**
     * @pre-condition
     * x , y and getData contain the data in the file in the same row
     * @post-condition
     * The 2d tree is constructed
     * @param x X in file
     * @param y Y in file
     * @param data data in column 3,4,5,6,7,8,9
     */
    public void insert(double x, double y,String data) {
        this.findPath(x, y, 0, this.root, data);
    }

    /**
     * @pre-condition
     * x , y and getData contain the data in the file in the same row
     * @post-condition
     * The 2d tree is constructed
     * @param x X in file
     * @param y Y in file
     * @param counter depth of Node
     * @param curObj Node of parent
     * @param getData data in column 3,4,5,6,7,8,9
     */

    public void findPath(double x, double y, int counter, Node curObj,String getData) {
        if (counter % 2 == 0)//compare the value of x
        {
            if (x <= curObj.x && curObj.left != null) {//on the left side , and the left node exists
                this.findPath(x, y, counter+1, curObj.left,getData);
            }
            else if (x > curObj.x && curObj.right != null) {//on the right side , and the right node exists
                this.findPath(x, y, counter+1, curObj.right,getData);
            }
            else if (x <= curObj.x && curObj.left == null)//on the left side , and the left node is null
            {
                curObj.left = new Node(x, y);
                curObj.left.depth = counter+1;
                curObj.left.data = getData;
            }
            else//on the left side , and the left node is null
            {
                curObj.right = new Node(x, y);
                curObj.right.depth = counter+1;
                curObj.right.data = getData;
            }
        }
        else { //compare the value of y
            if (y <= curObj.y && curObj.left != null) {//on the left side , and the left node exists
                this.findPath(x, y, counter+1, curObj.left,getData);
            } else if (y > curObj.y && curObj.right != null) {//on the right side , and the right node exists
                this.findPath(x, y, counter+1, curObj.right,getData);
            } else if (y <= curObj.y && curObj.left == null) {//on the left side , and the left node is null
                curObj.left = new Node(x, y);
                curObj.left.depth = counter+1;
                curObj.left.data = getData;
            } else {
                curObj.right = new Node(x, y);//on the right side , and the left node is null
                curObj.right.depth = counter+1;
                curObj.right.data = getData;
            }
        }
    }




    public static void main(String[] args) {


        TSP findCrime  =  new TSP();
        graph G =new graph(num);
        G.checkGraph(num,start,end);
        System.out.println("Hamiltonian Cycle (not necessarily optimum):");
        prim.MST(G);
        double distance = prim.getMileage();
        for(int i=0;i<prim.preorder.length;i++)
        {
            System.out.print(prim.preorder[i]+"\t");
        }
        System.out.println();
        System.out.println("Length Of cycle:"+distance+"miles");
        //P2
        System.out.println("Looking at every permutation to find the optimal solution");
        System.out.println("The best permutation");
        bruteForce BF = new bruteForce();
        BF.findPath();
        BF.findShortestPath();
        System.out.println(BF.shortestRoute);
        System.out.println("Optimal Cycle length ="+BF.shortestDistance+"miles");

        //P3
        ArrayList<String> tspPath = new ArrayList<>();
        int[] primwalk = prim.preorder;
        for(int i=0;i<primwalk.length;i++)
        {
            int temp = primwalk[i];
            String newString = graph.getLabel(temp).data;
            tspPath.add(newString);
        }
        ArrayList<String> optimalPath = new ArrayList<>();
        ArrayList<Integer> brutewalk = BF.shortestRoute;
        for(int i=0;i<brutewalk.size();i++)
        {
            int current = brutewalk.get(i);
            String newString = graph.getLabel(current).data;
            optimalPath.add(newString);
        }
        try {
            KML.toKML(tspPath,optimalPath);
        } catch (TransformerException e) {
            e.printStackTrace();
        }

    }









}
