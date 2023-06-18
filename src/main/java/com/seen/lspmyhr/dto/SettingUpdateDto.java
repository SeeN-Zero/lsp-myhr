package com.seen.lspmyhr.dto;

import com.seen.lspmyhr.model.Pekerjaan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SettingUpdateDto {
    List<Pekerjaan> pekerjaanListDto;
}
