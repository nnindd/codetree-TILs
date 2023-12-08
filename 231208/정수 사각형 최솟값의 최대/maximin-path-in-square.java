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

        //오른쪽, 아래로만 이동해서 최소값 기록
        dp[0][0] = map[0][0];

        for(int i=1; i<n; i++){
            dp[i][0] = Math.min(dp[i-1][0], map[i][0]);
            dp[0][i] = Math.min(dp[0][i-1], map[0][i]);
        }

        for(int i=1; i<n; i++){
            for(int j=1; j<n; j++){
                dp[i][j] = Math.min(Math.max(dp[i-1][j], dp[i][j-1]), map[i][j]);
            }
        }

        System.out.println(dp[n-1][n-1]);
    }
}