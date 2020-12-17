package Bowling;

import java.util.List;

public class BowlingStart {

	BowlingRolling bowlingRolling;
	BowlingScore bowlingScore;
	BowlingScoreRecord bowlingScoreShow;

	public BowlingStart() {
		bowlingRolling = new BowlingRolling();
		bowlingScore = new BowlingScore();
		bowlingScoreShow = new BowlingScoreRecord();
	}

	// true: test모드, false: 일반모드
	public int[] start(boolean mode, int[] lastTestPin, int[][][] testPins, List<UserDTO> palyersDto) {
		int check = 0;

		for (int frame = UserDTO.FIRST_FRAME; frame <= UserDTO.LAST_FRAME; frame++) {
			for (UserDTO userDto : palyersDto) {
				for (int ball = UserDTO.FIRST_BALL; ball <= UserDTO.SECOND_BALL; ball++) {
					userDto.setFrame(frame);
					userDto.setBall(ball);

					if (mode)
						check = bowlingRolling.rolling(testPins[userDto.getPlayerNumber() - 1], mode, userDto);
					else
						check = bowlingRolling.rolling(null, mode, userDto);

					if (check == 0) {
						break;
					} else if (check == 1) {
						userDto.setBall(userDto.getBall() - 1);
						userDto.setBallCnt(userDto.getBallCnt() - 1);
						continue;
					}

					bowlingScore.setScore(userDto);
					bowlingScoreShow.setNowScore(userDto);
					bowlingScoreShow.nowScore(userDto.getPrintTemp());

					if (userDto.isLastBall() || bowlingRolling.fillBall(userDto)) {
						break;
					} else if (userDto.isLastBall()) {
						ball--;
					}
				}
			}
		}
		if (mode) {
			int result[] = new int[palyersDto.size()];
			for (int i = 0; i < palyersDto.size(); i++) {
				if ((palyersDto.get(i).getResult(9, 0) + palyersDto.get(i).getResult(9, 1)) >= 10)
					result[i] = palyersDto.get(i).getTotal() + lastTestPin[i] - palyersDto.get(i).getResult(9, 1);
				else
					result[i] = palyersDto.get(i).getTotal();
			}
			return result;
		}

		return null;
	}
}
