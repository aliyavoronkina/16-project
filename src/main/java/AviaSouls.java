import java.util.Arrays;
import java.util.Comparator;

public class AviaSouls {
    private Ticket[] tickets = new Ticket[0];

    private Ticket[] addToArray(Ticket[] current, Ticket ticket) {
        Ticket[] tmp = new Ticket[current.length + 1];
        for (int i = 0; i < current.length; i++) {
            tmp[i] = current[i];
        }
        tmp[tmp.length - 1] = ticket;
        return tmp;
    }

    public void add(Ticket ticket) {
        tickets = addToArray(tickets, ticket);
    }

    public Ticket[] findAll() {
        return tickets;
    }

    public Ticket[] search(String from, String to) {
        Ticket[] result = new Ticket[0];
        // Добавляем проверку на null
        if (from == null || to == null) {
            return result;
        }
        for (Ticket ticket : tickets) {
            if (from.equals(ticket.getFrom()) && to.equals(ticket.getTo())) {
                result = addToArray(result, ticket);
            }
        }
        Arrays.sort(result);
        return result;
    }

    public Ticket[] searchAndSortBy(String from, String to, Comparator<Ticket> comparator) {
        Ticket[] result = new Ticket[0];
        // Добавляем проверку на null
        if (from == null || to == null || comparator == null) {
            return result;
        }
        for (Ticket ticket : tickets) {
            if (from.equals(ticket.getFrom()) && to.equals(ticket.getTo())) {
                result = addToArray(result, ticket);
            }
        }
        Arrays.sort(result, comparator);
        return result;
    }

}