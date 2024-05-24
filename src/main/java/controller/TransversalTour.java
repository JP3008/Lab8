package controller;

import domain.BTree;
import domain.BTreeNode;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TransversalTour
{
    @javafx.fxml.FXML
    private Pane pane;

    private BTree bTree;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @javafx.fxml.FXML
    public void initialize() {
        bTree = new BTree();
        generateInitialTree();
    }
    private void generateInitialTree() {
        int numberOfNodes = util.Utility.getRandom(24) + 8;
        for (int i = 0; i < numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }

    private void drawTree() {
        pane.getChildren().clear(); // limpia el panel antes de dibujar otro arbol

        double paneWidth = pane.getWidth();
        drawTreeRecursively(bTree.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4);
    }
    private void drawTreeRecursively(BTreeNode node, double x, double y, double hGap) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.PALEGREEN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());
        //Text textTour = new Text(x - 30, y + 40, node.data.toString());

        pane.getChildren().addAll(circle, text); //textTour);

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursively(node.left, childX, childY-35, hGap / 2);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursively(node.right, childX, childY-35, hGap / 2);
        }
    }

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;

    @javafx.fxml.FXML
    public void RandomizeButton(ActionEvent actionEvent) {
        bTree = new BTree();
        int numberOfNodes = util.Utility.getRandom(24) + 8;
        for (int i = 0; i < numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }


    @javafx.fxml.FXML
    public void inOrderOnAction(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void preOrderOnAction(ActionEvent actionEvent) {
        pane.getChildren().clear(); // Limpiar el panel antes de dibujar otro árbol
        double paneWidth = pane.getWidth();
        int[] counter = new int[1]; // Arreglo de un solo elemento para mantener el contador
        counter[0] = 1; // Ponemos el contador en 1 para el primer nodo
        drawTreeRecursivelyPreOrder(bTree.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
    }

    private void drawTreeRecursivelyPreOrder(BTreeNode node, double x, double y, double hGap, int[] counter) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.PALEGREEN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        // Muestra el número de orden en el recorrido preorden debajo de cada nodo
        Text orderText = new Text(x - 4, y + 25, Integer.toString(counter[0]));
        counter[0]++;

        pane.getChildren().addAll(circle, text, orderText);

        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursivelyPreOrder(node.left, childX, childY - 35, hGap / 2, counter);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - NODE_RADIUS);
            pane.getChildren().add(line);
            drawTreeRecursivelyPreOrder(node.right, childX, childY - 35, hGap / 2, counter);
        }
    }

    @javafx.fxml.FXML
    public void postOrderOnAction(ActionEvent actionEvent) {
        if (bTree != null) {
            pane.getChildren().clear();
            double paneWidth = pane.getWidth();
            int[] counter = {1}; // Contador para rastrear el número de nodos visitados
            drawTreeRecursivelyPostOrder(bTree.getRoot(), paneWidth / 2, 2 * NODE_RADIUS, paneWidth / 4, counter);
       }
    }

    private void drawTreeRecursivelyPostOrder(BTreeNode node, double x, double y, double hGap, int[] counter) {
        if (node == null) {
            return;
        }

        // Dibujar los hijos izquierdo y derecho primero (postorden)
        drawTreeRecursivelyPostOrder(node.left, x - hGap, y + VERTICAL_GAP, hGap / 2, counter);
        drawTreeRecursivelyPostOrder(node.right, x + hGap, y + VERTICAL_GAP, hGap / 2, counter);

        // Luego dibujar el nodo actual
        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.PALEGREEN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());

        // Mostrar el número de orden en el recorrido postorden debajo de cada nodo
        Text orderText = new Text(x - 4, y + 25, Integer.toString(counter[0]));
        counter[0]++;

        pane.getChildren().addAll(circle, text, orderText);

        // Dibujar línea desde el nodo hasta su hijo izquierdo
        if (node.left != null) {
            Line lineLeft = new Line(x, y + NODE_RADIUS, x - hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineLeft);
        }

        // Dibujar línea desde el nodo hasta su hijo derecho
        if (node.right != null) {
            Line lineRight = new Line(x, y + NODE_RADIUS, x + hGap, y + VERTICAL_GAP - NODE_RADIUS);
            pane.getChildren().add(lineRight);
        }
    }

}

