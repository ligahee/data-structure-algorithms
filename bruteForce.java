/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
import java.util.ArrayList;
import java.util.LinkedList;

public class bruteForce {
    public static LinkedList<routeNode> path = new LinkedList<>();
    public static ArrayList<Integer> shortestRoute= new ArrayList();
    public static double shortestDistance ;
    private static int count;

    private static class routeNode{
        private double distance;
        private ArrayList<Integer> route;

        private routeNode(ArrayList route)
        {
            this.distance = Double.MAX_VALUE;
            this.route = route;
        }
        private void setDistance(double weight)
        {
            this.distance = weight;
        }//total distance of  Hamiltonian cycle
        private void setRoute(ArrayList routeidex)
        {
            this.route = routeidex;
        }//arraylist of the  Hamiltonian cycle

    }

    public bruteForce()
    {
        shortestDistance = Double.MAX_VALUE;
        count = 0;
    }

    /**
     * @post_condition
     * calculate the total distance of the route
     * @param A the arraylist of the route
     * @return
     */
    public static double theTotaldistance(routeNode A)
    {
        ArrayList<Integer> route = A.route;
        route.add(A.route.get(0));
        A.setRoute(route);

        double distance =0;
        for(int i=0;i<graph.totalNum;i++)
        {
            distance+=graph.isEdge(route.get(i),route.get(i+1));
        }
        distance = distance*0.00018939;
        A.setDistance(distance);
        return distance;
    }

    /**
     * @post_condition
     * compare it to the shortest distance, then find and return the shortest path
     */
    static void findShortestPath()
    {
        for(int i=0;i<count;i++) {
            routeNode temp = path.get(i);
            if (temp.distance < shortestDistance) {
                shortestDistance = temp.distance;
                shortestRoute = temp.route;
            }

        }
    }

    /**
     * @pre_condition
     * find an optimal tour using brute force approach.
     * |V| ! permutations of the |V| vertices
     */
    public  static void findPath()
    {
        ArrayList<Integer> notInroute =new ArrayList<>();
        for(int i=0;i<graph.totalNum;i++)
            notInroute.add(i);//arraylist of vertices that are not in  Hamiltonian cycle
        findAllpath(new ArrayList<Integer>(),notInroute);

    }
    private static void findAllpath(ArrayList<Integer> currentRoute, ArrayList<Integer> notInRoute)
    {
        if(!notInRoute.isEmpty()) {
            for(int i=0;i<notInRoute.size();i++)
            {
                int newnode = notInRoute.remove(0);
                ArrayList<Integer> newRoute = (ArrayList<Integer>) currentRoute.clone();
                newRoute.add(newnode);
                findAllpath(newRoute,notInRoute);
                notInRoute.add(newnode);
                if(newRoute.size() == graph.totalNum) {
                    count++;
                    routeNode temp = new routeNode(newRoute);
                    theTotaldistance(temp);
                    path.add(temp);
                }
            }
        }

    }






}
