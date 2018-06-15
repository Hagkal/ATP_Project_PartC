package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.AState;
import algorithms.search.MazeState;
import algorithms.search.Solution;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class SolutionDisplayer extends Display {

    @Override
    public void display(Object... o){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(0, 0, getWidth(), getHeight());
        gc.setFill(Color.RED);

        if (o[1] == null || !(o[1] instanceof Solution) || !(o[0] instanceof Maze))
            return;

        Solution s = (Solution) o[1];
        Maze m = (Maze) o[0];

        int[][] maze = m.getMaze();

        double cellHeight = getHeight() / maze.length;
        double cellWidth = getWidth() / maze[0].length;
        for (AState state :
                s.getSolutionPath()) {
            if (!(state instanceof MazeState))
                return;

            MazeState mState = (MazeState) state;
            int colorRow = mState.getCurrentPosition().getRowIndex();
            int colorCol = mState.getCurrentPosition().getColumnIndex();
            gc.fillRect(colorCol * cellWidth, colorRow * cellHeight, cellWidth, cellHeight);


        }
    }
}
