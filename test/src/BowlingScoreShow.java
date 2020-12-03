
public class BowlingScoreShow {
	private BowlingDTO bowlingDto;

	public BowlingScoreShow(BowlingDTO bowlingDto) {
		this.bowlingDto = bowlingDto;
	}

	public void setNowScore() {
		bowlingDto.setPrintTemp(
				"출력 = " + bowlingDto.getScore() + "[" + bowlingDto.getFrame() + ", " + bowlingDto.getBall() + ", ");

		if (bowlingDto.getFrame() < BowlingDTO.LAST_FRAME) {
			if (bowlingDto.getPin() == BowlingDTO.EMPTY_PIN) {
				if (bowlingDto.getBall() == BowlingDTO.FIRST_BALL) {
					if (bowlingDto.getNowStatus("Turkey")) {
						bowlingDto.setTotalTemp(bowlingDto.getTotal() + "+10+10+");
						bowlingDto.setNowStatus("Turkey");
					} else if (bowlingDto.getNowStatus("Double")) {
						bowlingDto.setTotalTemp(bowlingDto.getTotalTemp() + "10+");
						bowlingDto.setNowStatus("Turkey");
					} else if (bowlingDto.getNowStatus("Strike")) {
						bowlingDto.setTotalTemp(bowlingDto.getTotalTemp() + "10+");
						bowlingDto.setNowStatus("Double");
					} else {
						bowlingDto.setTotalTemp(bowlingDto.getTotal() + "+");
						bowlingDto.setNowStatus("Strike");
					}
					bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotalTemp());
				} else if (bowlingDto.getBall() == BowlingDTO.SECOND_BALL) {
					bowlingDto.setNowStatus("Spare");
					bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotal() + "+");
				}
			} else {
				if (bowlingDto.isOneOfStrikeTurkeyDouble() && bowlingDto.getBall() == BowlingDTO.FIRST_BALL) {
					bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotal() + "+");
				} else
					bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotal());
			}
		} else if (bowlingDto.getFrame() == BowlingDTO.LAST_FRAME) {
			if (!bowlingDto.isLastBall()) {
				if (bowlingDto.isOneOfStrikeTurkeyDouble()) {
					if (bowlingDto.getBall() == BowlingDTO.FIRST_BALL) {
						if (bowlingDto.getNowStatus("Turkey"))
							bowlingDto.setTotalTemp(bowlingDto.getTotal() + "+10+" + bowlingDto.getScore() + "+");
						else
							bowlingDto.setTotalTemp(bowlingDto.getTotal() + bowlingDto.getScore() + "+");
					}
				}
			}

			if (bowlingDto.isOneOfStrikeTurkeyDouble())
				bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotalTemp());
			else
				bowlingDto.setPrintTemp(bowlingDto.getPrintTemp() + bowlingDto.getTotal());

		}

		if (bowlingDto.isLastBall())
			bowlingDto.setPrintTemp("출력 = " + bowlingDto.getScore() + "[ 10, 3," + bowlingDto.getTotal());

	}

	public void show() {
		int resultLength = bowlingDto.getResultLength();
		System.out.println(
				"##################################################기록판###############################################");
		for (int i = 1; i <= resultLength / 2; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = 0; i < resultLength / 2; i++) {
			if (bowlingDto.getResult(i, 0) == 10)
				System.out.print("\t|\tX, ");
			else if ((bowlingDto.getResult(i, 0) + bowlingDto.getResult(i, 1)) == 10) {
				System.out.print("\t|\t" + bowlingDto.getResult(i, 0) + ", /");
			} else
				System.out.print("\t|\t" + bowlingDto.getResult(i, 0) + ", " + bowlingDto.getResult(i, 1) + "");
		}
		System.out.println("\t|");

		System.out.println(
				"------------------------------------------------------------------------------------------------------");
		for (int i = resultLength / 2 + 1; i <= resultLength; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = resultLength / 2; i < resultLength - 1; i++) {
			if (bowlingDto.getResult(i, 0) == 10)
				System.out.print("\t|\tX, ");
			else if ((bowlingDto.getResult(i, 0) + bowlingDto.getResult(i, 1)) == 10) {
				System.out.print("\t|\t" + bowlingDto.getResult(i, 0) + ", /");
			} else
				System.out.print("\t|\t" + bowlingDto.getResult(i, 0) + ", " + bowlingDto.getResult(i, 1) + "");
		}

		if (bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1) == 20)
			System.out.print("\t|\tX, X, ");
		else if (bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1) == 10)
			System.out.print("\t|\t" + bowlingDto.getResult(9, 0) + ", /, ");
		else if (bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1) > 10)
			System.out.print("\t|\tX, " + bowlingDto.getResult(9, 1) + ", ");
		else
			System.out
					.println("\t|\t" + bowlingDto.getResult(9, 0) + ", " + bowlingDto.getResult(9, 1) + ", 0" + "\t|");

		if (bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1) >= 10) {
			if (bowlingDto.getScore().equals("10"))
				System.out.println("X\t|");
			else if ((bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1)) != 10
					&& (bowlingDto.getnScore() + bowlingDto.getResult(9, 1)) == 10)
				System.out.println("/\t|");
			else
				System.out.println(bowlingDto.getScore() + "\t|");
		}

		System.out.println(
				"##################################################기록판###############################################");
		System.out.println("최종점수 :" + bowlingDto.getTotal());
	}

	public void nowScore() {
		System.out.println(bowlingDto.getPrintTemp() + "]");
	}
}
