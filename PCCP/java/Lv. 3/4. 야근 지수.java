// 250630 - 우선순위큐
// 문제 설명
// 회사원 Demi는 가끔은 야근을 하는데요, 야근을 하면 야근 피로도가 쌓입니다. 야근 피로도는 야근을 시작한 시점에서 남은 일의 작업량을 제곱하여 더한 값입니다. 
// Demi는 N시간 동안 야근 피로도를 최소화하도록 일할 겁니다.Demi가 1시간 동안 작업량 1만큼을 처리할 수 있다고 할 때, 
// 퇴근까지 남은 N 시간과 각 일에 대한 작업량 works에 대해 야근 피로도를 최소화한 값을 리턴하는 함수 solution을 완성해주세요.

// 제한 사항
// works는 길이 1 이상, 20,000 이하인 배열입니다.
// works의 원소는 50000 이하인 자연수입니다.
// n은 1,000,000 이하인 자연수입니다.
// 입출력 예
// works	n	result
// [4, 3, 3]	4	12
// [2, 1, 2]	1	6
// [1,1]	3	0
// 입출력 예 설명
// 입출력 예 #1
// n=4 일 때, 남은 일의 작업량이 [4, 3, 3] 이라면 야근 지수를 최소화하기 위해 4시간동안 일을 한 결과는 [2, 2, 2]입니다. 이 때 야근 지수는 22 + 22 + 22 = 12 입니다.

// 입출력 예 #2
// n=1일 때, 남은 일의 작업량이 [2,1,2]라면 야근 지수를 최소화하기 위해 1시간동안 일을 한 결과는 [1,1,2]입니다. 야근지수는 12 + 12 + 22 = 6입니다.

// 입출력 예 #3

// 남은 작업량이 없으므로 피로도는 0입니다.


// 일이 늘어나는 것을 고려하지 못하고 세개의 합으로 구했다. -> 우선순위 큐로 풀기
// 내 답(오답)
import java.util.*;

class Solution {
    public long solution(int n, int[] works) {
        long answer = 0;

        // 전체 작업량 합계 계산
        int total = 0;
        for (int w : works) {
            total += w;
        }

        // 야근 시간 n이 전체 작업량보다 많거나 같으면 피로도는 0
        if (n >= total) return 0;

        // 줄이고 남은 총 작업량
        int remain = total - n;

        // 공평하게 분배할 몫과 나머지
        int size = works.length;
        int avg = remain / size;
        int mod = remain % size;

        // 새로운 배열로 분배된 작업량 구성
        int[] balanced = new int[size];
        for (int i = 0; i < size; i++) {
            balanced[i] = avg + (i < mod ? 1 : 0);
        }

        // 피로도 계산: 제곱합
        for (int i = 0; i < size; i++) {
            answer += (long) balanced[i] * balanced[i];
        }

        return answer;
    }
}
////////////////////////////////////////////////////////////////////////////////
// 정답
import java.util.*;  // 우선순위 큐 등 유틸리티 클래스 사용

class Solution {
    public long solution(int n, int[] works) {
        // 1. 최대 힙(내림차순 우선순위 큐) 생성: 큰 작업량부터 줄일 수 있게
        PriorityQueue<Integer> pq = new PriorityQueue<>(Collections.reverseOrder());

        // 2. 모든 작업량을 큐에 삽입
        for (int w : works) {
            pq.offer(w);  // offer는 add와 동일하지만, 반환값 있음
        }

        // 3. n번 동안 작업량을 줄임 (1초에 하나씩만 가능)
        while (n > 0 && !pq.isEmpty()) {
            int max = pq.poll();  // 현재 가장 큰 작업량 꺼냄

            if (max > 0) {
                pq.offer(max - 1);  // 1 줄인 후 다시 큐에 넣기
            }

            n--;  // 사용한 야근 시간 1초 차감
        }

        // 4. 모든 작업량의 제곱을 누적해서 피로도 계산
        long answer = 0;
        while (!pq.isEmpty()) {
            int w = pq.poll();              // 하나씩 꺼내서
            answer += (long) w * w;         // 제곱한 값을 long으로 누적
        }

        return answer;  // 최소 피로도 반환
    }
}
