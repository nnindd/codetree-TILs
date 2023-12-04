import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.StringTokenizer;

public class Main {
    static class Pos {
        int x, y;
        boolean isOut;

        public Pos(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Pos(int x, int y, boolean isOut) {
            this.x = x;
            this.y = y;
            this.isOut = isOut;
        }
    }

    static int N, M, K, map[][], copy[][], total;
    static Pos exit; //출구 위치
    static int bx, by, boxSize; //회전할 박스 시작행열, 사이즈 저장
    static Pos[] p; //사람들 좌표 저장

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringTokenizer tokens = new StringTokenizer(br.readLine());

        N = Integer.parseInt(tokens.nextToken()); //맵
        M = Integer.parseInt(tokens.nextToken()); //사람
        K = Integer.parseInt(tokens.nextToken()); //게임시간

        map = new int[N][N];
        p = new Pos[M];
        total = 0;

        for (int i = 0; i < N; i++) {
            tokens = new StringTokenizer(br.readLine());
            for (int j = 0; j < N; j++) {
                map[i][j] = Integer.parseInt(tokens.nextToken());
            }
        }

        for (int i = 0; i < M; i++) {
            tokens = new StringTokenizer(br.readLine());
            p[i] = new Pos(Integer.parseInt(tokens.nextToken()) - 1,
                    Integer.parseInt(tokens.nextToken()) - 1,
                    false);
        }

        tokens = new StringTokenizer(br.readLine());
        exit = new Pos(Integer.parseInt(tokens.nextToken()) - 1,
                Integer.parseInt(tokens.nextToken()) - 1);
        //end input

        while (K-- > 0) {
            //모든 참가자가 탈출했는지 확인
            if (isAllOut()) break;

            for (int i = 0; i < M; i++) {
                movePeople(i); //모든 참가자 이동
                //이동했는데 출구인지 확인
                if (p[i].x == exit.x && p[i].y == exit.y) {
                    p[i].isOut = true;
                }
            }

            bx = by = boxSize = 0; //모두 탈출하고 회전시킬 경우
            findRect(); //상자 사이즈 찾음

            rotateRect(); //미로 회전
        }
        
        StringBuilder sb = new StringBuilder();
        sb.append(total).append("\n").append(exit.x+1).append(" ").append(exit.y+1);
        System.out.println(sb);
    }

    private static boolean isAllOut() {
        for (int i = 0; i < M; i++) {
            if (!p[i].isOut) return false;
        }
        return true;
    }

    private static void rotateRect() {
        //맵 복사
        copy = new int[N][];
        for (int i = 0; i < N; i++) {
            copy[i] = Arrays.copyOfRange(map[i], 0, N);
        }

        //복사한 값을 원본에 돌려서 넣기
        for (int i = bx, x = by; i < bx + boxSize; i++, x++) {
            for (int j = by, y = bx + boxSize - 1; j < by + boxSize; j++, y--) {
                map[i][j] = copy[y][x];

                //내구도 감소
                if (isArea(map[i][j], 1, 10))
                    map[i][j]--;
            }
        }

        //출구 위치 변경
        if (isArea(exit.x, bx, bx + boxSize) && isArea(exit.y, by, by + boxSize)) {
            //(0,0) 기준으로 옮김
            int nx = exit.x - bx;
            int ny = exit.y - by;

            //회전시키고 기준점 다시 옮겨줌
            //20 -> 00
            //10 -> 01
            //00 -> 02
            //[x][y] -> [y][size-x-1]
            exit.x = ny + bx;
            exit.y = boxSize - nx - 1 + by;
        }

        //사람 위치 변경
        for (int i = 0; i < M; i++) {
            if (p[i].isOut) continue;

            if (isArea(p[i].x, bx, bx + boxSize) && isArea(p[i].y, by, by + boxSize)) {
                int nx = p[i].x - bx;
                int ny = p[i].y - by;

                p[i].x = ny + bx;
                p[i].y = boxSize - nx - 1 + by;
            }
        }
    }

    private static void findRect() {
        for (int size = 2; size <= N; size++) { //만들 수 있는 사각형
            for (int i = 0; i < N - size + 1; i++) {
                for (int j = 0; j < N - size + 1; j++) {
                    //출구가 있는지 확인
                    if (!(isArea(exit.x, i, i + size) && isArea(exit.y, j, j + size))) {
                        continue;
                    }

                    boolean flag = false;
                    //사람이 있는지 확인
                    for (int m = 0; m < M; m++) {
                        if (p[m].isOut) continue; //출구에 있는 참가자 제외

                        Pos now = p[m];
                        if (i <= now.x && now.x < i + size
                                && j <= now.y && now.y < j + size) {
                            flag = true;
                        }
                    }

                    if (flag) {
                        bx = i;
                        by = j;
                        boxSize = size;
                        return;
                    }

                }//end j

            }//end i

        }//end size
    }

    private static void movePeople(int idx) {
        Pos now = p[idx];

        //이미 출구인 경우
        if (now.isOut) return;

        //행이 다르다면 상하로 움직이기
        if (now.x != exit.x) {
            int nx = now.x;
            int ny = now.y;

            if (nx > exit.x) nx--; //위로 이동
            else nx++; //아래로 이동

            if (isRange(nx, ny) && map[nx][ny] == 0) {
                //움직일 수 있음
                now.x = nx;
                now.y = ny;
                p[idx] = now;
                total++;
                return;
            }
        }

        //열이 다르다면 좌우로 움직이기
        if (now.y != exit.y) {
            int nx = now.x;
            int ny = now.y;

            if (ny > exit.y) ny--; //좌로 이동
            else ny++; //우로 이동

            if (isRange(nx, ny) && map[nx][ny] == 0) {
                now.x = nx;
                now.y = ny;
                p[idx] = now;
                total++;
            }
        }
    }

    private static boolean isArea(int x, int min, int max) {
        if (min <= x && x < max) return true;
        return false;
    }

    private static boolean isRange(int x, int y) {
        if (x < 0 || y < 0 || x >= N || y >= N) return false;
        return true;
    }
}