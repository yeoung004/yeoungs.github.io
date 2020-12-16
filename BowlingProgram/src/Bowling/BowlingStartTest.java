package Bowling;

import static org.junit.Assert.assertArrayEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.junit.Test;

public class BowlingStartTest {
	BowlingStart start = new BowlingStart();
	List<UserDTO> palyersDto = new ArrayList<UserDTO>();
	testScore testResult = new testScore();
	int testResults[];
	int testPin[][][];
	int players = 0;
	int lastTestPin[];

	Scanner numberOfPlayer = new Scanner(System.in);

	public void testPerfectGame() throws Exception {
		System.out.println("Test할 인원수를 적어주세요");
		players = numberOfPlayer.nextInt();
		testResults = new int[players];
		lastTestPin = new int[players];
		testPin = new int[players][10][2];

		for (int player = 0; player < players; player++) {
			for (int i = 0; i < testPin[player].length; i++) {
				for (int j = 0; j < testPin[player][i].length; j++) {
					testPin[player][i][j] = 10;
				}
			}
			lastTestPin[player] = 10;
		}
		for (int i = 0; i < testResults.length; i++) {
			testResults[i] = 300;
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
		assertArrayEquals(testResults, start.start(true, lastTestPin, testPin, palyersDto));

	}

	public void testHalfScoreGame() throws Exception {
		System.out.println("Test할 인원수를 적어주세요");
		players = numberOfPlayer.nextInt();
		testResults = new int[players];
		lastTestPin = new int[players];
		testPin = new int[players][10][2];

		for (int player = 0; player < players; player++) {
			for (int i = 0; i < testPin[player].length; i++) {
				for (int j = 0; j < testPin[player][i].length; j++) {
					testPin[player][i][j] = 5;
				}
			}
			lastTestPin[player] = 5;

		}

		for (int i = 0; i < testResults.length; i++) {
			testResults[i] = 150;
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
		assertArrayEquals(testResults, start.start(true, lastTestPin, testPin, palyersDto));

	}

	public void nomalScoreGame() throws Exception {
		System.out.println("Test할 인원수를 적어주세요");
		players = numberOfPlayer.nextInt();
		testResults = new int[players];
		lastTestPin = new int[players];
		testPin = new int[players][10][2];

		for (int player = 0; player < players; player++) {
			for (int i = 0; i < testPin[players].length; i++) {
				testPin[player][i][0] = 3;
				testPin[player][i][1] = 5;
			}
			lastTestPin[player] = 3;
		}

		for (int i = 0; i < testResults.length; i++) {
			testResults[i] = 60;
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
		assertArrayEquals(testResults, start.start(true, lastTestPin, testPin, palyersDto));

	}

	@Test
	public void randomScoreGame() throws Exception {
		int limit = 10;
		int temp;

		System.out.println("Test할 인원수를 적어주세요");
		players = numberOfPlayer.nextInt();
		testResults = new int[players];
		lastTestPin = new int[players];
		testPin = new int[players][10][2];

		for (int player = 0; player < players; player++) {
			for (int i = 0; i < testPin[player].length; i++) {
				for (int j = 0; j < testPin[player][i].length; j++) {
					temp = (int) (Math.random() * (limit + 1));
					testPin[player][i][j] = temp;
					limit -= temp;
					if (limit == 0)
						break;
				}
				limit = 10;
			}
			lastTestPin[player] = (int) (Math.random() * (limit + 1));
			testResults[player] = testResult.getTestResult(testPin[player], lastTestPin[player]);
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
		assertArrayEquals(testResults, start.start(true, lastTestPin, testPin, palyersDto));

	}
}