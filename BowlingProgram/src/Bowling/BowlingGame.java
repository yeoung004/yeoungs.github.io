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

		File file = new File("backup.txt");

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
				} else {
					System.out.println("새 게임을 시작합니다...");
					setPlayer(palyersDto);
				}
				
			}
		} else {
			System.out.println("새 게임을 시작합니다...");
			setPlayer(palyersDto);
		}

		start.start(false, null, null, palyersDto);

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
		JSONObject players = (JSONObject)paser.parse(dataTemp);
		int num = 1;
		int [][] result = new int [10][2];
		Set key = players.keySet();
		Iterator iter = key.iterator();
		
		while(iter.hasNext()){
			System.out.println(paser.parse((String) iter.next()).toString());
			JSONObject player = (JSONObject)paser.parse((String) iter.next());
			JSONObject data = (JSONObject) player.get(num);
			JSONObject nowStatus = (JSONObject)data.get("nowStatus");
			
			JSONArray resultTemp = (JSONArray)data.get("result");
			for (int i = 0; i < resultTemp.size(); i++) {
				JSONArray resultTmp = (JSONArray)resultTemp.get(i);
				result[i][0] = (int) resultTmp.get(0);
				result[i][1] = (int) resultTmp.get(1);
				System.out.println(result[i][0] + "/" + result[i][1]);
			}
			
			palyersDto.add(new UserDTO((String)data.get("nScore"), result, (Map)nowStatus, 
					(int)data.get("ball"), (int)data.get("pin"), (int)data.get("frame"),
					(int)data.get("nScore"), (boolean)data.get("lastball"),
					(String)data.get("printTemp"), (int)data.get("ballCnt"), 
					(int)data.get("total"), (String)data.get("totalTemp"),num));
			num++;
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
