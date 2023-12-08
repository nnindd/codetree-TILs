import java.io.*;
import java.util.*;

public class Main {
    static int n, map[][];
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens;

        n = Integer.parseInt(br.readLine());
        map = new int[n][n];

        for(int i=0; i<n; i++){
            tokens = new StringTokenizer(br.readLine());
            for(int j=0; j<n; j++){
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }

        int answer = 0;

        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                //모든 시작점에 대해서 탐색
                answer = Math.max(answer, bfs(i, j));
            }
        }

        System.out.println(answer);

    }

    static int[][] dir = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    private static int bfs(int x, int y){
        int max = 0;

        Queue<int[]> q = new ArrayDeque<>();
        q.offer(new int[]{x, y, 1});

        boolean[][] v = new boolean[n][n];
        v[x][y] = true;

        while(!q.isEmpty()){
            int[] now = q.poll();

            max = Math.max(max, now[2]);

            for(int d=0; d<4; d++){
                int nx = now[0] + dir[d][0];
                int ny = now[1] + dir[d][1];

                if(!isRange(nx, ny) || v[nx][ny] || map[nx][ny] <= map[now[0]][now[1]]) continue;

                v[nx][ny] = true;
                q.offer(new int[]{nx, ny, now[2] + 1});
            }
        }

        return max;
    }

    private static boolean isRange(int x, int y){
        if(x<0||y<0||x>=n||y>=n) return false;
        return true;
    }
}