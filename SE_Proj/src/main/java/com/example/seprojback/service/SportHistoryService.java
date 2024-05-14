package com.example.seprojback.service;

import com.example.seprojback.entity.SportHistory;
import com.example.seprojback.entity.SportTips;

import java.util.List;

public interface SportHistoryService {
    List<SportHistory> getSportHistory(String userId);
}
