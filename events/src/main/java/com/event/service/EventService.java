package com.event.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class EventService {

    public static double getEventBudget(int participantsNumber, double costPerParticipant){
        if(participantsNumber <= 0){
            return 0;
        }

        if(costPerParticipant <= 0){
            throw new IllegalArgumentException("There should be a cost per participant");
        }

        return participantsNumber * costPerParticipant;
    }

    public static List<String> getParticipantsByName(List<String> participantsList, String searchText){
        if(participantsList == null){
            throw new IllegalArgumentException("A valid list of participants is expected");
        }

        if(searchText.isEmpty()){
            return Collections.emptyList();
        }

        return participantsList.stream().filter(participant -> participant.toLowerCase().startsWith(searchText.toLowerCase())).toList();
    }

    public static boolean validateEventDate(String startDate, String endDate){
        if(startDate == null || endDate == null){
            throw new IllegalArgumentException("Start date and end date must not be null.");
        }

        if(startDate.isEmpty() || endDate.isEmpty()){
            throw new IllegalArgumentException("Start date and end date must not be empty.");
        }

        LocalDate formattedStartDate;
        LocalDate formattedEndDate;

        try{
            formattedStartDate = LocalDate.parse(startDate);
            formattedEndDate = LocalDate.parse(endDate);
        }catch(Exception err){
            throw new IllegalArgumentException("Dates must be in the format 'yyyy-MM-dd'.", err);
        }

        if(formattedStartDate.isAfter(formattedEndDate)){
            throw new IllegalArgumentException("A start date and end date is expected");
        }

        return true;
    }

    public static List<String> getParticipantsReport(Map<String, Boolean> participantsMap){
        if(participantsMap == null){
            throw new IllegalArgumentException("The participantsMap must not be null");
        }

        List<String> confirmedParticipants = new ArrayList<>();

        participantsMap.forEach((key, value) -> {
            if(value){confirmedParticipants.add(key);}
        });

        return confirmedParticipants;
    }

    public static boolean eventIsFull(Map<String, Boolean> participantsMap, int maxParticipants){
        if(participantsMap == null){
            throw new IllegalArgumentException("The participantsMap must not be null");
        }

        long confirmedParticipants = participantsMap.values().stream().filter(Boolean::booleanValue).count();

        return confirmedParticipants >= maxParticipants;
    }
}