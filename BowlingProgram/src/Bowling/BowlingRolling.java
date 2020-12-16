package Bowling;

import java.util.Scanner;

public class BowlingRolling {
	private Scanner roll = new Scanner(System.in);

	public boolean fillBall(UserDTO userDto) {
		if (userDto.getPin() == UserDTO.EMPTY_PIN || userDto.getBall() == UserDTO.SECOND_BALL) {
			userDto.setPin(UserDTO.FULL_PIN);

			if (userDto.getBall() != UserDTO.FIRST_BALL
					&& (userDto.getResult(9, 0) + userDto.getResult(9, 1) == UserDTO.STRIKE_SPARE
							|| userDto.getResult(9, 0) == UserDTO.STRIKE_SPARE)) {
				userDto.setLastBall(true);
				return false;
			} else if (userDto.getFrame() == UserDTO.LAST_FRAME)
				return false;
			else
				return true;
		}
		return false;
	}

	public int grammarCheck(String score, int pin) {
		int check = 1;

		if (score.equals("")) {
			System.out.print("정말 종료하시겠습니까? y/n : ");
			score = roll.nextLine();
			if (score.equals("y")) {
				check = 0;
			} else
				System.out.println("다시 시작합 니다...");
		} else if (isNumber(score)) {
			check = 1;
			System.out.println("숫자값만 입력해주세요!!!");
		} else if (isScoreOfNumber(Integer.parseInt(score), pin)) {
			check = 1;
			System.out.println("점수는 0-" + pin + "만 입력해주세요!!!");
		} else
			check = 2;

		return check;
	}

	public boolean isScoreOfNumber(int score, int pin) {
		return score < 0 || score > pin;
	}

	public boolean isNumber(String input) {
		char temp = ' ';

		for (int i = 0; i < input.length(); i++) {
			temp = input.charAt(i);

			if ('0' <= temp && temp <= '9') {
				return false;
			}
		}
		return true;
	}

	public int rolling(int[][] testPins, boolean mode, UserDTO userDto) {
		int check = 0;

		System.out.print(userDto.getPlayerNumber() + "번 선수" + "> " + userDto.getBallCnt() + " 번째 입력 값 ");
		if (mode)
			userDto.setScore(testPins[userDto.getFrame() - 1][userDto.getBall() - 1] + "");
		else
			userDto.setScore(roll.nextLine());
		check = grammarCheck(userDto.getScore(), userDto.getPin());

		if (check == 2) {
			userDto.setnScore(Integer.parseInt(userDto.getScore()));
			userDto.setBallCnt(userDto.getBallCnt() + 1);
			userDto.setPin(userDto.getPin() - userDto.getnScore());
			userDto.setTotal(userDto.getTotal() + userDto.getnScore());

			if (!userDto.isLastBall())
				userDto.setResult(userDto.getFrame() - 1, userDto.getBall() - 1, userDto.getnScore());
		}

		return check;

	}

}
