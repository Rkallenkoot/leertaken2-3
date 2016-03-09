package dynProg.solvers;

import dynProg.Solver;

import java.util.Arrays;

/**
 * Created by Iris
 */
public class RecursiveSolver implements Solver {

    public boolean solve(int[] set, int sum){
        // base case
        // sum 0 is altijd te maken?
        if(sum == 0){
            return true;
        }
        // Als n 0 is en de sum is nog niet gevonden
        if(set.length == 0){
            return false;
        }

        if(set[set.length-1] > sum){
            return solve(Arrays.copyOf(set, set.length-1), sum);
        }
        int[] newSet = Arrays.copyOf(set, set.length -1);
        return solve(newSet, sum - set[set.length-1]) || solve(newSet, sum);
    }

}
