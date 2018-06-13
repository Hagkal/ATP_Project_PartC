package View;

import algorithms.mazeGenerators.Maze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Display {

    public void display(Object... o){
        if (!(o[0] instanceof Maze))
            return;

        Maze m = (Maze) o[0];
        int[][] maze = m.getMaze();

        double canvasHeight = getHeight();
        double canvasWidth = getWidth();
        double cellHeight = canvasHeight / maze.length;
        double cellWidth = canvasWidth / maze[0].length;

        try {
            //Image wallImage = new Image(new FileInputStream(ImageFileNameWall.get()));
            //Image characterImage = new Image(new FileInputStream(ImageFileNameCharacter.get()));

            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, getWidth(), getHeight());

            //Draw Maze
            gc.setFill(Color.BLACK);
            for (int i = 0; i < maze.length; i++) {
                for (int j = 0; j < maze[i].length; j++) {
                    if (maze[i][j] == 1) {
                        gc.fillRect(j * cellWidth, i * cellHeight, cellWidth, cellHeight);
                        //gc.drawImage(wallImage, i * cellHeight, j * cellWidth, cellHeight, cellWidth);
                    }
                }
            }

            // draw finish position
            gc.setFill(Color.GOLD);
            gc.fillRect(m.getGoalPosition().getColumnIndex()*cellWidth,
                    m.getGoalPosition().getRowIndex()*cellHeight,
                    cellWidth, cellHeight);


            //gc.drawImage(characterImage, characterPositionColumn * cellHeight, characterPositionRow * cellWidth, cellHeight, cellWidth);
        } catch (Exception e) {
            //e.printStackTrace();
        }
    }
}
