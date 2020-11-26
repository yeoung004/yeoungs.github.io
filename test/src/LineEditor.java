import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class LineEditor {
	public void show(Map<Integer, String> line_text) {
		System.out.println("===================================================");
		for (int i = 1; i <= line_text.size(); i++) {
			System.out.printf("%4d : %s\n", i, line_text.get(i));
		}
		System.out.println("===================================================");
	}

	public void help() {
		System.out.println("OPEN [파일명] : 파일을 읽어 메모리에 저장한다. (예, OPEN file.txt)\n"
				+ "SHOW : 메모리의 내용을 라인번호와 함께 표시한다. (예, 0002:내용)\n" + "ADD : 입력받은 내용을 마지막에 추가한다. (ADD  내용 입력)\n"
				+ "INS [번호] : 입력받은 내용을 라인번호 앞에 추가한다. (INS 2  입력)\n" + "EDIT [번호] : 라인번호의 내용을 표시하고 수정할 내용을 입력한다.\n"
				+ "DEL [번호] : 해당 라인번호의 내용을 삭제한다.\n" + "SAVE : 편집된 내용을 파일에 저장한다.\n"
				+ "EXIT : 종료할 것인지 묻고 Y를 선택하면 종료한다.\n");
	}

	public boolean isNumber(String input) {
		char temp = ' ';

		for (int i = 0; i < input.length(); i++) {
			temp = input.charAt(i);

			if (!('0' <= temp && temp <= '9')) {
				return false;
			}
		}
		return true;
	}

	public void delete(Map<Integer, String> line_text, int index) {
		if (index != 1) {
			for (int i = index+1; i <= line_text.size(); i++) {
				line_text.replace(i - 1, line_text.get(i));
			}
		}else{
			for (int i = 2; i <= line_text.size(); i++) {
				line_text.replace(i-1, line_text.get(i));
			}
		}
		line_text.remove(line_text.size());
	}

	public static void main(String[] args) throws IOException {
		LineEditor editor = new LineEditor();
		Scanner input = new Scanner(System.in);

		// 파일 이름 받아오는
		String command = "";
		String text = "";

		List<String> commandCheck;
		Map<Integer, String> line_text = new HashMap<Integer, String>();
		BufferedWriter writer = null;
		BufferedReader reader = null;
		File file = null;

		int lineNum = 1;
		boolean openCheck = false;
		boolean isWriting = true;
		boolean isSave = true; // 저장됬으면 true 아니면 false

		System.out.println("===================================================");
		System.out.println("신나는 라인 에디터 명령어를 입력해주세요!(HELP명령어를 입력하세요)");

		while (true) {
			System.out.print("CMD : ");
			command = input.nextLine();
			commandCheck = Arrays.asList(command.split(" "));

			if (commandCheck.size() == 1) {
				if ((commandCheck.get(0).equals("SAVE") || commandCheck.get(0).equals("save")) && openCheck) {
					// 저장전에 파일이 존재하는지 확인
					if (file.canRead() && file.canWrite() && file.exists()) {
						writer = new BufferedWriter(new FileWriter(file));
						for (int i = 1; i <= line_text.size(); i++) {
							writer.write(line_text.get(i) + "\n");
						}
						writer.flush();
						writer.close();
						isSave = true;
						System.out.println("=======================저장끝=======================");
					} else {
						System.out.println("파일이 존재하지 않습니다.");
					}

				} else if ((commandCheck.get(0).equals("SHOW") || commandCheck.get(0).equals("show")) && openCheck) {
					editor.show(line_text);

				} else if ((commandCheck.get(0).equals("ADD") || commandCheck.get(0).equals("add")) && openCheck) {
					System.out.println("=======================작성중=======================");
					System.out.print(">>");
					text = input.nextLine();
					line_text.put(lineNum, text);
					System.out.println("=======================작성끝=======================");
					lineNum++;
					isSave = false;

				} else if (commandCheck.get(0).equals("HELP") || commandCheck.get(0).equals("help")) {
					editor.help();
				} else if (commandCheck.get(0).equals("EXIT") || commandCheck.get(0).equals("exit")) {
					System.out.print("정말 종료하시겠습니까?(y) : ");

					command = input.nextLine();
					if (command.equals("y")) {
						if (isSave) {
							if (!isWriting) {
								reader.close();
							}
							System.out.println("에디터를 종료합니다.");
							break;
						} else {
							System.out.print("변경된 사항이 있습니다 그래도 종료하시겠습니까(y) :");
							command = input.nextLine();
							if (command.equals("y")) {
								reader.close();
								System.out.println("에디터를 종료합니다.");
								break;
							} else {
								System.out.println("잘못 입력하셨습니다!");
								continue;
							}
						}
					} else {
						System.out.println("잘못 입력하셨습니다!");
						continue;
					}
				} else {
					System.out.println("잘못된 명령입니다. HELP를 입력하여 사용법을 확인해 주세요.");
					continue;
				}

			} else if (commandCheck.size() == 2) {
				if (commandCheck.get(0).equals("OPEN") || commandCheck.get(0).equals("open")) {
					if (isWriting) {
						file = new File("G:\\COUE\\workspace\\test\\src\\" + commandCheck.get(1));
						// 파일이 있는지 읽고 쓸수 있는지 확인하기
						if (file.canRead() && file.canWrite() && file.exists()) {
							reader = new BufferedReader(new FileReader(file));
							while ((text = reader.readLine()) != null) {
								line_text.put(lineNum, text);
								System.out.println(text);
								lineNum++;
								isWriting = false;
							}

							openCheck = true;
							System.out.println("=======================읽기성공=======================");

						} else {
							System.out.println("파일이 존재하지 않거나 읽을 수 없는 파일입니다.");
							continue;
						}
					} else {
						System.out.println("이미 작성중인 파일이 존재합니다. 종료후 다시 실행해 주세요.");
					}

				} else if (commandCheck.get(0).equals("INS") || commandCheck.get(0).equals("ins")) {
					if (editor.isNumber(commandCheck.get(1))) {
						if (line_text.containsKey(Integer.parseInt(commandCheck.get(1)))) {
							System.out.println("=======================수정중=======================");
							System.out.println("변경전");
							System.out.println(line_text.get(Integer.parseInt(commandCheck.get(1))));
							System.out.print(">>");
							text = input.nextLine();
							line_text.put(Integer.parseInt(commandCheck.get(1)),
									text + line_text.get(Integer.parseInt(commandCheck.get(1))));
							System.out.println("변경후");
							System.out.println(line_text.get(Integer.parseInt(commandCheck.get(1))));
							System.out.println("=======================수정끝=======================");
							isSave = false;
						} else {
							System.out.println("존재하지 않는 번호입니다");
							continue;
						}
					} else {
						System.out.println("존재하지 않는 번호입니다");
						continue;
					}
				} else if (commandCheck.get(0).equals("EDIT") || commandCheck.get(0).equals("edit")) {
					if (line_text.containsKey(Integer.parseInt(commandCheck.get(1)))) {
						System.out.println("=======================수정중=======================");
						System.out.println("변경전");
						System.out.println(line_text.get(Integer.parseInt(commandCheck.get(1))));
						System.out.print(">>");
						text = input.nextLine();
						line_text.replace(Integer.parseInt(commandCheck.get(1)),text);
						System.out.println("변경후");
						System.out.println(line_text.get(Integer.parseInt(commandCheck.get(1))));
						System.out.println("=======================수정끝=======================");
						
						isSave = false;
					} else {
						System.out.println("존재하지 않는 번호입니다");
						continue;
					}
					isSave = false;
					if (line_text.containsKey(Integer.parseInt(commandCheck.get(1)))) {
						editor.delete(line_text, Integer.parseInt(commandCheck.get(1)));
						isSave = false;
						System.out.println("=======================삭제끝=======================");
					} else {
						System.out.println("존재하지 않는 번호입니다");
						continue;
					}
				} else {
					System.out.println("잘못된 명령입니다. HELP를 입력하여 사용법을 확인해 주세요.");
					continue;
				}
			} else {
				// 명령어 형식 오류
				System.out.println("잘못된 형식입니다. HELP를 입력하여 사용법을 확인해 주세요.");
				continue;
			}
		}

	}

}
