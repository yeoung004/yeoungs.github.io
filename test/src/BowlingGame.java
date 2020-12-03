import java.util.Map;
import java.util.Scanner;

public class BowlingGame {
	final static int EMPTY_PIN = 0;
	final static int FIRST_BALL = 1;
	final static int SECOND_BALL = 2;
	final static int FINAL_BALL = 3;
	final static int FULL_PIN = 10;
	final static int FIRST_FRAME = 1;
	final static int LAST_FRAME = 10;

	public static void main(String[] args) {
		Scanner roll = new Scanner(System.in);
		BowlingDTO dto = new BowlingDTO();

		System.out.println("-------------------프로그램을 시작합니다-------------------");

		for (dto.setFrame(FIRST_FRAME); dto.getFrame() <= LAST_FRAME; dto.setFrame(dto.getFrame() + 1)) {
			if (dto.getScore().equals("y"))
				break;
			for (dto.setBall(FIRST_BALL); dto.getBall() <= SECOND_BALL; dto.setBall(dto.getBall() + 1)) {

				dto.setBallCnt(dto.getBallCnt() + 1);
				System.out.print("> " + dto.getBallCnt() + " 번째 입력 값 ");
				dto.setScore(roll.nextLine());

				if (dto.getScore().equals("")) {
					System.out.print("정말 종료하시겠습니까? y/n : ");
					dto.setScore(roll.nextLine());

					if (dto.getScore().equals("y")) {
						break;
					}
					System.out.println("다시 시작합니다...");
					dto.setBallCnt(dto.getBallCnt() - 1);
					dto.setBall(dto.getBall() - 1);
					continue;
				}

				else if (!isCorrect(dto.getScore(), dto.getPin())) {
					dto.setBallCnt(dto.getBallCnt() - 1);
					dto.setBall(dto.getBall() - 1);
					continue;
				}

				dto.setnScore(Integer.parseInt(dto.getScore()));
				dto.setPin(dto.getPin() - dto.getnScore());
				dto.setTotal(dto.getTotal() + dto.getnScore());

				dto.setPrintTemp("출력 = " + dto.getScore() + "[" + dto.getFrame() + ", " + dto.getBall() + ", ");
				if (!dto.isLastBall())
					dto.setResult(dto.getFrame() - 1, dto.getBall() - 1, dto.getnScore());

				if (dto.getFrame() < LAST_FRAME) {
					if (dto.getBall() == FIRST_BALL) {
						if (dto.getNowStatus("Spare")) {
							dto.setTotal(dto.getTotal() + dto.getnScore());
							dto.setNowStatus("");
						} else if (dto.getNowStatus("Strike")) {
							dto.setTotal(dto.getTotal() + dto.getnScore());
						} else if ((dto.getNowStatus("Turkey") || dto.getNowStatus("Double"))) {
							dto.setTotal(dto.getTotal() + dto.getnScore() * 2);
						}
					} else {
						if (isOneOfStrikeTurkeyDouble(dto)) {
							dto.setTotal(dto.getTotal() + dto.getnScore());
							dto.setNowStatus("");
						}
					}

					if (dto.getPin() == EMPTY_PIN) {
						if (dto.getBall() == FIRST_BALL) {
							if (dto.getNowStatus("Turkey")) {
								dto.setTotalTemp(dto.getTotal() + "+10+10+");
								dto.setNowStatus("Turkey");
							} else if (dto.getNowStatus("Double")) {
								dto.setTotalTemp(dto.getTotalTemp() + "10+");
								dto.setNowStatus("Turkey");
							} else if (dto.getNowStatus("Strike")) {
								dto.setTotalTemp(dto.getTotalTemp() + "10+");
								dto.setNowStatus("Double");
							} else {
								dto.setTotalTemp(dto.getTotal() + "+");
								dto.setNowStatus("Strike");
							}
							dto.setPrintTemp(dto.getPrintTemp() + dto.getTotalTemp());
						} else if (dto.getBall() == SECOND_BALL) {
							dto.setNowStatus("Spare");
							dto.setPrintTemp(dto.getPrintTemp() + dto.getTotal() + "+");
						}
					} else {
						if (isOneOfStrikeTurkeyDouble(dto) && dto.getBall() == FIRST_BALL) {
							dto.setPrintTemp(dto.getPrintTemp() + dto.getTotal() + "+");
						} else
							dto.setPrintTemp(dto.getPrintTemp() + dto.getTotal());
					}

				} else if (dto.getFrame() == LAST_FRAME) {
					if (!dto.isLastBall()) {
						if (dto.getNowStatus("Spare")) {
							dto.setTotal(dto.getTotal() + dto.getnScore());
							dto.setNowStatus("");
						} else if (isOneOfStrikeTurkeyDouble(dto)) {
							if (dto.getBall() == SECOND_BALL) {
								dto.setTotal(dto.getTotal() + dto.getnScore());
								dto.setNowStatus("");
							} else {
								if (dto.getNowStatus("Strike"))
									dto.setTotal(dto.getTotal() + dto.getnScore());
								else
									dto.setTotal(dto.getTotal() + dto.getnScore() * 2);
								if (dto.getNowStatus("Turkey"))
									dto.setTotalTemp(dto.getTotal() + "+10+" + dto.getScore() + "+");
								else
									dto.setTotalTemp(dto.getTotal() + dto.getScore() + "+");
							}
						}
					}

					if (isOneOfStrikeTurkeyDouble(dto))
						dto.setPrintTemp(dto.getPrintTemp() + dto.getTotalTemp());
					else
						dto.setPrintTemp(dto.getPrintTemp() + dto.getTotal());

				}

				if (dto.isLastBall())
					dto.setPrintTemp("출력 = " + dto.getScore() + "[ 10, 3," + dto.getTotal());

				nowScore(dto.getPrintTemp());

				if (dto.getPin() == EMPTY_PIN || dto.getBall() == SECOND_BALL) {
					dto.setPin(FULL_PIN);

					if (dto.getFrame() != LAST_FRAME || dto.isLastBall())
						break;
					else if (dto.getBall() != FIRST_BALL) {
						if (dto.getResult(9, 0)+ dto.getResult(9, 1) == FULL_PIN || dto.getResult(9, 0) == FULL_PIN) {
							dto.setLastBall(true);
							dto.setBall(dto.getBall()-1);
						} else
							break;
					}
				}

			}
		}

		show(dto);
		System.out.println("최종점수 :" + dto.getTotal());

		System.out.println("-------------------프로그램을 종료합니다-------------------");

	}

