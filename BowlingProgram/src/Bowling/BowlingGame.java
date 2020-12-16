package Bowling;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

		while (players < 1 || players > 5) {
			System.out.println("인원수를 설정해 주세요(1-5 사이)");
			players = player.nextInt();

			if (players < 1 || players > 5)
				System.out.println("1명 이상의 선수를 선택해 주세요");
		}

		for (int i = 0; i < players; i++) {
			palyersDto.add(new UserDTO(i + 1));
		}
	}

}
