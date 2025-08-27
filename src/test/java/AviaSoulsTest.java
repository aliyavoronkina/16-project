import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;

public class AviaSoulsTest {

    @Test
    public void testTicketConstructorAndGetters() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertEquals("VKO", ticket.getFrom());
        assertEquals("KZN", ticket.getTo());
        assertEquals(5000, ticket.getPrice());
        assertEquals(10, ticket.getTimeFrom());
        assertEquals(12, ticket.getTimeTo());
    }

    @Test
    public void testTicketToString() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        String expected = "Ticket{from='VKO', to='KZN', price=5000, timeFrom=10, timeTo=12}";

        assertEquals(expected, ticket.toString());
    }

    @Test
    public void testTicketCompareTo() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 7000, 11, 14);
        Ticket ticket3 = new Ticket("VKO", "KZN", 5000, 9, 11);

        assertTrue(ticket1.compareTo(ticket2) < 0);
        assertTrue(ticket2.compareTo(ticket1) > 0);
        assertEquals(0, ticket1.compareTo(ticket3));
    }

    @Test
    public void testTicketCompareToWithNull() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertThrows(NullPointerException.class, () -> {
            ticket.compareTo(null);
        });
    }

    @Test
    public void testTicketTimeComparator() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12); // 2 часа
        Ticket ticket2 = new Ticket("VKO", "KZN", 7000, 11, 15); // 4 часа
        Ticket ticket3 = new Ticket("VKO", "KZN", 6000, 9, 11);  // 2 часа

        assertTrue(comparator.compare(ticket1, ticket2) < 0);
        assertTrue(comparator.compare(ticket2, ticket1) > 0);
        assertEquals(0, comparator.compare(ticket1, ticket3));
    }

    @Test
    public void testTicketTimeComparatorWithNull() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertThrows(NullPointerException.class, () -> {
            comparator.compare(null, ticket);
        });

        assertThrows(NullPointerException.class, () -> {
            comparator.compare(ticket, null);
        });

        assertThrows(NullPointerException.class, () -> {
            comparator.compare(null, null);
        });
    }

    @Test
    public void testTicketTimeComparatorEdgeCases() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 5);  // Отрицательное время полета
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 5, 10);  // Положительное время полета

        assertTrue(comparator.compare(ticket1, ticket2) < 0);
        assertTrue(comparator.compare(ticket2, ticket1) > 0);
    }

    @Test
    public void testTicketTimeComparatorWithSameFlightTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12); // 2 часа
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 8, 10);  // 2 часа

        assertEquals(0, comparator.compare(ticket1, ticket2));
    }

    @Test
    public void testSearchAndSortByPrice() {
        AviaSouls souls = new AviaSouls();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 11, 14);
        Ticket ticket3 = new Ticket("VKO", "KZN", 7000, 9, 11);
        Ticket ticket4 = new Ticket("SVO", "KZN", 4000, 8, 10);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);
        souls.add(ticket4);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket2, ticket1, ticket3};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByTime() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator timeComparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 13); // 3 часа
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 11, 15); // 4 часа
        Ticket ticket3 = new Ticket("VKO", "KZN", 7000, 9, 10);  // 1 час
        Ticket ticket4 = new Ticket("SVO", "KZN", 4000, 8, 10);  // 2 часа

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);
        souls.add(ticket4);

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", timeComparator);
        Ticket[] expected = {ticket3, ticket1, ticket2};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByTimeWithMultipleTickets() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator timeComparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 15); // 5 часов
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 11, 12); // 1 час
        Ticket ticket3 = new Ticket("VKO", "KZN", 7000, 9, 11);  // 2 часа
        Ticket ticket4 = new Ticket("VKO", "LED", 4000, 8, 10);  // 2 часа, другой маршрут

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);
        souls.add(ticket4);

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", timeComparator);
        Ticket[] expected = {ticket2, ticket3, ticket1};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByWithCustomComparator() {
        AviaSouls souls = new AviaSouls();

        // Компаратор для сортировки по убыванию цены
        Comparator<Ticket> priceDescComparator = new Comparator<Ticket>() {
            @Override
            public int compare(Ticket t1, Ticket t2) {
                return Integer.compare(t2.getPrice(), t1.getPrice());
            }
        };

        Ticket ticket1 = new Ticket("VKO", "KZN", 3000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 11, 14);
        Ticket ticket3 = new Ticket("VKO", "KZN", 4000, 9, 11);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", priceDescComparator);
        Ticket[] expected = {ticket2, ticket3, ticket1};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByWithSingleTicket() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", comparator);
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByWithNoMatches() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator timeComparator = new TicketTimeComparator();

        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.searchAndSortBy("SVO", "LED", timeComparator);
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchAndSortByWithEmptyResult() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", comparator);
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchNotFound() {
        AviaSouls souls = new AviaSouls();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14);

        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] result = souls.search("VKO", "LED");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchExactMatch() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchDifferentFrom() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("SVO", "KZN");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchDifferentTo() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "LED");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchBothDifferent() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("SVO", "LED");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchCaseSensitive() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result1 = souls.search("vko", "KZN");
        Ticket[] result2 = souls.search("VKO", "kzn");
        Ticket[] result3 = souls.search("vko", "kzn");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result1);
        assertArrayEquals(expected, result2);
        assertArrayEquals(expected, result3);
    }

    @Test
    public void testSearchEmptyStrings() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "", 3000, 11, 14);
        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] result1 = souls.search("", "KZN");
        Ticket[] result2 = souls.search("VKO", "");
        Ticket[] result3 = souls.search("", "");
        Ticket[] expected1 = {ticket1};
        Ticket[] expected2 = {ticket2};
        Ticket[] expected3 = new Ticket[0];

        assertArrayEquals(expected1, result1);
        assertArrayEquals(expected2, result2);
        assertArrayEquals(expected3, result3);
    }

    @Test
    public void testSearchWithSameFromTo() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "VKO", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "VKO");
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchWithWhitespace() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket(" VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN ", 3000, 11, 14);
        Ticket ticket3 = new Ticket("SVO", "LED", 7000, 9, 11);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        Ticket[] result1 = souls.search(" VKO", "KZN");
        Ticket[] result2 = souls.search("VKO", "KZN ");
        Ticket[] result3 = souls.search("SVO", "LED");
        Ticket[] expected1 = {ticket1};
        Ticket[] expected2 = {ticket2};
        Ticket[] expected3 = {ticket3};

        assertArrayEquals(expected1, result1);
        assertArrayEquals(expected2, result2);
        assertArrayEquals(expected3, result3);
    }

    @Test
    public void testSearchWithSpecialCharacters() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("VKO-1", "KZN-2", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO_3", "LED_4", 3000, 11, 14);

        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] result1 = souls.search("VKO-1", "KZN-2");
        Ticket[] result2 = souls.search("SVO_3", "LED_4");
        Ticket[] expected1 = {ticket1};
        Ticket[] expected2 = {ticket2};

        assertArrayEquals(expected1, result1);
        assertArrayEquals(expected2, result2);
    }

    @Test
    public void testSearchLongStrings() {
        String longFrom = "VERY_LONG_AIRPORT_NAME_FROM_1234567890";
        String longTo = "VERY_LONG_AIRPORT_NAME_TO_1234567890";

        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket(longFrom, longTo, 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search(longFrom, longTo);
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchWithMaxIntValues() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchWithMinIntValues() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchMultipleMatches() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 11, 14);
        Ticket ticket3 = new Ticket("SVO", "LED", 7000, 9, 11);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket2, ticket1}; // Сортировка по цене

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchNoTickets() {
        AviaSouls souls = new AviaSouls();
        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testEmptyManager() {
        AviaSouls souls = new AviaSouls();

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = new Ticket[0];

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchNullParameters() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        assertThrows(NullPointerException.class, () -> {
            souls.search(null, "KZN");
        });

        assertThrows(NullPointerException.class, () -> {
            souls.search("VKO", null);
        });

        assertThrows(NullPointerException.class, () -> {
            souls.search(null, null);
        });
    }

    @Test
    public void testSearchAndSortByWithNullComparator() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        assertThrows(NullPointerException.class, () -> {
            souls.searchAndSortBy("VKO", "KZN", null);
        });
    }

    @Test
    public void testSearchAndSortByWithNullParameters() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        assertThrows(NullPointerException.class, () -> {
            souls.searchAndSortBy(null, "KZN", comparator);
        });

        assertThrows(NullPointerException.class, () -> {
            souls.searchAndSortBy("VKO", null, comparator);
        });

        assertThrows(NullPointerException.class, () -> {
            souls.searchAndSortBy(null, null, comparator);
        });

        assertThrows(NullPointerException.class, () -> {
            souls.searchAndSortBy(null, null, null);
        });
    }

    @Test
    public void testAddToArrayMethod() {
        AviaSouls souls = new AviaSouls();

        Ticket[] expectedEmpty = new Ticket[0];
        assertArrayEquals(expectedEmpty, souls.findAll());

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket1);

        Ticket[] expectedOne = {ticket1};
        assertArrayEquals(expectedOne, souls.findAll());

        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14);
        souls.add(ticket2);

        Ticket[] expectedTwo = {ticket1, ticket2};
        assertArrayEquals(expectedTwo, souls.findAll());
    }

    @Test
    public void testAddNullTicket() {
        AviaSouls souls = new AviaSouls();

        assertThrows(NullPointerException.class, () -> {
            souls.add(null);
        });
    }

    @Test
    public void testAddMultipleTickets() {
        AviaSouls souls = new AviaSouls();

        Ticket[] expectedEmpty = new Ticket[0];
        assertArrayEquals(expectedEmpty, souls.findAll());

        // Добавляем 5 билетов
        Ticket[] expectedTickets = new Ticket[5];
        for (int i = 0; i < 5; i++) {
            Ticket ticket = new Ticket("VKO" + i, "KZN" + i, 1000 + i, i, i + 2);
            souls.add(ticket);
            expectedTickets[i] = ticket;
        }

        assertArrayEquals(expectedTickets, souls.findAll());
    }

    @Test
    public void testFindAllMethod() {
        AviaSouls souls = new AviaSouls();

        Ticket[] expectedEmpty = new Ticket[0];
        assertArrayEquals(expectedEmpty, souls.findAll());

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14);

        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] allTickets = souls.findAll();
        Ticket[] expected = {ticket1, ticket2};

        assertArrayEquals(expected, allTickets);
    }

    // Остальные тесты для equals, hashCode и т.д. остаются без изменений
    // так как они не работают с массивами

    @Test
    public void testEqualsReflexivity() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertTrue(ticket.equals(ticket));
    }

    @Test
    public void testEqualsSymmetry() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket2.equals(ticket1));
    }

    @Test
    public void testEqualsTransitivity() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket3 = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket2.equals(ticket3));
        assertTrue(ticket1.equals(ticket3));
    }

    @Test
    public void testEqualsConsistency() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);

        for (int i = 0; i < 10; i++) {
            assertTrue(ticket1.equals(ticket2));
        }
    }

    @Test
    public void testEqualsNull() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertFalse(ticket.equals(null));
    }

    @Test
    public void testEqualsDifferentClass() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        String notATicket = "not a ticket";
        assertFalse(ticket.equals(notATicket));
    }

    @Test
    public void testEqualsWithItself() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertTrue(ticket.equals(ticket));
    }

    @Test
    public void testEqualsWithDifferentObjectType() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        String notATicket = "not a ticket";

        assertFalse(ticket.equals(notATicket));
    }

    @Test
    public void testEqualsAllFieldsSame() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentFrom() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "KZN", 5000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentTo() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentPrice() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 6000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentTimeFrom() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 11, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsDifferentTimeTo() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 13);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsCaseSensitivity() {
        Ticket ticket1 = new Ticket("vko", "kzn", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testEqualsMultipleDifferences() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 6000, 11, 13);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testHashCodeConsistency() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        int firstHash = ticket.hashCode();

        for (int i = 0; i < 10; i++) {
            assertEquals(firstHash, ticket.hashCode());
        }
    }

    @Test
    public void testHashCodeEqualsContract() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);

        assertTrue(ticket1.equals(ticket2));
        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }

    @Test
    public void testHashCodeDifferentObjects() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 6000, 11, 13);

        assertNotEquals(ticket1.hashCode(), ticket2.hashCode());
    }

    @Test
    public void testEdgeCases() {
        Ticket ticket1 = new Ticket("", "", 0, 0, 0);
        Ticket ticket2 = new Ticket("", "", 0, 0, 0);

        assertTrue(ticket1.equals(ticket2));
        assertEquals(ticket1.hashCode(), ticket2.hashCode());

        Ticket ticket3 = new Ticket("A", "B", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);
        Ticket ticket4 = new Ticket("A", "B", Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE);

        assertTrue(ticket3.equals(ticket4));
        assertEquals(ticket3.hashCode(), ticket4.hashCode());

        Ticket ticket5 = new Ticket("A", "B", Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);
        Ticket ticket6 = new Ticket("A", "B", Integer.MIN_VALUE, Integer.MIN_VALUE, Integer.MIN_VALUE);

        assertTrue(ticket5.equals(ticket6));
        assertEquals(ticket5.hashCode(), ticket6.hashCode());
    }
}