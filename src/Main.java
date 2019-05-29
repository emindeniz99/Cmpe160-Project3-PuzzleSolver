import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

public class Main {

	public static void main(String[] args) throws FileNotFoundException {

		// time counter
//		long start = System.currentTimeMillis();

		Scanner scanner = new Scanner(new File(args[0]));

		PrintStream writer = new PrintStream(new File(args[1]));

//		while(scanner.hasNextLine()) {

		String[] input = scanner.nextLine().split("-");

//		if(input[0].equals("")) {
//			System.out.println("Finish");
//			break;
//		}

		int side = (int) (Math.sqrt(input.length));

		int[] table = new int[side * side];
		int zeroLoc = 0;

		for (int i = 0; i < input.length; i++) {
			int temp = Integer.parseInt(input[i]);
			if (temp == 0) {
				zeroLoc = i;
			}
			table[i] = temp;

		}

		Set<Long> history = new HashSet<>();
		// long for big numbers

		history.add(tableCode(table));

		Queue<Node> processOrder = new LinkedList<Node>();
	
		Node root = new Node(table, ' ', zeroLoc);
		processOrder.add(root);

		boolean bos = true;

		// AnswerNode
		Node answer = null;

		if (checkTable(processOrder.peek().data)) {
			processOrder.poll();
			bos = false;
			answer = root;
			answer.lastMove = ' ';
		}

		while (!processOrder.isEmpty()) {
			Node curr = processOrder.poll();
			int cz = curr.zeroLoc;
			char lm = curr.lastMove;
			int[] tb = curr.data.clone();

			if (lm != 'L') { // go right
				if (cz % side != side - 1) {
					int[] klon = tb.clone();
					klon[cz] = klon[cz + 1];
					klon[cz + 1] = 0;

					if (!history.contains(tableCode(klon))) {
						history.add(tableCode(klon));
						Node tempN = new Node(curr, klon, 'R', cz + 1);
						processOrder.add(tempN);
						curr.childs.add(tempN);
						if (checkTable(klon)) {
							bos = false;

							answer = tempN;
							break;

						}
					}

				}

			}
			if (lm != 'R') { // go left
				if (cz % side != 0) {
					int[] klon = tb.clone();
					klon[cz] = klon[cz - 1];
					klon[cz - 1] = 0;

					if (!history.contains(tableCode(klon))) {
						history.add(tableCode(klon));

						Node tempN = new Node(curr, klon, 'L', cz - 1);
						processOrder.add(tempN);
						curr.childs.add(tempN);

						if (checkTable(klon)) {
							bos = false;

							answer = tempN;
							break;

						}

					}

				}

			}
			if (lm != 'D') { // go up
				if (cz / side != 0) {
					int[] klon = tb.clone();
					klon[cz] = klon[cz - side];
					klon[cz - side] = 0;

					if (!history.contains(tableCode(klon))) {

						history.add(tableCode(klon));

						Node tempN = new Node(curr, klon, 'U', cz - side);
						processOrder.add(tempN);
						curr.childs.add(tempN);

						if (checkTable(klon)) {
							bos = false;

							answer = tempN;
							break;

						}
					}

				}

			}
			if (lm != 'U') { // go down
				if (cz / side != side - 1) {
					int[] klon = tb.clone();
					klon[cz] = klon[cz + side];
					klon[cz + side] = 0;

					if (!history.contains(tableCode(klon))) {

						history.add(tableCode(klon));

						Node tempN = new Node(curr, klon, 'D', cz + side);
						processOrder.add(tempN);
						curr.childs.add(tempN);

						if (checkTable(klon)) {
							bos = false;

							answer = tempN;
							break;

						}
					}

				}

			}

		}

		if (bos) {
			writer.println("N");
		} else {
			String s = "";
			while (answer.parent != null) {
				s = answer.lastMove + s;
				answer = answer.parent;
			}
			writer.println(s);
		}

//	}	

//		long end = System.currentTimeMillis();
//		
//		System.out.println("time diff = " + (end - start) + " ms");

		scanner.close();
		writer.close();
	}

	public static boolean checkTable(int[] table) {
		int len = table.length;

		for (int i = 1; i < len; i++) {
			if (table[i - 1] != i) {
				return false;
			}

		}
		return true;
	}

	public static long tableCode(int[] a) {
		if (a == null)
			return 0;

		long result = 1;
		for (int element : a)
			result = 61 * result + element;
		return result;

	}

}
