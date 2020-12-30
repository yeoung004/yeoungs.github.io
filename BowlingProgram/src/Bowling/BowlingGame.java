package Bowling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BowlingGame {
	public static void main(String[] args) throws IOException, ParseException {
		List<UserDTO> palyersDto = new ArrayList<UserDTO>();
		BowlingStart start = new BowlingStart();
		BowlingScoreRecord bowlingScoreRecord = new BowlingScoreRecord();
		Scanner input = new Scanner(System.in);
		String readGame = "";
		String dataTemp = "";
		int mode = 1;

		File file = new File("playerBackup.txt");

		System.out.println("-------------------프로그램을 시작합니다-------------------");

		if (file.exists()) {
			BufferedReader reader = new BufferedReader(new FileReader(file));
			dataTemp = reader.readLine();
			reader.close();

			if (dataTemp != "") {
				System.out.println("진행하던 게임이 있습니다. 계속해서 하시겠습니까?(y)");
				readGame = input.next();
				
				if (readGame.equals("y") || readGame.equals("Y")) {
					System.out.println("게임을 이어서 시작합니다...");
					loadData(palyersDto, dataTemp);
					mode = 2;
				} else {
					System.out.println("새 게임을 시작합니다...");
					setPlayer(palyersDto);
					mode = 1;
				}

			}
		} else {
			System.out.println("새 게임을 시작합니다...");
			setPlayer(palyersDto);
			mode = 1;
		}

		start.start(mode, null, null, palyersDto);

		for (int i = 0; i < palyersDto.size(); i++) {
			System.out.println((i + 1) + "번 째 선수");
			bowlingScoreRecord.show(palyersDto.get(i).getResult(), palyersDto.get(i).getnScore(),
					palyersDto.get(i).getTotal());
			System.out.println();
		}
		System.out.println("-------------------프로그램을 종료합니다-------------------");
	}

	private static void loadData(List<UserDTO> palyersDto, String dataTemp) throws ParseException {

		JSONParser paser = new JSONParser();
		JSONObject players = (JSONObject) paser.parse(dataTemp);
		int[][] result = new int[10][2];
		Set key = players.keySet();
		Iterator iter = key.iterator();

		while (iter.hasNext()) {
			JSONObject player = (JSONObject) players.get(iter.next().toString());
			JSONObject nowStatus = (JSONObject) player.get("nowStatus");

			JSONArray resultTemp = (JSONArray) player.get("result");
			for (int i = 0; i < resultTemp.size(); i++) {
				JSONArray resultTmp = (JSONArray) resultTemp.get(i);
				result[i][0] = Integer.parseInt(resultTmp.get(0).toString());
				result[i][1] = Integer.parseInt(resultTmp.get(1).toString());
			}

			palyersDto.add(new UserDTO(player.get("nScore").toString(), result, nowStatus,
					Integer.parseInt(player.get("ball").toString()), Integer.parseInt(player.get("pin").toString()),
					Integer.parseInt(player.get("frame").toString()), Integer.parseInt(player.get("nScore").toString()),
					(boolean) player.get("lastBall"), player.get("printTemp").toString(),
					Integer.parseInt(player.get("ballCnt").toString()),
					Integer.parseInt(player.get("total").toString()), player.get("totalTemp").toString(),
					Integer.parseInt(player.get("playerNumber").toString())));

		}

	}

	private static void setPlayer(List<UserDTO> palyersDto) {
		Scanner player = new Scanner(System.in);
		int players = 0;
		String input = "";
		String pattern = "^[1-5]$";

		while (true) {
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
