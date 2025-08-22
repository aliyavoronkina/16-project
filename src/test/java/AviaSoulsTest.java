import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AviaSoulsTest {

    @Test
    public void testTicketCompareToWhenFirstCheaper() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 7000, 900, 1100);
        assertTrue(ticket1.compareTo(ticket2) < 0);
    }

    @Test
    public void testTicketCompareToWhenFirstMoreExpensive() {
        Ticket ticket1 = new Ticket("VKO", "LED", 7000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 900, 1100);
        assertTrue(ticket1.compareTo(ticket2) > 0);
    }

    @Test
    public void testTicketCompareToWhenEqualPrice() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 900, 1100);
        assertEquals(0, ticket1.compareTo(ticket2));
    }

    @Test
    public void testTicketTimeComparatorWhenFirstFaster() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 7000, 900, 1200);
        assertTrue(comparator.compare(ticket1, ticket2) < 0);
    }

    @Test
    public void testTicketTimeComparatorWhenFirstSlower() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1300);
        Ticket ticket2 = new Ticket("VKO", "LED", 7000, 900, 1100);
        assertTrue(comparator.compare(ticket1, ticket2) > 0);
    }

    @Test
    public void testTicketTimeComparatorWhenEqualTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 7000, 900, 1100);
        assertEquals(0, comparator.compare(ticket1, ticket2));
    }

    @Test
    public void testSearchSortsByPriceAscending() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 7000, 1000, 1200));
        manager.add(new Ticket("VKO", "LED", 5000, 900, 1100));
        manager.add(new Ticket("VKO", "LED", 6000, 800, 1000));
        Ticket[] result = manager.search("VKO", "LED");
        assertEquals(3, result.length);
        assertEquals(5000, result[0].getPrice());
        assertEquals(6000, result[1].getPrice());
        assertEquals(7000, result[2].getPrice());
    }

    @Test
    public void testSearchReturnsEmptyArrayWhenNoMatches() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 7000, 1000, 1200));
        manager.add(new Ticket("SVO", "LED", 5000, 900, 1100));
        Ticket[] result = manager.search("VKO", "SVO");
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchReturnsSingleTicket() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        manager.add(ticket);
        Ticket[] result = manager.search("VKO", "LED");
        assertEquals(1, result.length);
        assertEquals(ticket, result[0]);
    }

    @Test
    public void testSearchAndSortByTime() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();
        manager.add(new Ticket("VKO", "LED", 7000, 1000, 1300)); // 300 мин
        manager.add(new Ticket("VKO", "LED", 5000, 900, 1100));  // 200 мин
        manager.add(new Ticket("VKO", "LED", 6000, 800, 1100));  // 300 мин
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", comparator);
        assertEquals(3, result.length);
        assertEquals(200, result[0].getFlightTime());
        assertEquals(300, result[1].getFlightTime());
        assertEquals(300, result[2].getFlightTime());
        assertEquals(5000, result[0].getPrice());
    }

    @Test
    public void testSearchAndSortByReturnsEmptyWhenNoMatches() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();
        manager.add(new Ticket("VKO", "LED", 7000, 1000, 1200));
        Ticket[] result = manager.searchAndSortBy("SVO", "LED", comparator);
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchAndSortByWithSingleResult() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        manager.add(ticket);
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", comparator);
        assertEquals(1, result.length);
        assertEquals(ticket, result[0]);
    }

    @Test
    public void testFindAllReturnsAllTickets() {
        AviaSouls manager = new AviaSouls();
        Ticket ticket1 = new Ticket("VKO", "LED", 7000, 1000, 1200);
        Ticket ticket2 = new Ticket("SVO", "LED", 5000, 900, 1100);
        manager.add(ticket1);
        manager.add(ticket2);
        Ticket[] result = manager.findAll();
        assertEquals(2, result.length);
    }

    @Test
    public void testAddIncreasesArraySize() {
        AviaSouls manager = new AviaSouls();
        assertEquals(0, manager.findAll().length);
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));
        assertEquals(1, manager.findAll().length);
        manager.add(new Ticket("SVO", "LED", 6000, 900, 1100));
        assertEquals(2, manager.findAll().length);
    }

    @Test
    public void testTicketEqualsWithDifferentClass() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket.equals("not a ticket"));
    }

    @Test
    public void testTicketEqualsWithDifferentFrom() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("SVO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithDifferentTo() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "AER", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithDifferentPrice() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 6000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithDifferentTimeFrom() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1100, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithDifferentTimeTo() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1300);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithSameObject() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertTrue(ticket.equals(ticket));
    }

    @Test
    public void testTicketEqualsWithIdenticalTickets() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketHashCodeConsistency() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        int hashCode1 = ticket.hashCode();
        int hashCode2 = ticket.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testTicketHashCodeForEqualObjects() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }

    @Test
    public void testSearchWithMultipleDifferentRoutes() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));
        manager.add(new Ticket("SVO", "LED", 6000, 900, 1100));
        manager.add(new Ticket("VKO", "AER", 7000, 800, 1000));

        Ticket[] result1 = manager.search("VKO", "LED");
        Ticket[] result2 = manager.search("SVO", "LED");
        Ticket[] result3 = manager.search("VKO", "AER");
        Ticket[] result4 = manager.search("LED", "VKO");

        assertEquals(1, result1.length);
        assertEquals(1, result2.length);
        assertEquals(1, result3.length);
        assertEquals(0, result4.length);
    }

    @Test
    public void testSearchAndSortByWithCustomComparator() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 7000, 1000, 1200));
        manager.add(new Ticket("VKO", "LED", 5000, 900, 1100));
        manager.add(new Ticket("VKO", "LED", 6000, 800, 1000));

        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", comparator);

        assertEquals(3, result.length);
    }

    @Test
    public void testEmptyManagerFindAll() {
        AviaSouls manager = new AviaSouls();
        Ticket[] result = manager.findAll();
        assertEquals(0, result.length);
    }

    @Test
    public void testEmptyManagerSearch() {
        AviaSouls manager = new AviaSouls();
        Ticket[] result = manager.search("VKO", "LED");
        assertEquals(0, result.length);
    }

    @Test
    public void testEmptyManagerSearchAndSortBy() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", comparator);
        assertEquals(0, result.length);
    }

    @Test
    public void testTicketFlightTimeCalculation() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertEquals(200, ticket.getFlightTime());
    }

    @Test
    public void testTicketFlightTimeNegative() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1200, 1000);
        assertEquals(-200, ticket.getFlightTime());
    }

    @Test
    public void testAddToArrayFunctionality() {
        AviaSouls manager = new AviaSouls();

        // Косвенно тестируем addToArray через add
        assertEquals(0, manager.findAll().length);

        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        manager.add(ticket1);
        assertEquals(1, manager.findAll().length);
        assertEquals(ticket1, manager.findAll()[0]);

        Ticket ticket2 = new Ticket("SVO", "LED", 6000, 900, 1100);
        manager.add(ticket2);
        assertEquals(2, manager.findAll().length);
        assertEquals(ticket2, manager.findAll()[1]);
    }

    @Test
    public void testSearchCaseSensitivity() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result1 = manager.search("VKO", "LED");
        Ticket[] result2 = manager.search("vko", "led");
        Ticket[] result3 = manager.search("VKO", "led");

        assertEquals(1, result1.length);
        assertEquals(0, result2.length);
        assertEquals(0, result3.length);
    }

    @Test
    public void testTicketTimeComparatorWithReasonableValues() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 2000); // 1000 минут
        Ticket ticket2 = new Ticket("VKO", "LED", 6000, 1000, 1500); // 500 минут

        assertTrue(comparator.compare(ticket1, ticket2) > 0); // 1000 > 500
    }

    @Test
    public void testTicketCompareToWithExtremeValues() {
        Ticket ticket1 = new Ticket("VKO", "LED", 1, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 1000000, 900, 1100);
        assertTrue(ticket1.compareTo(ticket2) < 0);
        assertTrue(ticket2.compareTo(ticket1) > 0);
    }

    @Test
    public void testSearchWithNullValues() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        // Проверка, что метод не падает при null значениях
        assertDoesNotThrow(() -> {
            Ticket[] result = manager.search(null, "LED");
            assertEquals(0, result.length);
        });

        assertDoesNotThrow(() -> {
            Ticket[] result = manager.search("VKO", null);
            assertEquals(0, result.length);
        });
    }

    @Test
    public void testTicketFlightTimeZero() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1000);
        assertEquals(0, ticket.getFlightTime());
    }

    @Test
    public void testAddMultipleTicketsAndVerifyOrder() {
        AviaSouls manager = new AviaSouls();

        // Добавляем несколько билетов и проверяем, что findAll возвращает их в порядке добавления
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("SVO", "LED", 6000, 900, 1100);
        Ticket ticket3 = new Ticket("VKO", "AER", 7000, 800, 1000);

        manager.add(ticket1);
        manager.add(ticket2);
        manager.add(ticket3);

        Ticket[] result = manager.findAll();
        assertEquals(3, result.length);
        assertEquals(ticket1, result[0]);
        assertEquals(ticket2, result[1]);
        assertEquals(ticket3, result[2]);
    }

    @Test
    public void testSearchWithEmptyStrings() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));
        manager.add(new Ticket("", "LED", 6000, 900, 1100));
        manager.add(new Ticket("VKO", "", 7000, 800, 1000));

        Ticket[] result1 = manager.search("", "LED");
        Ticket[] result2 = manager.search("VKO", "");
        Ticket[] result3 = manager.search("", "");

        assertEquals(1, result1.length);
        assertEquals(1, result2.length);
        assertEquals(0, result3.length); // Нет билетов с обеими пустыми строками
    }

    @Test
    public void testComparatorTransitivity() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1100); // 100 мин
        Ticket ticket2 = new Ticket("VKO", "LED", 6000, 1000, 1120); // 120 мин
        Ticket ticket3 = new Ticket("VKO", "LED", 7000, 1000, 1150); // 150 мин

        // Проверяем транзитивность: если A < B и B < C, то A < C
        assertTrue(comparator.compare(ticket1, ticket2) < 0);
        assertTrue(comparator.compare(ticket2, ticket3) < 0);
        assertTrue(comparator.compare(ticket1, ticket3) < 0);
    }

    @Test
    public void testTicketEqualsWithNullFrom() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket(null, "LED", 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithNullTo() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", null, 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithMixedNullFrom() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithMixedNullTo() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketHashCodeWithNullFields() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", null, 5000, 1000, 1200);

        // Просто проверяем, что не выбрасывается исключение
        assertDoesNotThrow(() -> ticket1.hashCode());
        assertDoesNotThrow(() -> ticket2.hashCode());
    }

    @Test
    public void testTicketEqualsBothNullFrom() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket(null, "LED", 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsBothNullTo() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", null, 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithOneNullFrom() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
        assertFalse(ticket2.equals(ticket1));
    }

    @Test
    public void testTicketEqualsWithOneNullTo() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
        assertFalse(ticket2.equals(ticket1));
    }

    @Test
    public void testSearchWithNullTo() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.search("VKO", null);
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchAndSortByWithNullFrom() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.searchAndSortBy(null, "LED", new TicketTimeComparator());
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchAndSortByWithNullTo() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.searchAndSortBy("VKO", null, new TicketTimeComparator());
        assertEquals(0, result.length);
    }

    @Test
    public void testTicketEqualsReflexivity() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertTrue(ticket.equals(ticket));
    }

    @Test
    public void testTicketEqualsSymmetry() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);

        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket2.equals(ticket1));
    }

    @Test
    public void testTicketEqualsTransitivity() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket3 = new Ticket("VKO", "LED", 5000, 1000, 1200);

        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket2.equals(ticket3));
        assertTrue(ticket1.equals(ticket3));
    }

    @Test
    public void testTicketEqualsConsistency() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);

        // Multiple calls should return the same result
        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket1.equals(ticket2));
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithDifferentObject() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        String notATicket = "not a ticket";
        assertFalse(ticket.equals(notATicket));
    }

    @Test
    public void testTicketEqualsWithNull() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket.equals(null));
    }

    @Test
    public void testTicketHashCodeConsistencyWithNullFields() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", null, 5000, 1000, 1200);

        int hashCode1 = ticket1.hashCode();
        int hashCode2 = ticket2.hashCode();

        assertEquals(hashCode1, ticket1.hashCode()); // Should be consistent
        assertEquals(hashCode2, ticket2.hashCode()); // Should be consistent
    }

    @Test
    public void testSearchAndSortByWithAllNullParameters() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.searchAndSortBy(null, null, null);
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithAllNullParameters() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.search(null, null);
        assertEquals(0, result.length);
    }

    @Test
    public void testTicketEqualsWhenFirstFromIsNullAndSecondIsNotNull() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWhenSecondFromIsNullAndFirstIsNotNull() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket(null, "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWhenFirstToIsNullAndSecondIsNotNull() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWhenSecondToIsNullAndFirstIsNotNull() {
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", null, 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithBothFromNullButDifferentOtherFields() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket(null, "AER", 5000, 1000, 1200); // Different 'to'
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithBothToNullButDifferentOtherFields() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("SVO", null, 5000, 1000, 1200); // Different 'from'
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testSearchAndSortByWithNullComparatorButValidRoute() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        // Этот тест проверяет именно ветку с null comparator
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", null);
        assertEquals(0, result.length); // Должен вернуть пустой массив из-за null comparator
    }

    @Test
    public void testSearchWithEmptyFromAndValidTo() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("", "LED", 5000, 1000, 1200));
        manager.add(new Ticket("VKO", "LED", 6000, 1100, 1300));

        Ticket[] result = manager.search("", "LED");
        assertEquals(1, result.length);
        assertEquals(5000, result[0].getPrice());
    }

    @Test
    public void testSearchWithEmptyToAndValidFrom() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "", 5000, 1000, 1200));
        manager.add(new Ticket("VKO", "LED", 6000, 1100, 1300));

        Ticket[] result = manager.search("VKO", "");
        assertEquals(1, result.length);
        assertEquals(5000, result[0].getPrice());
    }

    @Test
    public void testTicketFlightTimeWithReasonableExtremeValues() {
        // Тест на разумные крайние значения времени (без переполнения)
        Ticket ticket = new Ticket("VKO", "LED", 5000, 0, 1440); // Максимум 1 день
        assertEquals(1440, ticket.getFlightTime());
    }

    @Test
    public void testTicketCompareToWithExtremePriceValues() {
        Ticket ticket1 = new Ticket("VKO", "LED", Integer.MIN_VALUE, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", Integer.MAX_VALUE, 900, 1100);

        assertTrue(ticket1.compareTo(ticket2) < 0);
        assertTrue(ticket2.compareTo(ticket1) > 0);
    }

    @Test
    public void testTicketTimeComparatorWithNormalValues() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        // Тест с нормальными значениями (без риска переполнения)
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 2000);
        Ticket ticket2 = new Ticket("VKO", "LED", 6000, 1000, 1500);

        assertTrue(comparator.compare(ticket1, ticket2) > 0);
    }

    @Test
    public void testAddToArrayWithEmptyArray() {
        AviaSouls manager = new AviaSouls();

        // Косвенно тестируем addToArray с пустым массивом
        assertEquals(0, manager.findAll().length);

        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        manager.add(ticket);

        assertEquals(1, manager.findAll().length);
        assertEquals(ticket, manager.findAll()[0]);
    }

    @Test
    public void testObjectsHashWithNullValues() {
        // Тест на работу Objects.hash() с null значениями
        Ticket ticket = new Ticket(null, null, 5000, 1000, 1200);
        assertDoesNotThrow(() -> ticket.hashCode());

        // Проверяем консистентность
        int hashCode1 = ticket.hashCode();
        int hashCode2 = ticket.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    //недостающие тесты

    @Test
    public void testTicketEqualsWhenFirstFromIsNullAndSecondFromIsAlsoNullButDifferentTo() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket(null, "AER", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWhenFirstToIsNullAndSecondToIsAlsoNullButDifferentFrom() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("SVO", null, 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithNullFromAndNonNullFrom() {
        Ticket ticket1 = new Ticket(null, "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
        assertFalse(ticket2.equals(ticket1)); // Обратное сравнение
    }

    @Test
    public void testTicketEqualsWithNullToAndNonNullTo() {
        Ticket ticket1 = new Ticket("VKO", null, 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket2));
        assertFalse(ticket2.equals(ticket1)); // Обратное сравнение
    }

    @Test
    public void testSearchAndSortByWithNullComparatorButMatchingTickets() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        // Этот тест проверяет ветку где comparator == null
        Ticket[] result = manager.searchAndSortBy("VKO", "LED", null);
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithNullFromButValidTo() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.search(null, "LED");
        assertEquals(0, result.length);
    }

    @Test
    public void testSearchWithNullToButValidFrom() {
        AviaSouls manager = new AviaSouls();
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));

        Ticket[] result = manager.search("VKO", null);
        assertEquals(0, result.length);
    }

    @Test
    public void testTicketEqualsWithDifferentClassObject() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        String notTicket = "not a ticket";
        assertFalse(ticket.equals(notTicket));
    }

    @Test
    public void testTicketEqualsWithNullObject() {
        Ticket ticket = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertFalse(ticket.equals(null));
    }

    //еще

    @Test
    public void testTicketEqualsAllBranches() {
        // Все поля одинаковые
        Ticket ticket1 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 1000, 1200);
        assertTrue(ticket1.equals(ticket2));

        // Разные from
        Ticket ticket3 = new Ticket("SVO", "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket3));

        // Разные to
        Ticket ticket4 = new Ticket("VKO", "AER", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket4));

        // Разные price
        Ticket ticket5 = new Ticket("VKO", "LED", 6000, 1000, 1200);
        assertFalse(ticket1.equals(ticket5));

        // Разные timeFrom
        Ticket ticket6 = new Ticket("VKO", "LED", 5000, 1100, 1200);
        assertFalse(ticket1.equals(ticket6));

        // Разные timeTo
        Ticket ticket7 = new Ticket("VKO", "LED", 5000, 1000, 1300);
        assertFalse(ticket1.equals(ticket7));

        // Первый from = null, второй не null
        Ticket ticket8 = new Ticket(null, "LED", 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket8));
        assertFalse(ticket8.equals(ticket1));

        // Оба from = null, но разные to
        Ticket ticket9 = new Ticket(null, "AER", 5000, 1000, 1200);
        assertFalse(ticket8.equals(ticket9));

        // Первый to = null, второй не null
        Ticket ticket10 = new Ticket("VKO", null, 5000, 1000, 1200);
        assertFalse(ticket1.equals(ticket10));
        assertFalse(ticket10.equals(ticket1));

        // Оба to = null, но разные from
        Ticket ticket11 = new Ticket("SVO", null, 5000, 1000, 1200);
        assertFalse(ticket10.equals(ticket11));

        // Оба from = null и оба to = null, но разные price
        Ticket ticket12 = new Ticket(null, null, 5000, 1000, 1200);
        Ticket ticket13 = new Ticket(null, null, 6000, 1000, 1200);
        assertFalse(ticket12.equals(ticket13));

        // Сравнение с null
        assertFalse(ticket1.equals(null));

        // Сравнение с объектом другого класса
        assertFalse(ticket1.equals("not a ticket"));

        // Сравнение с самим собой
        assertTrue(ticket1.equals(ticket1));
    }

    @Test
    public void testSearchAllBranches() {
        AviaSouls manager = new AviaSouls();

        // Поиск с null параметрами
        Ticket[] result1 = manager.search(null, "LED");
        assertEquals(0, result1.length);

        Ticket[] result2 = manager.search("VKO", null);
        assertEquals(0, result2.length);

        Ticket[] result3 = manager.search(null, null);
        assertEquals(0, result3.length);

        // Поиск с пустыми строками
        manager.add(new Ticket("", "LED", 5000, 1000, 1200));
        manager.add(new Ticket("VKO", "", 6000, 1100, 1300));

        Ticket[] result4 = manager.search("", "LED");
        assertEquals(1, result4.length);

        Ticket[] result5 = manager.search("VKO", "");
        assertEquals(1, result5.length);

        Ticket[] result6 = manager.search("", "");
        assertEquals(0, result6.length);
    }

    @Test
    public void testSearchAndSortByAllBranches() {
        AviaSouls manager = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        // Все параметры null
        Ticket[] result1 = manager.searchAndSortBy(null, null, null);
        assertEquals(0, result1.length);

        // Только comparator null
        manager.add(new Ticket("VKO", "LED", 5000, 1000, 1200));
        Ticket[] result2 = manager.searchAndSortBy("VKO", "LED", null);
        assertEquals(0, result2.length);

        // Только from null
        Ticket[] result3 = manager.searchAndSortBy(null, "LED", comparator);
        assertEquals(0, result3.length);

        // Только to null
        Ticket[] result4 = manager.searchAndSortBy("VKO", null, comparator);
        assertEquals(0, result4.length);
    }
}