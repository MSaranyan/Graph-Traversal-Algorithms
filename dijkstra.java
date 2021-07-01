import java.util.*;
class Node{
    char node_name;
    boolean visited;
    Node next;
    Node q_next;
    char edgeto = '-';//to which vertex it is connected to
    int dis;// distance of this particular vertex from the origin vertex
    int weight;//weight of the edge incident to this vertex
    Node(){
        this.node_name = ' ';
        this.visited = false;
        this.next = null;
    }
    Node(char vertex){
        this.node_name = vertex;
        this.visited = false;
        this.next = null;
        this.q_next = null;
    }
    Node(char vertex, int weight){
        this.node_name = vertex;
        this.visited = false;
        this.next = null;
        this.q_next = null;
        this.weight = weight;
    }
}

class dijkstra{
    static Node adj_list[];
    static int num_nodes = 0;
    static Node top=null;
    static Node pq[];//priority queue array
    static int num_elements = 0;
    dijkstra(){
        adj_list=new Node[100];
        num_nodes = 0;
    }
    void add_vertex(char vertex){
        adj_list[num_nodes] = new Node(vertex);
        num_nodes++;
    }
    static void add_dir_edge(char v1, char v2, int weight){
        //find v1
        int i;
        for(i=0; i<num_nodes ; i++){
            if( adj_list[i].node_name == v1 ){ // found the node
            add_to_list( (adj_list[i]), v2, weight);
            }
        }
    }
    static void add_to_list( Node n, char vertex, int weight){
        Node tmp;
        // move to end of current list
        for(tmp=n; tmp.next != null; tmp=tmp.next);tmp.next = new Node(vertex, weight);
    }
    static void print_graph(Node adj_list[]){
        int i,j;
        Node tmp;
        for(i=0; i < num_nodes; i++){
            System.out.printf("%c -> ", adj_list[i].node_name);
            for(tmp=adj_list[i].next; tmp != null; tmp=tmp.next){
            System.out.printf("[%c]->", tmp.node_name);
            }
            System.out.printf("[]\n");
        }
    }
    /////////////////////////////////////////////////////////////////////////////////
    //My own Priority Queue implemented in this block
    static void insert(char vertex, int dist){
        num_elements++;
        pq[num_elements] = new Node(vertex);//the elements will be stored in the array from position one instead of zer0
        pq[num_elements].dis = dist;
        swim(num_elements);
        
    }
    static void swim(int N){
        while(N>1 && great(N/2, N)){
            swap(N/2, N);
            N = N/2;
        }
    }
    static void sink(int N){
        while(2*N<=num_elements){
            int j = 2*N;
            if(j<num_elements && great(j, j+1)){
                j++;
            }
            if(!great(N, j)){
                break;
            }
            swap(N, j);
            N = j;
        }
    }
    static char delMin(){
        char min_element = pq[1].node_name;
        swap(1, num_elements--);
        sink(1);
        pq[num_elements+1] = null;
        return min_element;
    }
    //it checks the distance of 2 vertices from the source and if a is greater than b, it will return true.
    static boolean great(int a, int b){
        Node tmp;
        if(pq[a].dis>pq[b].dis){
            return true;
        }
        return false;
    }
    //it will swapp 2 vertices
    static void swap(int a, int b){
        Node tmp;
        tmp = pq[b];
        pq[b] = pq[a];
        pq[a] = tmp;
    }
    static boolean isEmpty(){
        if(pq[1]==null){
            return true;
        }
        return false;
    }
    static void print_Elements() {
        Node tmp;
        for(int i=1; i<=num_elements; i++){
            System.out.println(pq[i].node_name);
        }
        System.out.println();
    }

    static void print_Nodes(char s, char v) {
        Node tmp;
        System.out.print("The shortest path from " + s + " to " + v + "\n[ ");
        for(tmp=top; tmp!=null; tmp=tmp.q_next ) {
            System.out.print(tmp.node_name + " -> ");
        }
        System.out.println(" ]\n");
    }
    /////////////////////////////////////////////////////////////////////////////////

    static void add_QUEUE(char s, int dis){
        insert(s, dis);
    }
    static char remove_QUEUE(){
        char rem;
        rem = delMin();
        return rem;
    }
    ////////////////////////////////////////////////////////////////////////////////////
    //push, pop functions are implemented in this block
    static void push(char a) {
        if(top==null) {
            top=new Node(a);
        }else{
            Node ptr;
            ptr = new Node(a);
            ptr.q_next = top;
            top = ptr;
        }
    }
    
    static Node pop(){
        Node temp;
        temp = top;
        top = top.q_next;
        return temp;
    }
    ////it will delete the stack to have it used for the next path search
    static void del_stack(){
        Node pops;
        while(top!=null){
            pops = pop();
        }
    }
    
    /////////////////////////////////////////////////////////////////

