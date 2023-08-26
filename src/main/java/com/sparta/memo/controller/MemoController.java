package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class MemoController {
    //DB를 배우지 않았기 때문에 컬렉션 으로 대체
    private final Map<Long, Memo> memoList = new HashMap<>();

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        // RequestDto -> Entity
        // 클라이언트에서 받아온 데이터가 requestDto 이기 때문에 생성자를 만들어준다.
        Memo memo = new Memo(requestDto);

        // Memo Max ID Check
        // 0보다 크다면 memoList의 key 중에 max 값을 가져온다. 거기에 +1 없다면 1을 넣는다.
        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        // DB 저장
        memoList.put(maxId, memo);

        // Entity -> ResponseDto 로 변경
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);

        return memoResponseDto;
    }

    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        // Map To List
        List<MemoResponseDto> responseList = memoList.values().stream() // List.values()를 하면 여러개가 나오는지 그것을 .stream()이 하나씩 for문처럼 돌려준다
                .map(MemoResponseDto::new).toList(); // map은 데이터를 하나씩 MemoResponseDto 이 생성자를(매개변수로 memo를 받았기 때문에 가능) 생성해서 .toList()가 responseList에 리스트로 만들어준다.
        // stream이 어렵다면 for문으로도 가능하다.

        return responseList;
    }
}
