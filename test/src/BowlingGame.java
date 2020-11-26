import java.util.Random;
import java.util.Scanner;

public class BowlingGame {
	public boolean isNumber(String input) {
		char temp = ' ';

		for (int i = 0; i < input.length(); i++) {
			temp = input.charAt(i);

			if ('0' <= temp && temp <= '9') {
				return true;
			}
		}
		return false;
	}

	public void show(int[][] result, String score) {
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

		for (int i = result.length / 2; i < result.length; i++) {
			if (result[i][0] == 10)
				System.out.print("\t|\tX, ");
			else if ((result[i][0] + result[i][1]) == 10) {
				System.out.print("\t|\t" + result[i][0] + ", /");
			} else
				System.out.print("\t|\t" + result[i][0] + ", " + result[i][1] + "");
		}
		if (result[9][1] == 10)
			System.out.print("X");
		if (score.equals("10"))
			System.out.println(", X\t|");
		else
			System.out.println(", " + score + "\t|");

		System.out.println(
				"##################################################기록판###############################################");
	}

	public boolean isCorrect(int frame, String score, int pin) {
		BowlingGame bowlingGame = new BowlingGame();

		if (!bowlingGame.isNumber(score)) {
			System.out.println("숫자값만 입력해주세요!!!");
			return false;
		} else if (Integer.parseInt(score) < 0 && Integer.parseInt(score) > pin) {
			System.out.println("점수는 0-" + pin + "만 입력해주세요!!!");
			return false;
		}

		return true;
	}

	public static void main(String[] args) {
		BowlingGame bowlingGame = new BowlingGame();
		Random random = new Random();
		Scanner input = new Scanner(System.in);

		String score = "";
		String printBonus = "";
		String printTemp = "";
		int[][] result = new int[10][2];
		int[][] test = { { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 }, { 10, 0 },
				{ 10, 0 }, { 0, 0 }, { 0, 5 } };

		int ballCnt = 0;
		int ball = 1;
		int pin = 10;
		int frame = 1;
		int total = 0;
		int strikeCnt = 0;

		boolean isLastBall = false;
		boolean isSpare = false;
		boolean isStrike = false;

		System.out.println("-------------------프로그램을 시작합니다-------------------");
		for (frame = 1; frame <= 9; frame++) {
			for (ball = 1; ball <= 2; ball++) {

				if (isStrike && ball == 2)
					break;

				// 몇 번째 투구
				ballCnt++;

				System.out.print("> " + ballCnt + " 번째 입력 값 ");
				 score = input.nextLine();
				// score = random.nextInt(pin + 1) + "";
//				score = 5 + "";
				// score = test[frame - 1][ball - 1] + "";
				if (score.equals("")) {
					System.out.print("정말 종료하시겠습니까? y/n : ");
					score = input.nextLine();
					
					if (input.equals("y")) {
						return;
					}
					System.out.println("다시 시작합니다...");
					frame--;
					continue;
				}
				if(!bowlingGame.isCorrect(frame, score, pin))
				{
					continue;
				}

				// 쓰러트린 핀의 개수 구하기
				pin -= Integer.parseInt(score);

				if (isSpare) {
					total += Integer.parseInt(score);
					isSpare = false;
				}

				if (isStrike) {
					isStrike = false;
				}

				if (pin == 0) {
					// 스트라이크 처리
					if (ball == 1) {
						total += 10;
						result[frame - 1][1] = 0;
						isStrike = true;
						// 스페어 처리
					} else {
						total += Integer.parseInt(score);
						isSpare = true;
					}
				} else {
					total += Integer.parseInt(score);
				}

				result[frame - 1][ball - 1] = Integer.parseInt(score);
				System.out.println("출력 = " + score + "[" + frame + ", " + ball + ", " + total + "]");
				printBonus = "";

				if (pin == 0 || ball == 2)
					pin = 10;
			}
		}
		bowlingGame.show(result, score);
		System.out.println("최종점수 :" + total);
		System.out.println("-------------------프로그램을 종료합니다-------------------");

	}

}
