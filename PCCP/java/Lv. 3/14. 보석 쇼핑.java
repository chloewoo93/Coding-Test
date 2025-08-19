// 문제 설명
// [본 문제는 정확성과 효율성 테스트 각각 점수가 있는 문제입니다.]

// 개발자 출신으로 세계 최고의 갑부가 된 어피치는 스트레스를 받을 때면 이를 풀기 위해 오프라인 매장에 쇼핑을 하러 가곤 합니다.
// 어피치는 쇼핑을 할 때면 매장 진열대의 특정 범위의 물건들을 모두 싹쓸이 구매하는 습관이 있습니다.
// 어느 날 스트레스를 풀기 위해 보석 매장에 쇼핑을 하러 간 어피치는 이전처럼 진열대의 특정 범위의 보석을 모두 구매하되 특별히 아래 목적을 달성하고 싶었습니다.
// 진열된 모든 종류의 보석을 적어도 1개 이상 포함하는 가장 짧은 구간을 찾아서 구매

// 예를 들어 아래 진열대는 4종류의 보석(RUBY, DIA, EMERALD, SAPPHIRE) 8개가 진열된 예시입니다.

// 진열대 번호	1	2	3	4	5	6	7	8
// 보석 이름	DIA	RUBY	RUBY	DIA	DIA	EMERALD	SAPPHIRE	DIA
// 진열대의 3번부터 7번까지 5개의 보석을 구매하면 모든 종류의 보석을 적어도 하나 이상씩 포함하게 됩니다.

// 진열대의 3, 4, 6, 7번의 보석만 구매하는 것은 중간에 특정 구간(5번)이 빠지게 되므로 어피치의 쇼핑 습관에 맞지 않습니다.

// 진열대 번호 순서대로 보석들의 이름이 저장된 배열 gems가 매개변수로 주어집니다. 이때 모든 보석을 하나 이상 포함하는 가장 짧은 구간을 찾아서 return 하도록 solution 함수를 완성해주세요.
// 가장 짧은 구간의 시작 진열대 번호와 끝 진열대 번호를 차례대로 배열에 담아서 return 하도록 하며, 만약 가장 짧은 구간이 여러 개라면 시작 진열대 번호가 가장 작은 구간을 return 합니다.

// [제한사항]
// gems 배열의 크기는 1 이상 100,000 이하입니다.
// gems 배열의 각 원소는 진열대에 나열된 보석을 나타냅니다.
// gems 배열에는 1번 진열대부터 진열대 번호 순서대로 보석이름이 차례대로 저장되어 있습니다.
// gems 배열의 각 원소는 길이가 1 이상 10 이하인 알파벳 대문자로만 구성된 문자열입니다.
// 입출력 예
// gems	result
// ["DIA", "RUBY", "RUBY", "DIA", "DIA", "EMERALD", "SAPPHIRE", "DIA"]	[3, 7]
// ["AA", "AB", "AC", "AA", "AC"]	[1, 3]
// ["XYZ", "XYZ", "XYZ"]	[1, 1]
// ["ZZZ", "YYY", "NNNN", "YYY", "BBB"]	[1, 5]
// 입출력 예에 대한 설명
// 입출력 예 #1
// 문제 예시와 같습니다.

// 입출력 예 #2
// 3종류의 보석(AA, AB, AC)을 모두 포함하는 가장 짧은 구간은 [1, 3], [2, 4]가 있습니다.
// 시작 진열대 번호가 더 작은 [1, 3]을 return 해주어야 합니다.

// 입출력 예 #3
// 1종류의 보석(XYZ)을 포함하는 가장 짧은 구간은 [1, 1], [2, 2], [3, 3]이 있습니다.
// 시작 진열대 번호가 가장 작은 [1, 1]을 return 해주어야 합니다.

// 입출력 예 #4
// 4종류의 보석(ZZZ, YYY, NNNN, BBB)을 모두 포함하는 구간은 [1, 5]가 유일합니다.
// 그러므로 [1, 5]를 return 해주어야 합니다.

// ※ 공지 - 2020년 7월 21일 테스트케이스가 추가되었습니다.

// 내풀이 - 효율성 통과를 못함ㅋ
import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        // 전체 보석 종류 수
        Set<String> setTotal = new LinkedHashSet<>();
        for (int i = 0; i < gems.length; i++) {
            setTotal.add(gems[i]);
        }
        int totalKinds = setTotal.size();

        int chayi = Integer.MAX_VALUE; // 최소 길이(차이)
        int startAns = 0, endAns = 0;  // 0-based 보관

        for (int i = 0; i < gems.length; i++) {
            Set<String> setAll = new LinkedHashSet<>();
            for (int j = i; j < gems.length; j++) {
                setAll.add(gems[j]);

                // 모든 종류를 포함하는 첫 j에서만 갱신(이 i에 대한 최소 구간)
                if (setAll.size() == totalKinds) {
                    int len = j - i;
                    if (len < chayi) {
                        chayi = len;
                        startAns = i;
                        endAns = j;
                    }
                    break; // 이 i로 더 늘리면 길이만 커짐
                }
            }
        }

        // 문제는 1-based 인덱스 요구
        return new int[]{ startAns + 1, endAns + 1 };
    }
}


// GPT 풀이
import java.util.*;

class Solution {
    public int[] solution(String[] gems) {
        // 1) 필요한 보석 종류 수
        int need = new HashSet<>(Arrays.asList(gems)).size();

        // 2) 슬라이딩 윈도우
        Map<String, Integer> cnt = new HashMap<>();
        int left = 0, right = 0, distinct = 0;

        int bestL = 0, bestR = gems.length - 1; // 최소 구간(0-based)
        boolean found = false;

        while (true) {
            // 아직 모든 종류 못 채웠으면 오른쪽 확장
            if (distinct < need) {
                if (right == gems.length) break;           // 더 이상 확장 불가
                cnt.put(gems[right], cnt.getOrDefault(gems[right], 0) + 1);
                if (cnt.get(gems[right]) == 1) distinct++; // 새 종류 추가됨
                right++;
            } else {
                // 모든 종류 포함 → 최소 길이 갱신 (tie 시 더 앞쪽이 자동 선택)
                if (!found || (right - left - 1) < (bestR - bestL)) {
                    bestL = left;
                    bestR = right - 1;
                    found = true;
                }
                // 왼쪽 좁혀서 더 줄일 수 있나 시도
                cnt.put(gems[left], cnt.get(gems[left]) - 1);
                if (cnt.get(gems[left]) == 0) distinct--;  // 한 종류 빠짐
                left++;
            }
        }

        // 문제 규칙: 1-based 반환
        return new int[]{ bestL + 1, bestR + 1 };
    }
}
