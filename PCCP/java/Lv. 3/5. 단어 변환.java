// 250701 - DFS/BFS
// 문제 설명
// 두 개의 단어 begin, target과 단어의 집합 words가 있습니다. 아래와 같은 규칙을 이용하여 begin에서 target으로 변환하는 가장 짧은 변환 과정을 찾으려고 합니다.

// 1. 한 번에 한 개의 알파벳만 바꿀 수 있습니다.
// 2. words에 있는 단어로만 변환할 수 있습니다.
// 예를 들어 begin이 "hit", target가 "cog", words가 ["hot","dot","dog","lot","log","cog"]라면 "hit" -> "hot" -> "dot" -> "dog" -> "cog"와 같이 4단계를 거쳐 변환할 수 있습니다.

// 두 개의 단어 begin, target과 단어의 집합 words가 매개변수로 주어질 때, 최소 몇 단계의 과정을 거쳐 begin을 target으로 변환할 수 있는지 return 하도록 solution 함수를 작성해주세요.

// 제한사항
// 각 단어는 알파벳 소문자로만 이루어져 있습니다.
// 각 단어의 길이는 3 이상 10 이하이며 모든 단어의 길이는 같습니다.
// words에는 3개 이상 50개 이하의 단어가 있으며 중복되는 단어는 없습니다.
// begin과 target은 같지 않습니다.
// 변환할 수 없는 경우에는 0를 return 합니다.
// 입출력 예
// begin	target	words	return
// "hit"	"cog"	["hot", "dot", "dog", "lot", "log", "cog"]	4
// "hit"	"cog"	["hot", "dot", "dog", "lot", "log"]	0
// 입출력 예 설명
// 예제 #1
// 문제에 나온 예와 같습니다.

// 예제 #2
// target인 "cog"는 words 안에 없기 때문에 변환할 수 없습니다.

// 너무 어렵다 ㅜㅜ

import java.util.*;

class Solution {
    public int solution(String begin, String target, String[] words) {
        // words에 있는 단어들을 방문했는지 체크하는 배열
        boolean[] visited = new boolean[words.length];

        // 각 단어가 몇 단계(깊이)에서 도달했는지 저장하는 배열
        int[] depth = new int[words.length];

        // BFS 탐색을 위한 큐: words 배열의 인덱스를 담는다
        Queue<Integer> queue = new LinkedList<>();

        // [1단계] 시작 단어(begin)와 한 글자 차이 나는 단어들을 먼저 큐에 넣는다
        for (int i = 0; i < words.length; i++) {
            if (isOneLetterDiff(begin, words[i])) {
                queue.offer(i);        // i번째 단어를 탐색 대상으로 추가
                visited[i] = true;     // 방문 처리
                depth[i] = 1;          // begin에서 한 번 바꾼 상태이므로 depth는 1
            }
        }

        // [2단계] BFS 탐색 시작
        while (!queue.isEmpty()) {
            int current = queue.poll();  // 큐에서 인덱스를 꺼냄

            // 현재 단어가 target이면, 정답 반환
            if (words[current].equals(target)) {
                return depth[current];  // 최소 변환 횟수
            }

            // words 전체를 돌면서 아직 방문하지 않았고,
            // 현재 단어와 한 글자 차이 나는 단어를 탐색
            for (int i = 0; i < words.length; i++) {
                if (!visited[i] && isOneLetterDiff(words[current], words[i])) {
                    queue.offer(i);                 // 다음 탐색 대상으로 추가
                    visited[i] = true;              // 방문 처리
                    depth[i] = depth[current] + 1;  // 변환 횟수 +1
                }
            }
        }

        // [3단계] target까지 도달하지 못했으면 0 반환
        return 0;
    }

    // [보조 메서드] 두 단어가 한 글자만 다른지 판단
    private boolean isOneLetterDiff(String a, String b) {
        int diff = 0;

        // 각 자리의 글자가 다른 횟수를 센다
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                diff++;
            }

            // 이미 2글자 이상 다르면 더 볼 필요 없음
            if (diff > 1) return false;
        }

        // 정확히 한 글자만 다를 때만 true
        return diff == 1;
    }
}
