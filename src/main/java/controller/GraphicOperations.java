package controller;

import domain.BTree;
import domain.BTreeNode;
import domain.TreeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class GraphicOperations
{
    @FXML
    private Pane pane;

    private BTree bTree;

    private Alert alert = new Alert(Alert.AlertType.ERROR);

    @FXML
    public void initialize() {
        bTree = new BTree();
        //Preguntarle a ellos si creen que deberiamos generar el arbol o dejar que el usuario lo haga
        generateInitialTree(); // Generar y dibujar el árbol inicial

        // Agregar un listener para dibujar el árbol cuando el pane cambie de tamaño
        pane.widthProperty().addListener((obs, oldVal, newVal) -> drawTree());
        pane.heightProperty().addListener((obs, oldVal, newVal) -> drawTree());
    }

    private void generateInitialTree() {
        int numberOfNodes = util.Utility.getRandom(24) + 8;
        for (int i = 0; i < numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }

    @FXML
    public void RandomizeButton() {
        bTree = new BTree();
        int numberOfNodes = util.Utility.getRandom(24) + 8;
        for (int i = 0; i < numberOfNodes; i++) {
            bTree.add(util.Utility.getRandom(100)); // Usando valores aleatorios entre 0 y 99
        }
        drawTree();
    }
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
            double startY = y - VERTICAL_GAP + NODE_RADIUS;
            double endY = startY;
            double startX = 0;
            double endX = paneWidth;

            Line line = new Line(startX, startY, endX, endY);
            pane.getChildren().add(line);
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
        Text textTour = new Text(x - 30, y + 40, node.data.toString());

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

    private static final int NODE_RADIUS = 20;
    private static final int VERTICAL_GAP = 100;
    //Obtener la altura de un nodo especifico
    @FXML
    public void nodeHeightOnAction(ActionEvent actionEvent) {
        if (bTree.isEmpty()){
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }else {
            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Height Node");
                dialog.setHeaderText("Enter the element to search the height:");
                dialog.setContentText("Element:");
                String result = dialog.showAndWait().orElse(null);
                if (result == null) {
                    alert.setContentText("Nothing was entered");
                    alert.showAndWait();
                } else {
                    try {
                        int element = Integer.parseInt(result);
                        if (bTree.contains(element)) {
                            Alert alertContains = new Alert(Alert.AlertType.INFORMATION);
                            alertContains.setContentText("The element height " + element + " is " + bTree.height(element));
                            alertContains.showAndWait();
                        } else {
                            Alert alertNoContains = new Alert(Alert.AlertType.INFORMATION);
                            alertNoContains.setContentText("The element " + element + " is not contained in the tree");
                            alertNoContains.showAndWait();
                        }

                    } catch (NumberFormatException nfe) {
                        alert.setContentText("The item entered is not a number");
                    }
                }
            } catch (TreeException e) {
                alert.setContentText("The tree is empty");
                alert.showAndWait();
            }
        }
    }
    //Remove un nodo del arbol al azar
    @FXML
    public void removeOnAction(ActionEvent actionEvent) throws TreeException {
        int eliminateValue;
        try{
        do {
            eliminateValue = util.Utility.getRandom(100);
        } while (!bTree.contains(eliminateValue));
        bTree.remove(eliminateValue);
        drawTree();
        } catch (TreeException e) {
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }
    }
    //Agregar un nodo al azar
    @FXML
    public void addOnAction(ActionEvent actionEvent) {
        int newValue = util.Utility.getRandom(100);
        bTree.add(newValue);
        drawTree();
    }
    //Demostrar si el valor ingresado hasta contenido en el arbol
    @FXML
    public void containsOnAction(ActionEvent actionEvent) {
        if (bTree.isEmpty()){
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }else {
            try {
                TextInputDialog dialog = new TextInputDialog();
                dialog.setTitle("Search element");
                dialog.setHeaderText("Enter the element to search:");
                dialog.setContentText("Element:");
                String result = dialog.showAndWait().orElse(null);
                if (result == null) {
                    alert.setContentText("Nothing was entered");
                    alert.showAndWait();
                } else {
                    try {
                        int element = Integer.parseInt(result);
                        if (bTree.contains(element)) {
                            Alert alertContains = new Alert(Alert.AlertType.INFORMATION);
                            alertContains.setContentText("The element " + element + " is contained in the tree");
                            alertContains.showAndWait();
                        } else {
                            Alert alertNoContains = new Alert(Alert.AlertType.INFORMATION);
                            alertNoContains.setContentText("The element " + element + " is not contained in the tree");
                            alertNoContains.showAndWait();
                        }

                    } catch (NumberFormatException nfe) {
                        alert.setContentText("The item entered is not a number");
                    }
                }
            } catch (TreeException e) {
                alert.setContentText("The tree is empty");
                alert.showAndWait();
            }
        }
    }
    //Obtener la altura que tiene el arbol
    @FXML
    public void treeHeightOnAction(ActionEvent actionEvent) throws TreeException {
        if (bTree.isEmpty()){
            alert.setContentText("The tree is empty");
            alert.showAndWait();
        }else{
            try {
                Alert alertHeightTree = new Alert(Alert.AlertType.INFORMATION);
                alertHeightTree.setContentText("The height tree is " + bTree.height());
                alertHeightTree.showAndWait();
            }catch (TreeException tE){
                alert.setContentText("The tree is empty");
                alert.showAndWait();
            }
        }
    }
}