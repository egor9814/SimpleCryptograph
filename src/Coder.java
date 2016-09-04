
import java.util.Random;

/**
 * Created by egor9814 on 03.09.2016.
 */
public class Coder {
	
	private Coder(){}

	private static char[] symbols = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private static String makeKey(int length){
		Random rnd = new Random();
		String key = "";
		for(int i = 0; i < length; i++){
			int pos = rnd.nextInt(symbols.length);
			char chr = symbols[pos];
			if(rnd.nextBoolean()){
				if(Character.isDigit(chr)){
					chr = symbols[9-pos];
				} else if(Character.isUpperCase(chr)){
					chr = Character.toLowerCase(chr);
				} else if(Character.isLowerCase(chr)){
					chr = symbols[(Math.abs(symbols.length - 10 - pos))];
				}
			}
			key += chr;
		}
		return key;
	}
	private static char[] apply(char[] chars, String key){
		int len = key.length();
		for(int i = 0; i < chars.length; i++){
			chars[i] ^= key.charAt(i % len);
		}
		return chars;
	}

	public static String encode(String input, int bits) throws Exception {
		int keylen = input.length() / bits;
		String key = makeKey(keylen);
		char[] chars = input.toCharArray();
		chars = apply(chars, key);
		String res = "";
		int pos = 0;
		for(int i = 0; i < chars.length; i++){
			if(i % bits == 0 && pos < keylen) {
				res += key.charAt(pos);
				pos++;
			}
			res += chars[i];
		}
		String count = Integer.toString(key.length());
		count = count.length() + count;
		return count + res;
	}

	public static String decode(String input, int bits) throws Exception {
		int count = Integer.parseInt(input.substring(0, 1));
		input = input.substring(1);
		int keylen = Integer.parseInt(input.substring(0, count));
		String key = "";
		char[] chars = input.substring(count).toCharArray();
		input = "";
		for(int i = 0; i < chars.length; i++){
			if(key.length() < keylen && i % (bits+1) == 0) {
				key += chars[i];
			} else {
				input += chars[i];
			}
		}
		chars = input.toCharArray();
		chars = apply(chars, key);
		return new String(chars);
	}

}
