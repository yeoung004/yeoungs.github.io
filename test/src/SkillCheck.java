import java.util.Scanner;

public class SkillCheck {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int w = 0;
		int h = 0;
		w = input.nextInt();
		h = input.nextInt();
		long answer = 0;

		if (w == h)
			answer = w / 2;
		else
			answer = (w - h)*(w - h);

		System.out.println(w*h-answer);

	}

}
