/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class Node {
    public double x;
    public double y;
    public Node left;
    public Node right;
    public int depth;
    public String data;
    public Node parent;

    public Node(double x, double y){
        this.x = x;//value of x
        this.y = y;//value of y
        this.left = null;//left node
        this.right = null;//right node
        this.depth = 0;//depth of the node
        this.data = null;
        this.parent = null;
    }

    /**
     * @pre-condition
     * The (x1,y1) pair represents a point in space near Pittsburgh and
     * in the state plane coordinate system
     * @post-condition
     * return the Euclidean distance between this point and that point
     * @param a value of x for query point
     * @param b value of y for query point
     * @return
     */
    public static double distanceTo(int a, int b)
    {
        Node A = graph.getLabel(a);
        Node B = graph.getLabel(b);
        double dx = A.x- B.x;
        double dy =A.y - B.y;
        double result = Math.sqrt(dx*dx+dy*dy);
        return result;
    }

    public void setParent(Node A)
    {
        this.parent = A;
    }



}
