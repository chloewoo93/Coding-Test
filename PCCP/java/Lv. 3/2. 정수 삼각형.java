// 250625 - 동적 계획법
//    7
//   3 8
//  8 1 0
// 2 7 4 4   
//4 5 2 6 5
// 위와 같은 삼각형의 꼭대기에서 바닥까지 이어지는 경로 중, 거쳐간 숫자의 합이 가장 큰 경우를 찾아보려고 합니다. 
// 아래 칸으로 이동할 때는 대각선 방향으로 한 칸 오른쪽 또는 왼쪽으로만 이동 가능합니다. 예를 들어 3에서는 그 아래칸의 8 또는 1로만 이동이 가능합니다.

// 삼각형의 정보가 담긴 배열 triangle이 매개변수로 주어질 때, 거쳐간 숫자의 최댓값을 return 하도록 solution 함수를 완성하세요.

// 제한사항
// 삼각형의 높이는 1 이상 500 이하입니다.
// 삼각형을 이루고 있는 숫자는 0 이상 9,999 이하의 정수입니다.
// 입출력 예
// triangle	result
// [[7], [3, 8], [8, 1, 0], [2, 7, 4, 4], [4, 5, 2, 6, 5]]	30


// DFS + 재귀로 풀려고 생각했으나.. 효율성 면에서 돌아가지 않음
class Solution {
    int answer = 0;

    public int solution(int[][] triangle) {
        dfs(1, 0, triangle, triangle[0][0]);
        dfs(1, 1, triangle, triangle[0][0]);
        return answer;
    }

    private void dfs(int row, int col, int[][] triangle, int sum) {
        sum += triangle[row][col];

        if (row == triangle.length - 1) {
            answer = Math.max(answer, sum);
            return;
        }

        dfs(row + 1, col, triangle, sum);     // 왼쪽 자식
        dfs(row + 1, col + 1, triangle, sum); // 오른쪽 자식
    }
}
/////////////////////////////////////////////////////////////////////////////////////////
// 동적 계획법 ver. (공부하기!)
class Solution {
    public int solution(int[][] triangle) {
        // triangle을 밑에서부터 위로 업데이트
        for (int i = triangle.length - 2; i >= 0; i--) {
            for (int j = 0; j < triangle[i].length; j++) {
                // 아래 줄에서 선택 가능한 두 경로 중 최대값 더함
                triangle[i][j] += Math.max(triangle[i + 1][j], triangle[i + 1][j + 1]);
            }
        }

        // 최종적으로 맨 위가 최대 경로 합이 됨
        return triangle[0][0];
    }
}
