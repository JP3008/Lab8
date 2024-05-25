package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class GraphicBTreeController {

    @FXML
    private Pane pane;

    private BTree bTree;
    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    public void initialize() {
        bTree = new BTree();
        generateInitialTree(); // Generar y dibujar el árbol inicial

        // Agregar un listener para dibujar el árbol cuando el pane cambie de tamaño
        pane.widthProperty().addListener((obs, oldVal, newVal) -> drawTree());
        pane.heightProperty().addListener((obs, oldVal, newVal) -> drawTree());
    }

    private void generateInitialTree() {
        int numberOfNodes = util.Utility.getRandom(5);
        for (int i = 0; i <numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }

    @FXML
    public void RandomizeButton() {
        bTree = new BTree();
        int numberOfNodes = util.Utility.getRandom(15) ;
        for (int i = 0; i < numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }


    @FXML
    public void levelsOnAction(ActionEvent actionEvent){
        drawTreeLevels();
    }
    private void drawTreeLevels() {
        pane.getChildren().clear(); // Clear the pane before drawing

        double paneWidth = pane.getWidth();
        double paneHeight = pane.getHeight();

        drawTreeRecursivelyLevels(bTree.getRoot(), paneWidth / 2, NODE_RADIUS * 2, paneWidth / 4, 0, paneWidth);
    }

    private void drawTreeRecursivelyLevels(BTreeNode node, double x, double y, double hGap, int level, double paneWidth) {
        if (node == null) {
            return;
        }

        Circle circle = new Circle(x, y, NODE_RADIUS);
        circle.setFill(Color.PALEGREEN);
        circle.setStroke(Color.BLACK);

        Text text = new Text(x - 4, y + 4, node.data.toString());


        pane.getChildren().addAll(circle, text);


        if (node.left != null) {
            double childX = x - hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - 10); // Ajuste aquí (-10)
            pane.getChildren().add(line);
            drawTreeRecursivelyLevels(node.left, childX, childY, hGap / 2, level + 1, paneWidth);
        }

        if (node.right != null) {
            double childX = x + hGap;
            double childY = y + VERTICAL_GAP;

            Line line = new Line(x, y + NODE_RADIUS, childX, childY - 10); // Ajuste aquí (-10)
            pane.getChildren().add(line);
            drawTreeRecursivelyLevels(node.right, childX, childY, hGap / 2, level + 1, paneWidth);
        }

        if (level > 0) {
            // Convertimos el nivel actual a String
            String nlevel = "Nivel " + level;

            // Calculamos las coordenadas para la línea
            double startY = y - VERTICAL_GAP + NODE_RADIUS;
            double endY = startY;
            double startX = 0;
            double endX = paneWidth;

            // Creamos la línea que representa el nivel
            Line line = new Line(startX, startY- -30, endX, endY- -30);
            line.setStroke(Color.FIREBRICK);

            // Posicionamos el texto del nivel cerca del borde izquierdo
            Text textLevel = new Text(startX + 10, startY - -20, nlevel);

            // Añadimos la línea y el texto al pane
            pane.getChildren().addAll(line, textLevel);
        }
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
        BTree bTree1 = new BTree();
        Text textTour = new Text(x - 20, y + 40, bTree1.preorderTour(node));
        textTour.setFont(new Font("Tahoma", 9));

        pane.getChildren().addAll(circle, text, textTour);

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

    @FXML
    public void tour(ActionEvent actionEvent) throws TreeException {
        if (bTree.isEmpty()){
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }else{
            try {
                Alert alertTreeTour = new Alert(Alert.AlertType.INFORMATION);
                alertTreeTour.setTitle("Tree Tour Information");
                alertTreeTour.setHeaderText(null);
                alertTreeTour.setContentText(bTree.preOrder() + "\n"
                        + bTree.inOrder() + "\n"
                        + bTree.postOrder());
                alertTreeTour.showAndWait();
            }catch (TreeException tE){
                alert.setContentText("The tree is empty");
                alert.showAndWait();
            }
        }
    }

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;

}