	private static boolean isOneOfStrikeTurkeyDouble(BowlingDTO dto) {
		return dto.getNowStatus("Strike") || dto.getNowStatus("Turkey") || dto.getNowStatus("Double");
	}

	private static void nowScore(String printTemp) {
		System.out.println(printTemp + "]");
	}

	private static boolean isNumber(String input) {
		char temp = ' ';

		for (int i = 0; i < input.length(); i++) {
			temp = input.charAt(i);

			if ('0' <= temp && temp <= '9') {
				return true;
			}
		}
		return false;
	}

	private static void show(BowlingDTO dto) {
		int resultLength = dto.getResultLength();
		System.out.println(
				"##################################################기록판###############################################");
		for (int i = 1; i <= resultLength / 2; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = 0; i < resultLength / 2; i++) {
			if (dto.getResult(i,0) == 10)
				System.out.print("\t|\tX, ");
			else if ((dto.getResult(i,0) + dto.getResult(i,1)) == 10) {
				System.out.print("\t|\t" + dto.getResult(i,0) + ", /");
			} else
				System.out.print("\t|\t" + dto.getResult(i,0) + ", " + dto.getResult(i,1) + "");
		}
		System.out.println("\t|");

		System.out.println(
				"------------------------------------------------------------------------------------------------------");
		for (int i = resultLength / 2 + 1; i <= resultLength; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i =resultLength / 2; i < resultLength - 1; i++) {
			if (dto.getResult(i,0) == 10)
				System.out.print("\t|\tX, ");
			else if ((dto.getResult(i,0) + dto.getResult(i,1)) == 10) {
				System.out.print("\t|\t" + dto.getResult(i,0) + ", /");
			} else
				System.out.print("\t|\t" + dto.getResult(i,0) + ", " + dto.getResult(i,1) + "");
		}

		if (dto.getResult(9,0) + dto.getResult(9,1) == 20)
			System.out.print("\t|\tX, X, ");
		else if (dto.getResult(9,0) + dto.getResult(9,1) == 10)
			System.out.print("\t|\t" + dto.getResult(9,0) + ", /, ");
		else if (dto.getResult(9,0) + dto.getResult(9,1) > 10)
			System.out.print("\t|\tX, " + dto.getResult(9,1) + ", ");
		else
			System.out.println("\t|\t" + dto.getResult(9,0) + ", " + dto.getResult(9,1) + ", 0|");

		if (dto.getResult(9,0) + dto.getResult(9,1) >= 10) {
			if (dto.getScore().equals("10"))
				System.out.println("X\t|");
			else if ((dto.getResult(9,0) + dto.getResult(9,1)) != 10 && (dto.getnScore() + dto.getResult(9,1)) == 10)
				System.out.println("/\t|");
			else
				System.out.println(dto.getScore() + "\t|");
		}

		System.out.println(
				"##################################################기록판###############################################");
	}

	private static boolean isCorrect(String score, int pin) {

		if (!isNumber(score)) {
			System.out.println("숫자값만 입력해주세요!!!");
			return false;
		} else if (Integer.parseInt(score) < 0 || Integer.parseInt(score) > pin) {
			System.out.println("점수는 0-" + pin + "만 입력해주세요!!!");
			return false;
		}

		return true;
	}

}
