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
        dp[0][0] = map[0][0]; //처음 위치
        //아래로만, 오른쪽으로만
        for(int i=1; i<n; i++){
            dp[i][0] = dp[i-1][0] + map[i][0]; //아래로만 움직이는 경우
            
            dp[0][i] = dp[0][i-1] + map[0][i]; //오른쪽으로만 움직이는 경우
        }

        //이후의 값에 대해서는 이전의 왼쪽값이나 위의 값이랑 자신을 더한 값의 최대값을 가져옴
        for(int i=1; i<n; i++){
            for(int j=1; j<n; j++){
                dp[i][j] = Math.max(dp[i-1][j] + map[i][j], dp[i][j-1] + map[i][j]);
            }
        }

        System.out.println(dp[n-1][n-1]);
    }
}