package com.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class DFS {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_RED = "\u001B[31m";
    public static final String TEXT_BLUE = "\u001B[34m";
    public static final String TEXT_YELLOW = "\u001B[33m";
    Integer[][] initialState = new Integer[3][3];
    Node root;
    Node curNode;
    Integer[][] goalState = {{0,1,2},{3,4,5},{6,7,8}};
    Stack<Node> fringe = new Stack<Node>();
    List<Node> visitednodes = new ArrayList<>();

    public DFS(String... state)
    {
        int index = 0;
        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {

                if(state[index].equals("0")) {
                    root = new Node(0, i, j,initialState,"",0);
                }
                initialState[i][j] = Integer.parseInt(state[index++]);

            }
        }
        root.setState(initialState);
    }

    public boolean solve()
    {
        boolean solved = false;
        fringe.add(root);

        while(!fringe.isEmpty())
        {

            curNode = fringe.pop();
            visitednodes.add(curNode);
            if(gloalReached())
            {


                System.out.println(TEXT_YELLOW+"Current state after: "+ curNode.getPathToGoal() +TEXT_RESET);
                for(int k = 0; k < 3; k++){


                    printCurrentState(k);
                }
                return true;
            }
            else
            {
                System.out.println(TEXT_YELLOW+"Current state after: "+ curNode.getPathToGoal() +TEXT_RESET);

                for(int k = 0; k < 3; k++){

                    printCurrentState(k);
                }
            }


            for(Node neighbor : curNode.getNeighbors())
            {
                if(visitednodes.indexOf(neighbor) == -1 && fringe.contains(neighbor) == false)
                {
                    fringe.add(neighbor);

                }
            }
            curNode.setNeighbors(null);

        }





        return solved;
    }

    private boolean gloalReached() {
        boolean success = true;

        for(int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3; j++)
            {
                if(!curNode.getState()[i][j].equals(goalState[i][j]))
                {
                    success = false;
                    break;
                }

            }
            if(success == true)
            {
                break;
            }

        }

        return success;
    }

    private void printCurrentState(int row) {





        for(int i = 0;i <  7 * 3 ;i++){
            System.out.print(TEXT_BLUE+"-"+TEXT_RESET);
        }
        System.out.println(TEXT_BLUE+"-"+TEXT_RESET);

        for(int i = 1;i <= 3;i++){
            System.out.printf(TEXT_BLUE+"|"+TEXT_RESET +TEXT_RED+" %4d "+TEXT_RESET,curNode.getState()[row][i-1]);
        }
        System.out.println(TEXT_BLUE+"|"+TEXT_RESET);


        if(row == 3 -  1){

            for(int i = 0;i <  7 * 3 ;i++){
                System.out.print(TEXT_BLUE+"-"+TEXT_RESET);
            }
            System.out.println(TEXT_BLUE+"-"+TEXT_RESET);

        }
    }

    public Integer[][] getInitialState() {
        return initialState;
    }

    public void setInitialState(Integer[][] initialState) {
        this.initialState = initialState;
    }

    public Node getRoot() {
        return root;
    }

    public void setRoot(Node root) {
        this.root = root;
    }

    public Node getCurrentNode() {
        return curNode;
    }

    public void setCurrentNode(Node currentNode) {
        this.curNode = currentNode;
    }

    public Integer[][] getGoalState() {
        return goalState;
    }

    public void setGoalState(Integer[][] goalState) {
        this.goalState = goalState;
    }

    public Stack<Node> getFringe() {
        return fringe;
    }

    public void setFringe(Stack<Node> fringe) {
        this.fringe = fringe;
    }

    public List<Node> getvisitednodes() {
        return visitednodes;
    }

    public void setvisitednodes(List<Node> exploredNodes) {
        this.visitednodes = exploredNodes;
    }




}