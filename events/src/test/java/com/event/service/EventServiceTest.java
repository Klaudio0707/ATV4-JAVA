package com.event.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

public class EventServiceTest {

    private Map<String, Boolean> validParticipantsMap;
    private final Map<String, Boolean> emptyParticipantsMap = Collections.emptyMap();
    private String validStartDate;
    private String validEndDate;
    private int validParticipantsNumber;
    private double validCostPerParticipant;
    private String[] validParticipantsList;
    private String validSearchText;
    private int validMaxParticipants;

    @BeforeEach
    void setUp() {
        validParticipantsMap = new HashMap<>();
        validParticipantsMap.put("Alice", true);
        validParticipantsMap.put("Bob", false);
        validParticipantsMap.put("Charlie", true);

        validStartDate = "2024-11-01";
        validEndDate = "2024-11-10";

        validParticipantsNumber = 3;
        validCostPerParticipant = 100.0;

        validParticipantsList = new String[]{"Alice", "Bob", "Charlie"};
        validSearchText = "A";
        validMaxParticipants = 2;
    }

    @Nested
    class TestGetEventBudget {

        @Test
        void testValidParticipants() {
            double result = EventService.getEventBudget(validParticipantsNumber, validCostPerParticipant);
            System.out.println("Resultado do orçamento do evento (válido): " + result);
            assertEquals(300.0, result, "O custo do evento deve ser 300.");
        }

        @Test
        void testZeroOrNegativeParticipants() {
            double resultZero = EventService.getEventBudget(0, validCostPerParticipant);
            System.out.println("Resultado do orçamento com 0 participantes: " + resultZero);
            assertEquals(0, resultZero, "O custo deve ser 0 quando o número de participantes for 0.");

            double resultNegative = EventService.getEventBudget(-1, validCostPerParticipant);
            System.out.println("Resultado do orçamento com participantes negativos: " + resultNegative);
            assertEquals(0, resultNegative, "O custo deve ser 0 quando o número de participantes for negativo.");
        }

        @Test
        void testInvalidCost() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                EventService.getEventBudget(validParticipantsNumber, -50.0)
            );
            System.out.println("Exceção lançada para custo inválido: " + exception.getMessage());
            assertEquals("There should be a cost per participant", exception.getMessage());
        }
    }

    @Nested
    class TestGetParticipantsByName {

        @Test
        void testValidSearchText() {
            List<String> result = EventService.getParticipantsByName(Arrays.asList(validParticipantsList), validSearchText);
            System.out.println("Participantes encontrados para a busca por '" + validSearchText + "': " + result);
            assertTrue(result.contains("Alice"), "Should return 'Alice' when 'A' is searched.");
        }

        @Test
        void testEmptySearchText() {
            List<String> result = EventService.getParticipantsByName(Arrays.asList(validParticipantsList), "");
            System.out.println("Resultado com texto de busca vazio: " + result);
            assertTrue(result.isEmpty(), "The list should be empty when the searchText is empty.");
        }

        @Test
        void testNullParticipantsList() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                EventService.getParticipantsByName(null, validSearchText)
            );
            System.out.println("Exceção lançada para lista nula: " + exception.getMessage());
            assertEquals("A valid list of participants is expected", exception.getMessage());
        }
    }

    @Nested
    class TestValidateEventDate {

        @Test
        void testValidDates() {
            boolean result = EventService.validateEventDate(validStartDate, validEndDate);
            System.out.println("Validação de datas válidas: " + result);
            assertTrue(result, "Valid dates should pass the validation.");
        }

        @Test
        void testStartDateAfterEndDate() {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> 
                EventService.validateEventDate(validEndDate, validStartDate)
            );
            System.out.println("Exceção para data de início após a data de término: " + exception.getMessage());
            assertEquals("A start date and end date is expected", exception.getMessage());
        }

        // Outros métodos com logs semelhantes...
    }

    @Nested
    class TestGetParticipantsReport {

        @Test
        void testValidParticipants() {
            List<String> result = EventService.getParticipantsReport(validParticipantsMap);
            System.out.println("Relatório de participantes confirmados: " + result);
            assertTrue(result.contains("Alice"), "Should return 'Alice' as a confirmed participant.");
            assertTrue(result.contains("Charlie"), "Should return 'Charlie' as a confirmed participant.");
        }

        // Outros métodos com logs semelhantes...
    }

    @Nested
    class TestEventIsFull {

        @Test
        void testEventNotFull() {
            boolean result = EventService.eventIsFull(validParticipantsMap, 4);
            System.out.println("Validação de evento não cheio: " + result);
            assertFalse(result, "The event should not be full.");
        }

        // Outros métodos com logs semelhantes...
    }
}
