package Bowling;
public class BowlingGame {
	public static void main(String[] args) {
		BowlingDTO bowlingDto = new BowlingDTO();
		BowlingRolling bowlingrolling = new BowlingRolling(bowlingDto);
		BowlingScore bowlingScore = new BowlingScore(bowlingDto);
		BowlingScoreShow bowlingScoreShow = new BowlingScoreShow(bowlingDto);
		
		boolean isExit = false;
		int check = 0;
		
		System.out.println("-------------------프로그램을 시작합니다-------------------");

		for (bowlingDto.setFrame(BowlingDTO.FIRST_FRAME); bowlingDto.getFrame() <= BowlingDTO.LAST_FRAME; bowlingDto
				.setFrame(bowlingDto.getFrame() + 1)) {
			if (isExit)
				break;
			for (bowlingDto.setBall(BowlingDTO.FIRST_BALL); bowlingDto.getBall() <= BowlingDTO.SECOND_BALL;) {
				
				check = bowlingrolling.rolling();

				if (check == 0) {
					isExit = true;
					break;
				} else if (check == 1) {
					continue;
				}
				
				bowlingScore.setScore();
				bowlingScoreShow.setNowScore();
				bowlingScoreShow.nowScore();

				if (bowlingDto.getPin() == BowlingDTO.EMPTY_PIN || bowlingDto.getBall() == BowlingDTO.SECOND_BALL) {
					bowlingDto.setPin(BowlingDTO.FULL_PIN);

					if (bowlingDto.getFrame() != BowlingDTO.LAST_FRAME || bowlingDto.isLastBall())
						break;
					else if (bowlingDto.getBall() != BowlingDTO.FIRST_BALL) {
						if (bowlingDto.getResult(9, 0) + bowlingDto.getResult(9, 1) == BowlingDTO.FULL_PIN
								|| bowlingDto.getResult(9, 0) == BowlingDTO.FULL_PIN) {
							bowlingDto.setLastBall(true);
							bowlingDto.setBall(bowlingDto.getBall() - 1);
						} else
							break;
					}
				}

				bowlingDto.setBall(bowlingDto.getBall() + 1);
			}
		}
		bowlingScoreShow.show();
		System.out.println("-------------------프로그램을 종료합니다-------------------");

	}

}
