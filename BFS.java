class Node{
    char node_name;
    boolean visited;
    Node next;
    Node q_next;
    char edgeto = '-';//to which vertex it is connected to
    int dis = 0;// distance of this particular vertex from the origin vertex. At first it is initialized to zero
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
}

class B_F_S{
    static Node adj_list[];
    static int num_nodes = 0;
    static Node top=null;
    B_F_S(){
        adj_list=new Node[100];
        num_nodes = 0;
    }
    void add_vertex(char vertex){
        adj_list[num_nodes] = new Node(vertex);
        num_nodes++;
    }
    static void add_ud_edge(char v1, char v2){
        //find v1
        int i;
        for(i=0; i<num_nodes ; i++){
            if( adj_list[i].node_name == v1 ){ // found the node
            add_to_list( (adj_list[i]), v2);
            }
            if( adj_list[i].node_name == v2 ){ // found the node
            add_to_list( (adj_list[i]), v1);
            }
        }
    }
    static void add_to_list( Node n, char vertex){
        Node tmp;
        // move to end of current list
        for(tmp=n; tmp.next != null; tmp=tmp.next);tmp.next = new Node(vertex);
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
    //insert, remove and print queue functions are implemented in this block
    static void insert(char a) {
        if(top==null) {
            top=new Node(a);
        }else{
            Node ptr;
            for(ptr= top; ptr.q_next != null; ptr=ptr.q_next);
            ptr.q_next = new Node(a);
        }
    }
    static char remove(){
        Node temp;
        temp = top;
        top = top.q_next;
        return temp.node_name;
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

    static void add_QUEUE(char s){
        insert(s);
    }
    static char remove_QUEUE(){
        char rem;
        rem = remove();
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
    ////it will delete the stack to have it used for the next path search
    static void del_stack(){
        Node pops;
        while(top!=null){
            pops = pop();
        }
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

    //this below function will perform the breadth first search
    static int dist=0;//distance from the starting vertex
    static Node vat;
    static void bfs(char s){
        add_QUEUE(s);
        //to mark the vertex visited
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==s){
                adj_list[i].visited = true;
            }   
        }
        while(!(top==null)){//runs until queue is non empty
            char v = remove_QUEUE();
            for(int i=0; i<num_nodes; i++){
                if(adj_list[i].node_name==v){
                    vat = adj_list[i];
                    dist = adj_list[i].dis;
                }   
            }
            char w;
            for(vat=vat.next; vat != null; vat=vat.next){
                w = vat.node_name;
                for(int i=0; i<num_nodes; i++){
                    if( adj_list[i].node_name == w){
                        if(adj_list[i].visited == false){//if it isnt marked visited
                            add_QUEUE(w);//the unvisited vertex will be added to the queue
                            adj_list[i].visited = true;
                            edgeTo(w, v, dist+1);//the distance from the origin vertex wll be calculated by adding 1 to its adjacent vertex distance from the origin which is stored in the dis variable of that vertex
                        }
                    }
                }
            }
        }
    }

    public static void main(String args[]){
        B_F_S l1=new B_F_S();
        l1.add_vertex('A');
        l1.add_vertex('B');
        l1.add_vertex('C');
        l1.add_vertex('D');
        l1.add_vertex('E');
        l1.add_vertex('F');
        l1.add_vertex('G');
        l1.add_vertex('H');
        l1.add_vertex('I');

        add_ud_edge('A', 'B');
        add_ud_edge('A', 'C');
        add_ud_edge('A', 'D');
        add_ud_edge('A', 'E');
        add_ud_edge('B', 'F');
        add_ud_edge('D', 'G');
        add_ud_edge('F', 'H');
        add_ud_edge('G', 'I');
        add_ud_edge('E', 'H');
        print_graph(adj_list);

        bfs('A');
        for(int i=0; i < num_nodes; i++){
            System.out.println("[ " + adj_list[i].node_name + " <- " + adj_list[i].edgeto + " | Distance from the Origin Vertex - " + adj_list[i].dis + " ] ");
        }
        pathto('A', 'E');
        pathto('A', 'I');
        pathto('A', 'C');
        pathto('A', 'F');
        pathto('A', 'H');
       
    }
}