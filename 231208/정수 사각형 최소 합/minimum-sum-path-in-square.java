import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens;

        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];

        for(int i=0; i<n; i++){
            tokens = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }

        int[][] dp = new int[n][n];

        //init
        dp[0][n-1] = map[0][n-1];

        //왼쪽으로만 가는 경우와 밑으로만 가는 경우
        for(int i=n-2; i>=0; i--){
            dp[0][i] = dp[0][i+1] + map[0][i]; //왼쪽
        }
        for(int i=1; i<n; i++){
            dp[i][n-1] = dp[i-1][n-1] + map[i][n-1]; //오른쪽
        }

        //dp[i][j] = max(오른쪽+현재, 위+현재)
        for(int i=1; i<n; i++){
            for(int j=n-2; j>=0; j--){
                dp[i][j] = Math.min(dp[i][j+1], dp[i-1][j]) + map[i][j];
            }
        }

        System.out.println(dp[n-1][0]);
    }
}