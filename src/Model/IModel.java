package Model;

import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.beans.Observable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Observer;

public interface IModel {

    // init Model Functionality
    public void initModel();

    // generate maze
    public void generateMaze(int height, int width);

    // get generated maze
    public Maze getMaze();

    // calculating solution
    public void findSolution();

    // returning solution
    public Solution getSolution();

    // saving current game state
    public void saveGame();

    // load saved game state
    public void loadGame();

    // defining settings
    //public void setNewSettings(String[] args);

    // making a proper exit
    public void exitGame();

    // moving the character
    public void movePlayer(KeyCode step);

    /* should add movePlayer method with hover mouse */


    public int getPlayerRow();

    public int getPlayerCol();
}
