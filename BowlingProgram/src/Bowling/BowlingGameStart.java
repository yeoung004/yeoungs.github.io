package Bowling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BowlingGameStart {

	BowlingRolling bowlingRolling;
	BowlingScore bowlingScore;
	BowlingScoreRecord bowlingScoreRecord;

	public BowlingGameStart() {
		bowlingRolling = new BowlingRolling();
		bowlingScore = new BowlingScore();
		bowlingScoreRecord = new BowlingScoreRecord();
	}

	// 0: test모드, 1: 일반모드, 2:이어하기
	public int[] start(int mode, int[] lastTestPin, int[][][] testPins, List<UserDTO> playersDto) throws IOException {
		int check = 0;
		int player = 0;
		int frame = UserDTO.FIRST_FRAME;
		int ball = UserDTO.FIRST_BALL;
		Map<String, Integer> gameData = new HashMap<String, Integer>();
		String[] loadGame = new String[3];

		File gameDatafile = new File("gameBackup.txt");
		File playerDatafile = new File("playerBackup.txt");

		if (mode == 2) {
			BufferedReader reader = new BufferedReader(new FileReader(gameDatafile));
			loadGame = reader.readLine().toString().split("/");
			frame = Integer.parseInt(loadGame[0]);
			ball = Integer.parseInt(loadGame[1]);
			player = Integer.parseInt(loadGame[2]);
			reader.close();
		}

		for (; frame <= UserDTO.LAST_FRAME; frame++) {
			for (; player < playersDto.size(); player++) {
				for (; ball <= UserDTO.SECOND_BALL; ball++) {
					
					gameData.put("frame", frame);
					gameData.put("ball", ball);
					gameData.put("player", player);
					bowlingScoreRecord.backup(playersDto, gameData, gameDatafile, playerDatafile);
					
					playersDto.get(player).setFrame(frame);
					playersDto.get(player).setBall(ball);

					if (mode == 0)
						check = bowlingRolling.rolling(testPins[playersDto.get(player).getPlayerNumber() - 1], true,
								playersDto.get(player));
					else
						check = bowlingRolling.rolling(null, false, playersDto.get(player));

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

		bowlingScoreRecord.resetFiles(gameDatafile, playerDatafile);

		// 테스트용
		if (mode == 0) {
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
