package PlusCalc;
import java.util.Scanner;

public class PlusCalc {
	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		//숫자를 입력받음
		String input = "";
		//결과 값을 저장
		double result = 0;
		//숫자확인
		boolean checkType = true;
		char temp = ' ';
		boolean checkDecimal = false;

		System.out.println("###########신나는 덧셈 프로그램###########");
		System.out.println("더하고자하는 숫자를 입력하세요(문자X/더하기만가능) (입력 종료시 그냥 엔터를 입력해 주세요)");
		
		while(true)
		{
			System.out.print(">>> ");
			input = in.nextLine();
			checkDecimal = false;
			checkType = true;

			//input에 아무런 값이 안들어 갈시 종료하기
			if(input.length() == 0)
			{
				System.out.print("정말 종료하시겠습니까? y/n : ");
				input = in.nextLine();				

				if(input.equals("y"))
				{
					System.out.println("종료합니다!!");
					break;
				}
				continue;
			}
			//숫자와 +,-,.이 아닌 문자 걸러내기
			for(int i = 0; i < input.length(); i++)
			{	
				//문자를 ascii값으로 변환 후 48 ~ 57까지 또는 45, 43, 46인지 검사
				temp = input.charAt(i);
				if(i==0 && (temp == '-' || temp == '+' || temp == '.') && input.length() == 1)
				{
					checkType = false;
					break;
				}
				//피연산자없이 기호들만 들어오는지 검사
				else if(!('0' <= temp && temp <= '9' || temp == '-' || temp == '+' || temp == '.')){
					checkType = false;
					break;
				}
				//음수 또는 양수 기호가 아닌 연산자로 들어오는지 검사
				else if( i != 0 && (temp == '-' || temp == '+')){
					checkType = false;
					break;					
				}
				//소숫점이 두번이상 들어가 있는지 검사
				else if(temp == '.'){
					if(checkDecimal){
						checkType = false;
						break;
					}
					checkDecimal = true;
				}

			}

			if(!checkType){
				System.out.println("입력오류 입니다 다시 입력해주세요!!");
				continue;
			}
			
			result += Double.parseDouble(input);
			System.out.printf("합계 : %.4f\n", result);

		}
	}
}
