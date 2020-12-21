package Bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class BowlingGame {
	public static void main(String[] args) {
		List<UserDTO> palyersDto = new ArrayList<UserDTO>();
		BowlingStart start = new BowlingStart();
		BowlingScoreRecord bowlingScoreRecord = new BowlingScoreRecord();

		System.out.println("-------------------프로그램을 시작합니다-------------------");
		setPlayer(palyersDto);

		start.start(false, null, null, palyersDto);

		for (int i = 0; i < palyersDto.size(); i++) {
			System.out.println((i + 1) + "번 째 선수");
			bowlingScoreRecord.show(palyersDto.get(i).getResult(), palyersDto.get(i).getnScore(),
					palyersDto.get(i).getTotal());
			System.out.println();
		}

		System.out.println("-------------------프로그램을 종료합니다-------------------");
	}

	private static void setPlayer(List<UserDTO> palyersDto) {
		Scanner player = new Scanner(System.in);
		int players = 0;
		String input = "";
		boolean isCorrect = true;
		String pattern = "^[1-5]*$";

		while (true) {
			isCorrect = true;
			System.out.println("인원수를 설정해 주세요(1-5 사이)");
			input = player.next();

			if (!Pattern.matches(pattern, input)) {
				System.out.println("올바른 값을 입력해 주세요!!");
			} else {
				players = Integer.parseInt(input);
				break;
			}
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
	}

}
