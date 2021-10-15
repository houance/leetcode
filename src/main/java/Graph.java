import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Graph 存储用于存储图
 *
 * Graph 用一个 Map<Integer, Node> 存储结点, index 是 key
 *
 * Node : 表示一个结点, 包含三个变量
 *         weight 表示这个结点的权重(可用可不用),
 *         edges 表示以这个结点为起点的边, 一般不需要传入
 *         index 表示这个结点的序号, 不需要传入, 在 graph 中添加 node 时已自动传入
 *
 * Edge : 表示一条边, 包含两个变量
 *          weight 表示这条边的权重,
 *          end 表示这条边连接的终点, 即 Node
 */
public class Graph {

    static class Node {
        static class Edge {
            Node end;
            int weight;

            public Edge(Node end) {
                this.end = end;
            }

            public Edge(Node end, int weight) {
                this.end = end;
                this.weight = weight;
            }

            public Node getEnd() {
                return end;
            }

            public void setEnd(Node end) {
                this.end = end;
            }

            public int getWeight() {
                return weight;
            }
            public void setWeight(int weight) {
                this.weight = weight;
            }

            @Override
            public String toString() {
                return "Edge{" +
                        "end=" + end +
                        ", weight=" + weight +
                        '}';
            }
        }


        int weight;
        List<Edge> edges;
        int index;

        public Node(int weight) {
            this.weight = weight;
        }

        public Node(){
            this.edges = new ArrayList<>();
        }

        public void addEdge(Edge edge){
            this.edges.add(edge);
        }

        public int getWeight() {
            return weight;
        }
        public void setWeight(int weight) {
            this.weight = weight;
        }
        public List<Edge> getEdges() {
            return edges;
        }
        public void setEdges(List<Edge> Edges) {
            this.edges = Edges;
        }

        @Override
        public String toString() {
            return "Node{" +
                    "weight=" + weight +
                    ", edges=" + edges +
                    ", index=" + index +
                    '}';
        }
    }

    private Map<Integer, Node> nodes;

    public Graph() {
        this.nodes = new HashMap<>();
    }

    public void addNode(int index, Node node){
        node.index = index;
        nodes.put(index, node);
    }


    public Node getNode(int number){
        return nodes.get(number);
    }

    public Map<Integer, Node> getNodes() {
        return nodes;
    }

    public void setNodes(Map<Integer, Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "nodes=" + nodes +
                '}';
    }
}
