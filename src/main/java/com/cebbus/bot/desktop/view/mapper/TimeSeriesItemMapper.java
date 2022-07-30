package com.cebbus.bot.desktop.view.mapper;

import com.cebbus.bot.api.dto.IndicatorValueDto;
import org.jfree.data.time.RegularTimePeriod;
import org.jfree.data.time.TimeSeriesDataItem;

import java.util.List;
import java.util.stream.Collectors;

public class TimeSeriesItemMapper {

    private TimeSeriesItemMapper() {
    }

    public static TimeSeriesDataItem dtoToItem(IndicatorValueDto indicatorValue) {
        RegularTimePeriod period = OhlcItemMapper.convertTimeToPeriod(indicatorValue.getDateTime());
        return new TimeSeriesDataItem(period, indicatorValue.getValue());
    }

    public static List<TimeSeriesDataItem> dtoToItem(List<IndicatorValueDto> indicatorValueList) {
        return indicatorValueList.stream().map(TimeSeriesItemMapper::dtoToItem).collect(Collectors.toList());
    }
}
