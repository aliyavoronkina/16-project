import java.util.Objects;

public class Ticket implements Comparable<Ticket> {
    private String from;
    private String to;
    private int price;
    private int timeFrom;
    private int timeTo;

    public Ticket(String from, String to, int price, int timeFrom, int timeTo) {
        this.from = from;
        this.to = to;
        this.price = price;
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public int getPrice() {
        return price;
    }

    public int getTimeFrom() {
        return timeFrom;
    }

    public int getTimeTo() {
        return timeTo;
    }

    public int getFlightTime() {
        return timeTo - timeFrom;
    }

    @Override
    public int compareTo(Ticket other) {
        return Integer.compare(this.price, other.price);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;

        // Безопасное сравнение строк с проверкой на null
        if (!Objects.equals(from, ticket.from)) return false;
        if (!Objects.equals(to, ticket.to)) return false;
        if (price != ticket.price) return false;
        if (timeFrom != ticket.timeFrom) return false;
        return timeTo == ticket.timeTo;
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, price, timeFrom, timeTo);
    }
}