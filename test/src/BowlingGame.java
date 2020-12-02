import java.util.HashMap;
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

		String score = "";
		String totalTemp = "";
		String printTemp = "";
		int[][] result = { { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 }, { 0, 0 },
				{ 0, 0 } };

		int ballCnt = 0;
		int ball = 1;
		int pin = 10;
		int frame = 1;
		int total = 0;
		int nScore = 0;
		boolean lastBall = false;

		Map<String, Boolean> nowStatus = new HashMap<String, Boolean>();

		nowStatus.put("isTurkey", false);
		nowStatus.put("isStrike", false);
		nowStatus.put("isSpare", false);
		nowStatus.put("isDouble", false);

		System.out.println("-------------------프로그램을 시작합니다-------------------");

		for (frame = FIRST_FRAME; frame <= LAST_FRAME; frame++) {
			if (score.equals("y"))
				break;
			for (ball = FIRST_BALL; ball <= SECOND_BALL; ball++) {

				// 몇 번째 투구
				ballCnt++;
				System.out.print("> " + ballCnt + " 번째 입력 값 ");
				score = roll.nextLine();

				if (score.equals("")) {
					System.out.print("정말 종료하시겠습니까? y/n : ");
					score = roll.nextLine();

					if (score.equals("y")) {
						break;
					}
					System.out.println("다시 시작합니다...");
					ballCnt--;
					ball--;
					continue;
				}

				else if (!isCorrect(score, pin)) {
					ballCnt--;
					ball--;
					continue;
				}

				nScore = Integer.parseInt(score);
				pin -= nScore;
				result[frame - 1][ball - 1] = nScore;
				total += nScore;
				printTemp = "출력 = " + score + "[" + frame + ", " + ball + ", ";

				if (frame < 10) {
					if (ball == FIRST_BALL) {
						if (nowStatus.get("isSpare")) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						} else if (nowStatus.get("isStrike")) {
							total += nScore;
						} else if ((nowStatus.get("isTurkey") || nowStatus.get("isDouble"))) {
							total += nScore * 2;
						}
					} else {
						if (isOneOfStrikeTurkeyDouble(nowStatus)) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						}
					}

					if (pin == EMPTY_PIN) {
						if (ball == FIRST_BALL) {
							if (nowStatus.get("isTurkey")) {
								totalTemp = total + "+10+10+";
								nowStatus = updateStatus(nowStatus, "isTurkey");
							} else if (nowStatus.get("isDouble")) {
								totalTemp += "10+";
								nowStatus = updateStatus(nowStatus, "isTurkey");
							} else if (nowStatus.get("isStrike")) {
								totalTemp += "10+";
								nowStatus = updateStatus(nowStatus, "isDouble");
							} else {
								totalTemp = total + "+";
								nowStatus = updateStatus(nowStatus, "isStrike");
							}
							printTemp += totalTemp;
						} else if (ball == SECOND_BALL) {
							nowStatus = updateStatus(nowStatus, "isSpare");
							printTemp += total + "+";
						}
					} else {
						if (isOneOfStrikeTurkeyDouble(nowStatus) && ball == FIRST_BALL) {
							printTemp += totalTemp + score + "+";
						} else
							printTemp += total;
					}
					
				} else if (frame == LAST_FRAME) {
					if (!lastBall) {
						result[frame - 1][ball - 1] = nScore;

						if (nowStatus.get("isSpare")) {
							total += nScore;
							nowStatus = updateStatus(nowStatus, "");
						} else if (isOneOfStrikeTurkeyDouble(nowStatus)) {
							if (ball == SECOND_BALL) {
								total += nScore;
								nowStatus = updateStatus(nowStatus, "");
							} else {
								if (nowStatus.get("isStrike"))
									total += nScore;
								else
									total += nScore * 2;
								if (nowStatus.get("isTurkey"))
									totalTemp = total + "+10+" + score + "+";
								else
									totalTemp += score + "+";
							}
						}
					}

					if (isOneOfStrikeTurkeyDouble(nowStatus))
						printTemp += totalTemp;
					else
						printTemp += total;

				}

				if (lastBall)
					printTemp = "출력 = " + score + "[10, 3, " + total;

				nowScore(printTemp);

				if (pin == EMPTY_PIN || ball == SECOND_BALL) {
					pin = FULL_PIN;

					if (frame != LAST_FRAME || lastBall)
						break;
					else if (ball != FIRST_BALL) {
						if (result[9][0] + result[9][1] == FULL_PIN || result[9][0] == FULL_PIN) {
							lastBall = true;
							ball--;
						} else
							break;
					}
				}

			}
		}

		show(result, score);
		System.out.println("최종점수 :" + total);

		System.out.println("-------------------프로그램을 종료합니다-------------------");

	}

	private static boolean isOneOfStrikeTurkeyDouble(Map<String, Boolean> nowStatus) {
		return nowStatus.get("isStrike") || nowStatus.get("isTurkey") || nowStatus.get("isDouble");
	}

	private static void nowScore(String printTemp) {
		System.out.println(printTemp + "]");
	}

	private static Map<String, Boolean> updateStatus(Map<String, Boolean> nowStatus, String update) {
		nowStatus.replaceAll((key, value) -> value = false);
		if (update != "")
			nowStatus.replace(update, true);
		return nowStatus;

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

	private static void show(int[][] result, String score) {
		System.out.println(
				"##################################################기록판###############################################");
		for (int i = 1; i <= result.length / 2; i++) {
			System.out.print("\t|\t" + i);
		}
		System.out.println("\t|");

		for (int i = 0; i < result.length / 2; i++) {
			if (result[i][0] == 10)
				System.out.print("\t|\tX, ");
			else if ((result[i][0] + result[i][1]) == 10) {
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
			if (result[i][0] == 10)
				System.out.print("\t|\tX, ");
			else if ((result[i][0] + result[i][1]) == 10) {
				System.out.print("\t|\t" + result[i][0] + ", /");
			} else
				System.out.print("\t|\t" + result[i][0] + ", " + result[i][1] + "");
		}

		if (result[9][0] + result[9][1] == 20)
			System.out.print("\t|\tX, X, ");
		else if (result[9][0] + result[9][1] == 10)
			System.out.print("\t|\t" + result[9][0] + ", /, ");
		else if (result[9][0] + result[9][1] > 10)
			System.out.print("\t|\tX, " + result[9][1] + ", ");
		else
			System.out.println("\t|\t" + result[9][0] + ", " + result[9][1] + ", 0");

		if (result[9][0] + result[9][1] >= 10) {
			if (score.equals("10"))
				System.out.println("X\t|");
			else if ((result[9][0] + result[9][1]) != 10 && (Integer.parseInt(score) + result[9][1]) == 10)
				System.out.println("/\t|");
			else
				System.out.println(score + "\t|");
		}

		System.out.println(
				"##################################################기록판###############################################");
	}

	private static boolean isCorrect(String score, int pin) {
		BowlingGame bowlingGame = new BowlingGame();

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
