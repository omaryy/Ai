package com.company;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Main {
    public static final String TEXT_RESET = "\u001B[0m";
    public static final String TEXT_PURPLE = "\u001B[35m";
    public static final String TEXT_YELLOW = "\u001B[33m";


    public static void main(String[] args) {
        System.out.println(TEXT_YELLOW+"Starting DFS"+TEXT_RESET);

        LocalDateTime startTime = LocalDateTime.now();
        DFS bfs = new DFS("1","2","5","3","4","0","6","7","8");
        boolean success = bfs.solve();
        LocalDateTime endTime = LocalDateTime.now();

        if(success)
        {

            System.out.println(TEXT_PURPLE+"Path to goal: " +TEXT_RESET+ bfs.getCurrentNode().getPathToGoal());
            System.out.println(TEXT_PURPLE+"Cost to goal: " +TEXT_RESET+ bfs.getCurrentNode().getCostOfPath());
            System.out.println(TEXT_PURPLE+"Nodes expanded: "+TEXT_RESET+bfs.getvisitednodes().size());
            System.out.println(TEXT_PURPLE+"Search depth: " +TEXT_RESET+ bfs.getCurrentNode().getSearchDepth());

            long seconds = startTime.until( endTime, ChronoUnit.SECONDS);
            if(seconds == 0)
            {
                long milliSeconds = startTime.until( endTime, ChronoUnit.MILLIS);
                System.out.println(TEXT_PURPLE+"running time:  "+TEXT_RESET+ milliSeconds+" MILLISECONDS");            }
            else
            {
                System.out.println("running time:  "+ seconds+" SECONDS");
            }



        }
        else
        {
            System.err.println("no Answer found!");
        }


    }

}
