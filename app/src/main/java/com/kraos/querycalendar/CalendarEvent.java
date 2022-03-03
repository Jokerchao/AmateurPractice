package com.kraos.querycalendar;

/**
 * Description:
 * Author:Kraos dengbch@crop.21cn.com
 * Date:2019/11/15 10:47
 */
public class CalendarEvent {
    private String nameOfEvent;
    private String startDates;
    private String endDates;
    private String descriptions;

    public String getNameOfEvent() {
        return nameOfEvent;
    }

    public void setNameOfEvent(String nameOfEvent) {
        this.nameOfEvent = nameOfEvent;
    }

    public String getStartDates() {
        return startDates;
    }

    public void setStartDates(String startDates) {
        this.startDates = startDates;
    }

    public String getEndDates() {
        return endDates;
    }

    public void setEndDates(String endDates) {
        this.endDates = endDates;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    @Override
    public String toString() {
        return "CalendarEvent{" +
                "nameOfEvent='" + nameOfEvent + '\'' +
                ", startDates='" + startDates + '\'' +
                ", endDates='" + endDates + '\'' +
                ", descriptions='" + descriptions + '\'' +
                '}';
    }
}
