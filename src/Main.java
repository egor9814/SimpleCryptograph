import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Scanner;

/**
 * Created by egor9814 on 03.09.2016.
 */
public class Main {

	public static void main(String[] args) throws Exception {
		switch (args.length){
			default:
				printUsage();
				break;
			case 2:
				if(args[0].startsWith("-e")){
					encode(args[0].substring(2), args[1]);
				} else if(args[0].startsWith("-d")){
					decode(args[0].substring(2), args[1]);
				} else {
					printUsage();
				}
				break;
		}
	}

	private static void printUsage(){
		System.out.println("usage:\n" +
				"    -gui    Start a GUI\n" +
				"    -e[count] <file>    encode file\n" +
				"    -d[count] <file>    decode file\n" +
				"      by default count equal 8");
	}

	private static void encode(String arg, String path) throws Exception {
		File in = new File(path);
		File out = new File(in.getAbsolutePath() + ".enc");

		String source = read(in);

		out.createNewFile();
		PrintWriter writer = new PrintWriter(new FileWriter(out));
		writer.print(Coder.encode(source, getCount(arg)));
		writer.close();
	}

	private static void decode(String arg, String path) throws Exception {
		File in = new File(path);
		File out = new File(in.getAbsolutePath() + ".dec");

		String source = read(in);

		out.createNewFile();
		PrintWriter writer = new PrintWriter(new FileWriter(out));
		writer.print(Coder.decode(source, getCount(arg)));
		writer.close();
	}

	private static String read(File in) throws FileNotFoundException {
		Scanner input = new Scanner(in);
		String source = "";
		while (true){
			if(input.hasNextLine()){
				source += input.nextLine();
			}
			if(input.hasNextLine()) source += "\n";
			else break;
		}
		input.close();
		return source;
	}

	private static int getCount(String arg){
		try {
			return Integer.parseInt(arg);
		} catch (Throwable t){
			return 8;
		}
	}
}
