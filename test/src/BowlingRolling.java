import java.util.Scanner;

public class BowlingRolling {
	private BowlingDTO bowlingDto;
	private Scanner roll = new Scanner(System.in);
	
	public BowlingRolling(BowlingDTO bowlingDto) {
		this.bowlingDto = bowlingDto;
	}

	public int grammarCheck() {
		int check = 1;

		if (bowlingDto.getScore().equals("")) {
			System.out.print("정말 종료하시겠습니까? y/n : ");
			bowlingDto.setScore(roll.nextLine());
			if (bowlingDto.getScore().equals("y")) {
				check = 0;
			}
			System.out.println("다시 시작합니다...");
		}else if (isNumber(bowlingDto.getScore())) {
			check = 1;
			System.out.println("숫자값만 입력해주세요!!!");
		} else if (isScoreOfNumber()) {
			check = 1;
			System.out.println("점수는 0-" + bowlingDto.getPin() + "만 입력해주세요!!!");
		}else
			check = 2;

		return check;
	}

	public boolean isScoreOfNumber() {
		bowlingDto.setnScore(Integer.parseInt(bowlingDto.getScore()));
		return bowlingDto.getnScore() < 0 || bowlingDto.getnScore() > bowlingDto.getPin();
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

	public int rolling() {
		int check = 0;

		System.out.print("> " + bowlingDto.getBallCnt() + " 번째 입력 값 ");
		bowlingDto.setScore(roll.nextLine());
		check = grammarCheck();

		if (check == 2) {
			if (!bowlingDto.isLastBall())
				bowlingDto.setResult(bowlingDto.getFrame() - 1, bowlingDto.getBall() - 1, bowlingDto.getnScore());

			bowlingDto.setBallCnt(bowlingDto.getBallCnt() + 1);
			bowlingDto.setPin(bowlingDto.getPin() - bowlingDto.getnScore());
			bowlingDto.setTotal(bowlingDto.getTotal() + bowlingDto.getnScore());
		}
		
		return check;

	}

}
