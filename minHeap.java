/**
 * @author lijiayi
 * @course data structure and algorithms
 * project 4
 */
public class minHeap {
    private static Vertex[] queue;
    private static int size;
    private int capacity;


    public class Vertex{
        public Node node;
        public double distance;//it equals to weight[idex]
        public int idex;
        public Vertex(Node node,double distance)
        {
            this.node = node;
            this.distance = distance;
            this.idex = 0;
        }
        private static  double returnDistance(Vertex e)
        {
            return e.distance;
        }
        private void setDistance(double distance)
        {
            this.distance = distance;
        }
    }

    public minHeap(int capacity)
    {
        this.capacity = capacity;
        this.queue = new Vertex[capacity+1];
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * @post_condition
     * create the heap,add all the node into the heap
     * @param e node
     * @param weight the distance from node e to the parent of node e
     */
    public void add(Node e, double weight)
    {
        if (e == null)
            throw new NullPointerException();
        if(size == capacity)
            throw new RuntimeException("Minheap is full");
        size++;
        queue[size] = new Vertex(e,weight);
        queue[size].idex = size-1;

    }

    /**
     * @post_condition
     * replace with smaller weight
     * @param i the label of the node
     * @param weight the distance from label[i] to parent of label[i]
     */
    public void addAgain(int i, double weight)
    {
        if(size == capacity)
            throw new RuntimeException("Minheap is full");
        for(int j=1;j<=size;j++){
            if(queue[j].idex==i)
            {
                queue[j].setDistance(weight);
                siftUp(j);
                break;
            }

        }

    }

    /**
     * @post_condition
     * sort the heap from small to large
     * @param k rank of the node
     */
    private void siftUp(int k) {
        while(k > 1 && Vertex.returnDistance(queue[k/2])> Vertex.returnDistance(queue[k]))
        {
            swap(k,k/2);//k/2 is the parent
            k = k/2;
        }
    }

    private void swap(int first, int second)
    {
        Vertex temp;
        temp = queue[first];
        queue[first] = queue[second];
        queue[second] = temp;
    }

    /**
     * @post_condition
     * delete and return the node with minimum weight
     * @return
     */
    public Vertex deleteMin() {
        if(isEmpty())
            throw  new RuntimeException("Heap is empty");
        Vertex minimum = queue[1];
        swap(1,size);
        size--;
        if(size != 0)
            siftDown(1);
        return minimum;

    }

    private void siftDown(int k){
        while(2*k <= size){
            int i = 2*k;//2k is the left vertex, and 2k+1 is the right vertex
            if(i < size && Vertex.returnDistance(queue[i+1])>Vertex.returnDistance(queue[i]))
                i++;//find the largest vertex

            if(Vertex.returnDistance(queue[k])<Vertex.returnDistance(queue[i]))
                break;
            swap(k,i);
            k=i;
        }



    }
}
