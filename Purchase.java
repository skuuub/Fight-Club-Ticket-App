public class Purchase {
    private final String username;
    private final String fight;
    private final int numTickets;
    private final double totalCost;

    public Purchase(String username, String fight, int numTickets, double totalCost) {
        this.username = username;
        this.fight = fight;
        this.numTickets = numTickets;
        this.totalCost = totalCost;
    }

    public String getUsername() {
        return username;
    }

    public String getFight() {
        return fight;
    }

    public int getNumTickets() {
        return numTickets;
    }

    public double getTotalCost() {
        return totalCost;
    }

    @Override
    public String toString() {
        return "Fight: " + fight + ", Tickets: " + numTickets + ", Cost: $" + totalCost;
    }
}
