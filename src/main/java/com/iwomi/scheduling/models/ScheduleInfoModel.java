package com.iwomi.scheduling.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleInfoModel implements Serializable {
    private int totalFireCount;
    private int remainingFireCount;
    private Map<String, String> data;
}
