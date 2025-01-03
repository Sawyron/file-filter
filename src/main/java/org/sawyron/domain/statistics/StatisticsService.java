package org.sawyron.domain.statistics;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class StatisticsService {
    private final List<StatisticsUi> statisticsUiList;

    public StatisticsService(List<StatisticsUi> statisticsUiList) {
        this.statisticsUiList = new ArrayList<>(statisticsUiList);
    }

    public void show() {
        for (StatisticsUi statisticsUi : statisticsUiList) {
            System.out.println("-----");
            statisticsUi.show();
            System.out.println("-----");
        }
    }
}
