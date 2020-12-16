package Bowling;

public class BowlingScoreRecord {

	public void setNowScore(UserDTO userDto) {
		userDto.setPrintTemp("출력 = " + userDto.getScore() + "[" + userDto.getFrame() + ", " + userDto.getBall() + ", ");

		if (userDto.getFrame() < UserDTO.LAST_FRAME) {
			if (userDto.getPin() == UserDTO.EMPTY_PIN) {
				if (userDto.getBall() == UserDTO.FIRST_BALL) {
					if (userDto.getNowStatus("Turkey")) {
						userDto.setTotalTemp(userDto.getTotal() + "+10+10+");
						userDto.setNowStatus("Turkey");
					} else if (userDto.getNowStatus("Double")) {
						userDto.setTotalTemp(userDto.getTotalTemp() + "10+");
						userDto.setNowStatus("Turkey");
					} else if (userDto.getNowStatus("Strike")) {
						userDto.setTotalTemp(userDto.getTotalTemp() + "10+");
						userDto.setNowStatus("Double");
					} else {
						userDto.setTotalTemp(userDto.getTotal() + "+");
						userDto.setNowStatus("Strike");
					}
					userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotalTemp());
				} else if (userDto.getBall() == UserDTO.SECOND_BALL) {
					userDto.setNowStatus("Spare");
					userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotal() + "+");
				}
			} else {
				if (userDto.isOneOfStrikeTurkeyDouble() && userDto.getBall() == UserDTO.FIRST_BALL) {
					userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotalTemp() + userDto.getScore() + "+");
				} else
					userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotal());
			}
		} else if (userDto.getFrame() == UserDTO.LAST_FRAME) {
			if (!userDto.isLastBall()) {
				if (userDto.isOneOfStrikeTurkeyDouble()) {
					if (userDto.getBall() == UserDTO.FIRST_BALL) {
						if (userDto.getNowStatus("Turkey"))
							userDto.setTotalTemp(userDto.getTotal() + "+10+" + userDto.getScore() + "+");
						else
							userDto.setTotalTemp(userDto.getTotal() + userDto.getScore() + "+");
					}
				}
			}

			if (userDto.isOneOfStrikeTurkeyDouble())
				userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotalTemp() + userDto.getScore() + "+");
			else
				userDto.setPrintTemp(userDto.getPrintTemp() + userDto.getTotal());

		}

		if (userDto.isLastBall())
			userDto.setPrintTemp("출력 = " + userDto.getScore() + "[10, 3, " + userDto.getTotal());

	}

	public void show(int[][] result, int score, int total) {
		System.out.println(
				"##################################################기록판###############################################");
		for (int i = 1; i <= result.length / 2; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = 0; i < result.length / 2; i++) {
			if (result[i][0] == UserDTO.STRIKE_SPARE)
				System.out.print("\t|\tX, ");
			else if ((result[i][0] + result[i][1]) == UserDTO.STRIKE_SPARE) {
				System.out.print("\t|\t" + result[i][0] + ", /");
			} else
				System.out.print("\t|\t" + result[i][0] + ", " + result[i][1] + "");
		}
		System.out.println("\t|");

		System.out.println(
				"------------------------------------------------------------------------------------------------------");
		for (int i = result.length / 2 + 1; i <= result.length; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = result.length / 2; i < result.length - 1; i++) {
			if (result[i][0] == UserDTO.STRIKE_SPARE)
				System.out.print("\t|\tX, ");
			else if ((result[i][0] + result[i][1]) == UserDTO.STRIKE_SPARE) {
				System.out.print("\t|\t" + result[i][0] + ", /");
			} else
				System.out.print("\t|\t" + result[i][0] + ", " + result[i][1] + "");
		}

		if (result[9][0] + result[9][1] == 20)
			System.out.print("\t|\tX, X, ");
		else if (result[9][0] + result[9][1] == UserDTO.STRIKE_SPARE)
			System.out.print("\t|\t" + result[9][0] + ", /, ");
		else if (result[9][0] + result[9][1] > UserDTO.STRIKE_SPARE)
			System.out.print("\t|\tX, " + result[9][1] + ", ");
		else
			System.out.println("\t|\t" + result[9][0] + ", " + result[9][1] + ", 0" + "\t|");

		if (result[9][0] + result[9][1] >= UserDTO.STRIKE_SPARE) {
			if (score == 10)
				System.out.println("X\t|");
			else if ((result[9][0] + result[9][1]) != 10 && (score + result[9][1]) == UserDTO.STRIKE_SPARE)
				System.out.println("/\t|");
			else
				System.out.println(score + "\t|");
		}

		System.out.println(
				"##################################################기록판###############################################");
		System.out.println("최종점수 :" + total);
	}

	public void nowScore(String printTemp) {
		System.out.println(printTemp + "]");
	}
}
