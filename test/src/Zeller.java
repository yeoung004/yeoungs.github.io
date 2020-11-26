import java.util.Scanner;

public class Zeller {
    /**
     * A demo implementation of 'Zeller's Congruence' for the Gregorian Calendar
     * See http://en.wikipedia.org/wiki/Zeller's_congruence
     *
     * @param args (Not used)
     */
    final static String[] DAYS_OF_WEEK = {
            "Saturday", "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday","Friday"};

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.print("Enter the date in dd/mm/yyyy form: ");

        String[] atoms = input.nextLine().split("/");
        int day = Integer.parseInt(atoms[0]);
        int month = Integer.parseInt(atoms[1]);
        int year = Integer.parseInt(atoms[2]);

        if (month < 3) {
            month += 12;
            year -= 1;
        }

        int k = year % 100;
        int j = year / 100;

        int date = ((day + (((month + 1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;

        System.out.println("That date was a " + DAYS_OF_WEEK[date] + ".");
    }
}