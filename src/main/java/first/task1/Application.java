package first.task1;

import java.io.File;

public class Application {
    public static void main(String[] args) throws Exception {
        File file = new File("/Users/aleksandr/5 курс 1 семестр/Code/src/main/java/first.neuralNetworks/task1/test.txt");
        Graph graph = new Graph();
        graph.readArcs(file);
        graph.sortAndReturn();
    }
}
