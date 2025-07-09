// 250709 - 해시
// 문제 설명
// 스트리밍 사이트에서 장르 별로 가장 많이 재생된 노래를 두 개씩 모아 베스트 앨범을 출시하려 합니다. 노래는 고유 번호로 구분하며, 노래를 수록하는 기준은 다음과 같습니다.

// 1) 속한 노래가 많이 재생된 장르를 먼저 수록합니다.
// 2) 장르 내에서 많이 재생된 노래를 먼저 수록합니다.
// 장르 내에서 재생 횟수가 같은 노래 중에서는 고유 번호가 낮은 노래를 먼저 수록합니다.
// 노래의 장르를 나타내는 문자열 배열 genres와 노래별 재생 횟수를 나타내는 정수 배열 plays가 주어질 때, 베스트 앨범에 들어갈 노래의 고유 번호를 순서대로 return 하도록 solution 함수를 완성하세요.

// 제한사항
// genres[i]는 고유번호가 i인 노래의 장르입니다.
// plays[i]는 고유번호가 i인 노래가 재생된 횟수입니다.
// genres와 plays의 길이는 같으며, 이는 1 이상 10,000 이하입니다.
// 장르 종류는 100개 미만입니다.
// 장르에 속한 곡이 하나라면, 하나의 곡만 선택합니다.
// 모든 장르는 재생된 횟수가 다릅니다.
// 입출력 예
// genres	plays	return
// ["classic", "pop", "classic", "classic", "pop"]	[500, 600, 150, 800, 2500]	[4, 1, 3, 0]
// 입출력 예 설명
// classic 장르는 1,450회 재생되었으며, classic 노래는 다음과 같습니다.

// 고유 번호 3: 800회 재생
// 고유 번호 0: 500회 재생
// 고유 번호 2: 150회 재생
// pop 장르는 3,100회 재생되었으며, pop 노래는 다음과 같습니다.

// 고유 번호 4: 2,500회 재생
// 고유 번호 1: 600회 재생
// 따라서 pop 장르의 [4, 1]번 노래를 먼저, classic 장르의 [3, 0]번 노래를 그다음에 수록합니다.

// 장르 별로 가장 많이 재생된 노래를 최대 두 개까지 모아 베스트 앨범을 출시하므로 2번 노래는 수록되지 않습니다.

import java.util.*;

class Solution {
    public int[] solution(String[] genres, int[] plays) {
        // 1. 장르별 총 재생 횟수를 저장할 HashMap
        Map<String, Integer> genreSum = new HashMap<>();
        for (int i = 0; i < genres.length; i++) {
            // 동일 장르의 재생 수를 누적
            genreSum.put(genres[i], genreSum.getOrDefault(genres[i], 0) + plays[i]);
        }

        // 2. 장르를 총 재생 수 기준으로 내림차순 정렬한 Map 생성
        Map<String, Integer> sortedMap = new LinkedHashMap<>();
        genreSum.entrySet().stream()
            .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())  // 내림차순 정렬
            .forEach(e -> sortedMap.put(e.getKey(), e.getValue()));            // 순서 유지해서 넣기

        // 정렬된 장르 순서를 리스트로 저장
        List<String> keys = new ArrayList<>(sortedMap.keySet());

        // 최종 결과로 반환할 곡 인덱스를 저장할 리스트
        List<Integer> result = new ArrayList<>();

        // 3. 각 장르별로 가장 많이 재생된 곡 2개씩 추출
        for (String genre : keys) {
            List<Integer> idxList = new ArrayList<>();

            // 현재 장르에 해당하는 곡들의 인덱스를 모은다
            for (int i = 0; i < genres.length; i++) {
                if (genres[i].equals(genre)) {
                    idxList.add(i);  // 해당 인덱스를 리스트에 저장
                }
            }

            // 곡 인덱스를 재생 수 기준 내림차순 정렬 (동점이면 인덱스 오름차순)
            idxList.sort((i1, i2) -> {
                if (plays[i2] != plays[i1]) return plays[i2] - plays[i1];  // 재생 수 비교
                return i1 - i2;  // 같으면 인덱스 작은 순
            });

            // 상위 1~2개 곡 인덱스를 결과에 추가
            result.add(idxList.get(0));           // 가장 많이 재생된 곡
            if (idxList.size() > 1) {
                result.add(idxList.get(1));       // 두 번째 곡 (있는 경우만)
            }
        }

        // 4. 결과 리스트를 int[] 배열로 변환해 반환
        return result.stream().mapToInt(Integer::intValue).toArray();
    }
}
