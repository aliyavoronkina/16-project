import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Comparator;
import java.util.Arrays;

public class AviaSoulsTest {

    // Тесты для класса Ticket
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

    // Тесты для equals
    @Test
    public void testTicketEqualsWithSameObject() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertTrue(ticket.equals(ticket));
    }

    @Test
    public void testTicketEqualsAllFieldsSame() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertTrue(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsDifferentFrom() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "KZN", 5000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsDifferentTo() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "LED", 5000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsDifferentPrice() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 6000, 10, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsDifferentTimeFrom() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 11, 12);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsDifferentTimeTo() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 13);
        assertFalse(ticket1.equals(ticket2));
    }

    @Test
    public void testTicketEqualsWithNull() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertFalse(ticket.equals(null));
    }

    @Test
    public void testTicketEqualsWithDifferentClass() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertFalse(ticket.equals("not a ticket"));
    }

    // Тесты для hashCode
    @Test
    public void testTicketHashCodeConsistency() {
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        int hashCode1 = ticket.hashCode();
        int hashCode2 = ticket.hashCode();
        assertEquals(hashCode1, hashCode2);
    }

    @Test
    public void testTicketHashCodeForEqualObjects() {
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 5000, 10, 12);
        assertEquals(ticket1.hashCode(), ticket2.hashCode());
    }

    // Тесты для TicketTimeComparator
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
    }

    @Test
    public void testTicketTimeComparatorWithSameFlightTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12); // 2 часа
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 8, 10);  // 2 часа

        assertEquals(0, comparator.compare(ticket1, ticket2));
    }

    @Test
    public void testTicketTimeComparatorNegativeFlightTime() {
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 15, 12); // -3 часа
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 10, 12); // 2 часа

        assertTrue(comparator.compare(ticket1, ticket2) < 0);
        assertTrue(comparator.compare(ticket2, ticket1) > 0);
    }

    // Тесты для AviaSouls - основные методы
    @Test
    public void testAddAndFindAll() {
        AviaSouls souls = new AviaSouls();

        // Проверяем пустую коллекцию
        assertArrayEquals(new Ticket[0], souls.findAll());

        // Добавляем билеты
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14);

        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] result = souls.findAll();
        assertArrayEquals(new Ticket[]{ticket1, ticket2}, result);
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

        // Добавляем 5 билетов
        Ticket[] expected = new Ticket[5];
        for (int i = 0; i < 5; i++) {
            Ticket ticket = new Ticket("VKO" + i, "KZN" + i, 1000 + i, i, i + 2);
            souls.add(ticket);
            expected[i] = ticket;
        }

        assertArrayEquals(expected, souls.findAll());
    }

    // Тесты для метода search
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
    public void testSearchNotFound() {
        AviaSouls souls = new AviaSouls();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14);

        souls.add(ticket1);
        souls.add(ticket2);

        Ticket[] result = souls.search("VKO", "LED");
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchExactMatch() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "KZN");
        assertArrayEquals(new Ticket[]{ticket}, result);
    }

    @Test
    public void testSearchDifferentFrom() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("SVO", "KZN");
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchDifferentTo() {
        AviaSouls souls = new AviaSouls();
        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.search("VKO", "LED");
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchNoTickets() {
        AviaSouls souls = new AviaSouls();
        Ticket[] result = souls.search("VKO", "KZN");
        assertArrayEquals(new Ticket[0], result);
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
    }

    @Test
    public void testSearchWithMultipleSameRouteTickets() {
        AviaSouls souls = new AviaSouls();

        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "KZN", 3000, 11, 14);
        Ticket ticket3 = new Ticket("VKO", "KZN", 7000, 9, 11);
        Ticket ticket4 = new Ticket("VKO", "KZN", 4000, 8, 10);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);
        souls.add(ticket4);

        Ticket[] result = souls.search("VKO", "KZN");
        Ticket[] expected = {ticket2, ticket4, ticket1, ticket3};

        assertArrayEquals(expected, result);
    }

    // Тесты для метода searchAndSortBy
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
    public void testSearchAndSortByWithCustomComparator() {
        AviaSouls souls = new AviaSouls();

        // Компаратор для сортировки по убыванию цены
        Comparator<Ticket> priceDescComparator = (t1, t2) ->
                Integer.compare(t2.getPrice(), t1.getPrice());

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
        assertArrayEquals(new Ticket[]{ticket}, result);
    }

    @Test
    public void testSearchAndSortByWithNoMatches() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator timeComparator = new TicketTimeComparator();

        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.searchAndSortBy("SVO", "LED", timeComparator);
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchAndSortByWithEmptyResult() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        Ticket ticket = new Ticket("VKO", "KZN", 5000, 10, 12);
        souls.add(ticket);

        Ticket[] result = souls.searchAndSortBy("SVO", "LED", comparator);
        assertArrayEquals(new Ticket[0], result);
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
    }

    @Test
    public void testSearchAndSortByWithEmptyTicketsArray() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        // Пустой массив билетов
        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", comparator);
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchAndSortByWithNoMatchingTickets() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        // Добавляем билеты, которые не соответствуют критериям поиска
        Ticket ticket1 = new Ticket("SVO", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "LED", 3000, 11, 14);
        Ticket ticket3 = new Ticket("SVO", "KZN", 7000, 9, 11);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        // Ищем маршрут, которого нет
        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", comparator);
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchAndSortByWithMixedMatchingAndNonMatching() {
        AviaSouls souls = new AviaSouls();
        TicketTimeComparator comparator = new TicketTimeComparator();

        // Добавляем смесь подходящих и неподходящих билетов
        Ticket ticket1 = new Ticket("VKO", "KZN", 5000, 10, 13); // 3 часа - подходит
        Ticket ticket2 = new Ticket("SVO", "LED", 3000, 11, 14); // не подходит
        Ticket ticket3 = new Ticket("VKO", "KZN", 7000, 9, 10);  // 1 час - подходит
        Ticket ticket4 = new Ticket("VKO", "LED", 4000, 8, 10);  // не подходит

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);
        souls.add(ticket4);

        Ticket[] result = souls.searchAndSortBy("VKO", "KZN", comparator);
        Ticket[] expected = {ticket3, ticket1};

        assertArrayEquals(expected, result);
    }

    @Test
    public void testSearchWithEmptyTicketsArray() {
        AviaSouls souls = new AviaSouls();

        // Пустой массив билетов
        Ticket[] result = souls.search("VKO", "KZN");
        assertArrayEquals(new Ticket[0], result);
    }

    @Test
    public void testSearchWithNoMatchingTickets() {
        AviaSouls souls = new AviaSouls();

        // Добавляем билеты, которые не соответствуют критериям поиска
        Ticket ticket1 = new Ticket("SVO", "LED", 5000, 10, 12);
        Ticket ticket2 = new Ticket("VKO", "LED", 3000, 11, 14);
        Ticket ticket3 = new Ticket("SVO", "KZN", 7000, 9, 11);

        souls.add(ticket1);
        souls.add(ticket2);
        souls.add(ticket3);

        // Ищем маршрут, которого нет
        Ticket[] result = souls.search("VKO", "KZN");
        assertArrayEquals(new Ticket[0], result);
    }

    // Вспомогательный метод для проверки наличия элемента в массиве
    private boolean arrayContains(Ticket[] array, Ticket ticket) {
        for (Ticket t : array) {
            if (t.equals(ticket)) {
                return true;
            }
        }
        return false;
    }
}