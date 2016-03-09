package dynProg.solvers;

import dynProg.Solver;

public class TopDownSolver implements Solver {

    private Boolean[][] solution;

    @Override
    public boolean solve(int[] set, int sum) {
        solution = new Boolean[set.length][sum + 1];
        return findSolution(set, sum);
    }

    private  boolean findSolution(int[] set, int sum){
        if( sum == 0 ) {
            return true;
        }
        if( set.length == 0 || sum < 0){
            return false;
        }
        if(solution[set.length - 1][sum] == null){
            solution[set.length - 1][sum] = doSolve(set, sum);
        }
        return solution[set.length - 1][sum];
    }

    private boolean doSolve(int[] set, int sum){
        int n = set.length - 1;
        int[] shorterNumbersCopy = new int[n];
        for(int index = 0; index < n; index++){
            shorterNumbersCopy[index] = set[index];
        }
        return findSolution(shorterNumbersCopy, sum)
                || findSolution(shorterNumbersCopy, sum - set[n]);
    }
}
