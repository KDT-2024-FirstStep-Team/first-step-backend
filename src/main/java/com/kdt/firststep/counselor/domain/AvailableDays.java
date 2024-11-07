package com.kdt.firststep.counselor.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AvailableDays {
    WEEKDAY("평일"),
    WEEKEND("주말"),
    ALL("모든요일");

    private final String day;

    // DB에 저장될 때는 day 값(한글)으로 저장됨
    @Override
    public String toString() {
        return day;
    }
}