package Model;

import Client.*;
import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Observable;

public class MyModel extends Observable implements IModel {

    private Maze maze = null;
    private Solution solved = null;
    private int playerRow;
    private int playerCol;
    private Server generateServer;
    private Server solveServer;

    @Override
    public void initModel(){
        generateServer = new Server(5400, 3, new ServerStrategyGenerateMaze());
        generateServer.start();
        solveServer = new Server(5600, 3, new ServerStrategySolveSearchProblem());
        solveServer.start();
    }

    @Override
    public void generateMaze(int height, int width) {
        try {
            // some stuff here needs to be changed. like 'read'
            Client me = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                @SuppressWarnings("ResultOfMethodCallIgnored")
                @Override
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        int[] mazeDimensions = new int[]{height, width};

                        toServer.writeObject(mazeDimensions); //send maze dimensions to server
                        toServer.flush();

                        byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
                        InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                        byte[] decompressedMaze = new byte[2600]; //allocating byte[] for the decompressed maze -
                        is.read(decompressedMaze); //Fill decompressedMaze with bytes
                        maze = new Maze(decompressedMaze);
                        //maze.print();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // lets the client operate with server
            me.communicateWithServer();

            // notify when generation is completed
            setChanged();
            notifyObservers();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    @Override
    public void findSolution() {
        try {
            Client me = new Client(InetAddress.getLocalHost(), 5600, new IClientStrategy() {
                @Override
                public void clientStrategy(InputStream inputStream, OutputStream outputStream) {
                    try {
                        ObjectInputStream fromServer = new ObjectInputStream(inputStream);
                        ObjectOutputStream toServer = new ObjectOutputStream(outputStream);
                        toServer.flush();

                        toServer.writeObject(maze);
                        toServer.flush();

                        solved = (Solution) fromServer.readObject();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            // communicating with server for a solution
            me.communicateWithServer();

            // notifying observers (View Model)
            setChanged();
            notifyObservers();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Solution getSolution() {
        return solved;
    }

    @Override
    public void exitGame() {
        generateServer.stop();
        solveServer.stop();
    }

    @Override
    public void movePlayer(KeyCode step) {
        boolean moved = false;
        switch (step){
            case NUMPAD8: // up
                if (maze.isPath(playerRow-1, playerCol))
                    playerRow--;
                moved = true;
                break;
            case NUMPAD2: // down
                if (maze.isPath(playerRow+1, playerCol))
                    playerRow++;
                moved = true;
                break;
            case NUMPAD4: // left
                if (maze.isPath(playerRow, playerCol--))
                    playerCol--;
                moved = true;
                break;
            case NUMPAD6: // right
                if (maze.isPath(playerRow, playerCol++))
                    playerCol++;
                moved = true;
                break;
            case NUMPAD9: // up and right
                if (maze.isPath(playerRow-1, playerCol+1)){
                    playerRow--;
                    playerCol++;
                }
                moved = true;
                break;
            case NUMPAD7: // up and left
                if (maze.isPath(playerRow-1, playerCol-1)){
                    playerRow--;
                    playerCol--;
                }
                moved = true;
                break;
            case NUMPAD3: // down and right
                if (maze.isPath(playerRow+1, playerCol+1)){
                    playerRow++;
                    playerCol++;
                }
                moved = true;
                break;
            case NUMPAD1: // down and left
                if (maze.isPath(playerRow+1, playerCol-1)){
                    playerRow++;
                    playerCol--;
                }
                moved = true;
                break;
        }
        if (moved){
            setChanged();
            notifyObservers();
        }
    }

    @Override
    public int getPlayerRow() {
        return playerRow;
    }

    @Override
    public int getPlayerCol() {
        return playerCol;
    }
}
