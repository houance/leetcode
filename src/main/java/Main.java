import java.sql.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    static class someThing{
        public int val;

        public someThing(int val) {
            this.val = val;
        }

        @Override
        public String toString() {
            return "someThing{" +
                    "val=" + val +
                    '}';
        }
    }

    public static void main(String[] args) {

        Graph graph = new Graph();

        graph.addNode(1, new Graph.Node(2));

        System.out.println(graph);

    }


}