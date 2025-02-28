import java.util.Scanner;

public class DayCalculator {
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter month (name or number): ");
        String monthInput = sc.nextLine().trim();
        System.out.print("Enter year (in number): ");
        int year = sc.nextInt();

        String[][] monthNames = {
                {"January", "Jan.", "Jan", "1"},
                {"February", "Feb.", "Feb", "2"},
                {"March", "Mar.", "Mar", "3"},
                {"April", "Apr.", "Apr", "4"},
                {"May", "May", "May", "5"},
                {"June", "June", "Jun", "6"},
                {"July", "July", "Jul", "7"},
                {"August", "Aug.", "Aug", "8"},
                {"September", "Sept.", "Sep", "9"},
                {"October", "Oct.", "Oct", "10"},
                {"November", "Nov.", "Nov", "11"},
                {"December", "Dec.", "Dec", "12"}
        };

        int[] daysInMonth = {31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

        int days = -1;
        for (int i = 0; i < 12; i++) {
            for (String name : monthNames[i]) {
                if (monthInput.equalsIgnoreCase(name)) {
                    days = daysInMonth[i];
                    if (i == 1 && isLeapYear(year)) {
                        days = 29;
                    }
                    break;
                }
            }
            if (days != -1)
                break;
        }

        if (days != -1) {
            System.out.println("Number of days: " + days);
        } else {
            System.out.println("Invalid month input!");
        }
    }
}
