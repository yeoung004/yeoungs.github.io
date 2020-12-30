package Bowling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class BowlingStart {

	BowlingRolling bowlingRolling;
	BowlingScore bowlingScore;
	BowlingScoreRecord bowlingScoreRecord;

	public BowlingStart() {
		bowlingRolling = new BowlingRolling();
		bowlingScore = new BowlingScore();
		bowlingScoreRecord = new BowlingScoreRecord();
	}

	// true: test모드, false: 일반모드
	public int[] start(boolean mode, int[] lastTestPin, int[][][] testPins, List<UserDTO> playersDto)
			throws IOException {
		int check = 0;
		int player = 0;
		int frame = UserDTO.FIRST_FRAME;
		int ball = UserDTO.FIRST_BALL;
		File file = new File("backup.txt");
		
		if (!file.exists())
			file.createNewFile();
		
		for (; frame <= UserDTO.LAST_FRAME; frame++) {
			for (; player < playersDto.size(); player++) {
				
				bowlingScoreRecord.backup(playersDto, file);
				for (; ball <= UserDTO.SECOND_BALL; ball++) {
					playersDto.get(player).setFrame(frame);
					playersDto.get(player).setBall(ball);

					if (mode)
						check = bowlingRolling.rolling(testPins[playersDto.get(player).getPlayerNumber() - 1], mode,
								playersDto.get(player));
					else
						check = bowlingRolling.rolling(null, mode, playersDto.get(player));

					if (check == 0) {
						return null;
					} else if (check == 1) {
						ball--;
						continue;
					}

					bowlingScore.setScore(playersDto.get(player));
					bowlingScoreRecord.setNowScore(playersDto.get(player));
					bowlingScoreRecord.nowScore(playersDto.get(player).getPrintTemp());

					if (playersDto.get(player).isLastBall() || bowlingRolling.fillBall(playersDto.get(player))) {
						break;
					} else if (playersDto.get(player).isLastBall()) {
						ball--;
					}
				}
				ball = UserDTO.FIRST_BALL;
			}
			player = 0;
		}

		// 테스트용
		if (mode) {
			int result[] = new int[playersDto.size()];
			for (int i = 0; i < playersDto.size(); i++) {
				if ((playersDto.get(i).getResult(9, 0) + playersDto.get(i).getResult(9, 1)) >= 10)
					result[i] = playersDto.get(i).getTotal() + lastTestPin[i] - playersDto.get(i).getResult(9, 1);
				else
					result[i] = playersDto.get(i).getTotal();
			}
			return result;
		}

		return null;
	}
}
