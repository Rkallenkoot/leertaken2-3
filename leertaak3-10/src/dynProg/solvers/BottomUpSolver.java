package dynProg.solvers;

import dynProg.Solver;

public class BottomUpSolver implements Solver {


    @Override
    public boolean solve(int[] numbers, int sum) {
        // using boolean matrix for DP
        boolean dp[][] = new boolean[numbers.length+1][sum+1];  // +1 in row and column


        // if the length of the array is variable (and sum is 0) then fill TRUE, since the SUM=0
        for(int row = 0;row < dp.length; row++){
            dp[row][0] = true; // NOTE: dp[length=VARIABLE][sum=0], thus we satisfy the condition where length is VARIABLE

        }

        // if the SUM is variable and length is 0 then FALSE, since (sum=variable && length=0)
        for(int column = 1;column < dp[0].length; column++){
            dp[0][column] = false;
        }

        for(int i = 1;i < dp.length; i++){
            for(int j = 1;j < dp[0].length; j++){
                dp[i][j] = dp[i - 1][j];
                if(j >= numbers[i - 1]){
                    dp[i][j] = dp[i][j] || dp[i-1][j - numbers[i - 1]];
                }

            }
        }

        return dp[numbers.length][sum];
    }
}
