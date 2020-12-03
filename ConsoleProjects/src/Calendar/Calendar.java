package Calendar;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calendar {

	public int startMonth(int year, int month) {
		int[] days_of_week = { 6, 0, 1, 2, 3, 4, 5 };
		int day = 1;

		if (month < 3) {
			month += 12;
			year -= 1;
		}

		int k = year % 100;
		int j = year / 100;

		int date = ((day + (((month + 1) * 26) / 10) + k + (k / 4) + (j / 4)) + (5 * j)) % 7;

		return days_of_week[date];
	}

	public void showCal(int year, int month, boolean endProgram) {
		int first = startMonth(year, month);
		Map<Integer,Integer> days = new HashMap<>(); 
		boolean leapYear = false;
		int enterCnt = 0;
		
		if((year % 4 == 0) && (year % 100 != 0)||(year % 4 ==0))
			leapYear = true;
		
		days.put(1, 31);		
		if(leapYear)
			days.put(2,29);
		else
			days.put(2,28);
		days.put(3, 31);
		days.put(4, 30);
		days.put(5, 31);
		days.put(6, 30);
		days.put(7, 31);
		days.put(8, 31);
		days.put(9, 30);
		days.put(10, 31);
		days.put(11, 30);
		days.put(12, 31);
		
		System.out.println("<달력표시>");
		System.out.println(year + "년 "+month + "월");
		System.out.println("----------------------------");
		System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
		System.out.println("----------------------------");
		
		for(int i = 1; i <= first; i++)
		{
			System.out.print("    ");
			enterCnt++;
		}
		for (int i = 1; i < 10; i++) {
			System.out.printf(" 0%d ",i);
			
			enterCnt++;
			if(enterCnt % 7 == 0)
				System.out.println();
		}
		for (int i = 10; i <= days.get(month); i++) {
			System.out.printf(" %d ",i);
			
			enterCnt++;
			if(enterCnt % 7 == 0)
				System.out.println();
		}
		System.out.println("\n----------------------------");
	}

	public boolean isNumber(String year, String month) {
		String merg = year + month;
		char temp = ' ';

		for (int i = 0; i < merg.length(); i++) {
			temp = merg.charAt(i);

			if (!('0' <= temp && temp <= '9')) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Calendar cal = new Calendar();

		String year = "";
		String month = "";
		int yearNum = 0;
		int monthNum = 0;

		boolean numberCheck = false;
		boolean endProgram = true;

		while (endProgram) {
			System.out.print("년도를 입력하세요...");
			year = input.next();

			System.out.print("월를 입력하세요...");
			month = input.next();

			numberCheck = cal.isNumber(year, month);

			if (!numberCheck) {
				System.out.println("잘못된 정보를 입력하셨습니다!");

			} else {
				yearNum = Integer.parseInt(year);
				monthNum = Integer.parseInt(month);

				if (!(1 <= monthNum && monthNum <= 12)) {
					System.out.println("1년은 1월에서 12월까지 밖에 없습니다!!");
				} else {
					cal.showCal(yearNum, monthNum, endProgram);
					endProgram = false;
				}

			}
		}

	}

}