    //this edgeTo() function will store the vertex v which lead to the vertex w in its edgeto variable
    static void edgeTo(char w, char v, int dis){
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==w){
                adj_list[i].edgeto = v;
                adj_list[i].dis = dis;
            }   
        }
    }
    /////////////////////////////
    //this function will return 1 or 0 based on whether the vertex v is visited or not
    static int isPath(char v){
        for(int i=0; i<num_nodes; i++){
            if( adj_list[i].node_name == v){
                if(adj_list[i].visited){
                    return 1;//returns 1 if it is marked as visited
                }
            }
        }   
        return 0; 
    }
    
    //this function will find a path from s to v and store the path in stack and print it
    static Node pathto(char s, char v){//origin, end point
        if(!(isPath(v)==1)){
            return null;
        }
        for(int j=0; j<num_nodes; j++){
            if( adj_list[j].node_name == v){
                for(char a = v; a!=s;){
                    for(int i=0; i<num_nodes; i++){//it will traverse the array to find that particular vertex 
                        if(adj_list[i].node_name==a){
                            push(a);
                            a = adj_list[i].edgeto;
                            break;
                        }   
                    }
                }
            }
        }
        push(s); 
        print_Nodes(s, v);//it will print the stack
        del_stack();//it will delete the stack to have it used for the next path search
        return top;
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    static void relax(char vee, char wuu, int edge_weight){
        char v = vee;
        char w = wuu;
        Node ve = null;
        Node wu = null;
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==v){
                ve = adj_list[i];
            }   
        }
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==w){
                wu = adj_list[i];
            }   
        }
        if(wu.dis>ve.dis + edge_weight){
            wu.dis = ve.dis + edge_weight;
            edgeTo(w, v, wu.dis);
            if(is_contains(w)){
                update_pq(w, wu.dis);
            }
            else{
                add_QUEUE(w, wu.dis);
            }
        }
    }
    static boolean is_contains(char w){
        for(int i=1; i<num_elements; i++){
            if(pq[i].node_name==w){
                return true;
            }   
        }
        return false;
    }
    //this function is used to update its position in the priority queue after the update
    static void update_pq(char w, int dist){
        for(int i=1; i<num_elements; i++){
            if(pq[i].node_name==w){
                pq[i].dis = dist;
                swim(i);
            }   
        }
    }
    ///////////////////////////////////////////////////////////////////////////////////////////
    //this below function will implements the dijkstra's algorithm
    static Node vat;
    static void dijk(char s){
        pq = new Node[num_nodes+1];
        int edge_weight = 0;
        //initialize all the vertices in the graph to have large distance from the source vertex except for the source vertex
        for(int i=1; i<num_nodes; i++){
            adj_list[i].dis = 1000; 
        }
        //to mark the source vertex as visited
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==s){
                adj_list[i].visited = true;
                adj_list[i].dis = 0;
            }   
        }
        add_QUEUE(s, 0);// add the source vertex to the priority queue
        while(!isEmpty()){//this loop will run until the priority queue is empty
            char v = remove_QUEUE();//pop the vertex with minimum distance from the source vertex in the priority queue
            for(int i=0; i<num_nodes; i++){
                if(adj_list[i].node_name==v){
                    adj_list[i].visited = true;
                    vat = adj_list[i];
                }   
            }
            char w;
            for(vat=vat.next; vat != null; vat=vat.next){//it finds the adjacent vertices 
                w = vat.node_name;//the name of the adjacent vertex of vertex 'v'
                edge_weight = vat.weight;
                relax(v, w, edge_weight);//it will relax the edge
            }
        }
    }

    public static void main(String args[]){
        dijkstra l1=new dijkstra();
        l1.add_vertex('A');
        l1.add_vertex('B');
        l1.add_vertex('C');
        l1.add_vertex('D');
        l1.add_vertex('E');
        l1.add_vertex('F');
        l1.add_vertex('G');
        l1.add_vertex('H');

        add_dir_edge('A', 'B', 5);
        add_dir_edge('A', 'C', 8);
        add_dir_edge('A', 'D', 9);
        add_dir_edge('B', 'F', 15);
        add_dir_edge('B', 'H', 12);
        add_dir_edge('B', 'C', 4);
        add_dir_edge('C', 'H', 7);
        add_dir_edge('C', 'E', 6);
        add_dir_edge('D', 'C', 5);
        add_dir_edge('D', 'E', 4);
        add_dir_edge('D', 'G', 20);
        add_dir_edge('E', 'H', 1);
        add_dir_edge('E', 'G', 13);
        add_dir_edge('F', 'G', 9);
        add_dir_edge('H', 'F', 3);
        add_dir_edge('H', 'G', 11);
        print_graph(adj_list);

        dijk('A');
        for(int i=0; i < num_nodes; i++){
            System.out.println("[ " + adj_list[i].node_name + " edge to " + adj_list[i].edgeto + " | Shortest Distance from the Origin Vertex - " + adj_list[i].dis + " ] ");
        }
        pathto('A', 'E');
        pathto('A', 'I');
        pathto('A', 'C');
        pathto('A', 'F');
        pathto('A', 'H');
        pathto('A', 'G');
        pathto('A', 'B');
    }
}