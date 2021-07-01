class Node{
    char node_name;
    boolean visited;
    Node next;
    Node q_next;
    char edgeto = '-';//to which vertex it is connected to
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

class dfs{
    static Node adj_list[];
    static int num_nodes = 0;
    static Node top=null;
    dfs(){
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
    //push, pop and print stack functions are implemented in this block
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

    static void print_Nodes(char s, char v) {
        Node tmp;
        System.out.print("The  path from " + s + " to " + v + "\n[ ");
        for(tmp=top; tmp!=null; tmp=tmp.q_next ) {
            System.out.print(tmp.node_name + " -> ");
        }
        System.out.println(" ]\n");
    }
    /////////////////////////////////////////////////////////////////////////////////
    //this edgeTo() function will store the vertex v which lead to the vertex w in its edgeto variable
    static void edgeTo(char w, char v){
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==w){
                adj_list[i].edgeto = v;
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
    //this below function will perform the depth first search
    static void d_fs(char s){
        Node vat = null;
        //to mark the vertex visited
        for(int i=0; i<num_nodes; i++){
            if(adj_list[i].node_name==s){
                adj_list[i].visited = true;
                vat = adj_list[i];
            }   
        }
            char w;
            for(vat=vat.next; vat != null; vat=vat.next){//the for loop will traverses through the adjacency list of the given vertex
                w = vat.node_name;
                for(int i=0; i<num_nodes; i++){
                    if( adj_list[i].node_name == w){
                        if(adj_list[i].visited == false){//if it isnt marked
                            edgeTo(w, s);
                            d_fs(w);//this will perform depth first search on unvisited vertex
                        }
                    }
                }
            }
    }

    public static void main(String args[]){
        dfs l1=new dfs();
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

        d_fs('A');
        for(int i=0; i < num_nodes; i++){
            System.out.println("[ " + adj_list[i].node_name + " <- " + adj_list[i].edgeto + " ] ");
        }
        pathto('A', 'E');
        pathto('A', 'I');
        pathto('A', 'C');
        pathto('A', 'F');
        pathto('A', 'H');

    }
}