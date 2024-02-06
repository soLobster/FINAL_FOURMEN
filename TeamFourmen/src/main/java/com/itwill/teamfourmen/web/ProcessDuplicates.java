package com.itwill.teamfourmen.web;

import com.itwill.teamfourmen.dto.person.CombinedCreditsCastDto;
import com.itwill.teamfourmen.dto.person.CombinedCreditsDto;
import com.itwill.teamfourmen.service.PersonService;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class ProcessDuplicates {

    private final PersonService personService;
    private final CombinedCreditsDto combinedCreditsDto;

    public ProcessDuplicates(PersonService personService, CombinedCreditsDto combinedCreditsDto) {
        this.personService = personService;
        this.combinedCreditsDto = combinedCreditsDto;
    }

    public List<CombinedCreditsCastDto> processDuplicates() {
        int combinedCreditsCastSize = combinedCreditsDto.getCast().size();
        List<CombinedCreditsCastDto> processedCombinedCastDtoList = new ArrayList<>();

        for (int i = 0; i < combinedCreditsCastSize; i++) {
            List<CombinedCreditsCastDto> processedDuplicates = processDuplicates(combinedCreditsDto, i);
            processedCombinedCastDtoList.addAll(processedDuplicates);
        }

        return processedCombinedCastDtoList;
    }

//    // 각 인물이 출연한 작품 수 계산해보기...
//    // 중복 제거 처리가 안 된 인물이 출연한 총 작품 수. (캐스트 리스트의 크기)
//    int combinedCreditsCastSize = CombinedCreditsDto.getcast().size();
//
//    // 중복이 제거된 리스트.
//    private List<CombinedCreditsCastDto> processedCombinedCastDtoList = new ArrayList<>();
//
//		for (int i = 0; i < combinedCreditsCastSize; i++) {
//        List<CombinedCreditsCastDto> processedDuplicates = processDuplicates(combinedCreditsDto, i);
//        processedCombinedCastDtoList.addAll(processedDuplicates);
//    }

    private List<CombinedCreditsCastDto> processDuplicates(CombinedCreditsDto dto, int i) {
        boolean isDuplicate = false;
        // 값이 중복되는 인덱스들을 넣기 위한 리스트 선언.
        List<Integer> duplicatedIndex = new ArrayList<>();

        for (int n = i + 1; n < dto.getCast().size(); n++) {
            String nameI = dto.getCast().get(i).getName();
            String originalNameI = dto.getCast().get(i).getOriginalName();
            String titleI = dto.getCast().get(i).getTitle();
            String originalTitleI = dto.getCast().get(i).getOriginalTitle();

            if ((nameI != null && nameI.equals(dto.getCast().get(n).getName())) ||
                    (originalNameI != null && originalNameI.equals(dto.getCast().get(n).getOriginalName())) ||
                    (titleI != null && titleI.equals(dto.getCast().get(n).getTitle())) ||
                    (originalTitleI != null && originalTitleI.equals(dto.getCast().get(n).getOriginalTitle()))) {
                isDuplicate = true;
                duplicatedIndex.add(n);
            } else {
                log.info("The data appears to be empty or the value is null...");
            }
        }

        // 중복이 하나라도 존재한다면...
        if (isDuplicate) {
            return handleDuplicates(dto, duplicatedIndex);
        }

        return new ArrayList<>(dto.getCast()); // 중복이 제거된 리스트를 리턴.
    }

    private List<CombinedCreditsCastDto> handleDuplicates(CombinedCreditsDto combinedCreditsDto, List<Integer> duplicatedIndex) {
        // Log the duplicate indices
        log.info("Duplicate indices: {}", duplicatedIndex);

        // Create a copy of the original list
        List<CombinedCreditsCastDto> modifiedList = new ArrayList<>(combinedCreditsDto.getCast());

        // Remove duplicates
        for (int i = duplicatedIndex.size() - 1; i >= 0; i--) {
            int indexToRemove = duplicatedIndex.get(i);
            if (indexToRemove < modifiedList.size()) {
                modifiedList.remove(indexToRemove);
            }
        }

        return modifiedList;
    }
}
