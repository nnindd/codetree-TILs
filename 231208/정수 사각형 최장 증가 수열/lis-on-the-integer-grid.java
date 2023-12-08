import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens;

        int n = Integer.parseInt(br.readLine());
        int[][] map = new int[n][n];
        ArrayList<int[]> list = new ArrayList<>();

        int[][] dp = new int[n][n];
        for(int i=0; i<n; i++){
            Arrays.fill(dp[i], 1);
        }

        for(int i=0; i<n; i++){
            tokens = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(tokens.nextToken());
                list.add(new int[]{i, j, map[i][j]});
            }
        }

        //작은 칸부터 4방 탐색해서 최대로 갈 수 있는 길이 선택
        Collections.sort(list, (a, b) -> a[2] - b[2]);

        int[][] dir = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        int max = 0;
        for(int i=0; i<list.size(); i++){
            int[] now = list.get(i);

            for(int d=0; d<4; d++){
                int nx = now[0] + dir[d][0];
                int ny = now[1] + dir[d][1];

                if(!isRange(nx, ny, n) || map[nx][ny] <= map[now[0]][now[1]]) continue;

                dp[nx][ny] = Math.max(dp[nx][ny], dp[now[0]][now[1]] + 1);

                max = Math.max(max, dp[nx][ny]);
            }
        }

        System.out.println(max);

    }
    private static boolean isRange(int x, int y, int n){
        if(x<0||y<0||x>=n||y>=n) return false;
        return true;
    }
}