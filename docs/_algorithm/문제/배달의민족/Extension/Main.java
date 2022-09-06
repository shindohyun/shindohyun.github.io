import java.util.Scanner;

/*TEST DATA
6
my.song.mp3 11b
greatSong.flac 1000b
not3.txt 5b
video.mp4 200b
game.exe 100b
mov!e.mkv 10000b
 */
public class Main {
	static final String MUSIC[] = {"mp3", "aac", "flac"};
	static final String IMAGE[] = {"jpg", "bmp", "gif"};
	static final String MOVIE[] = {"mp4", "avi", "mkv"};
	static final String EXTS[][] = {MUSIC, IMAGE, MOVIE};
	
	public static void main(String[] args) {
		int N;
		String S = "";
		Scanner sc = new Scanner(System.in);
		N = sc.nextInt();
		sc.nextLine();
		for(int i = 0; i < N; i++) {
			String temp = sc.nextLine();
			S += temp;
			S += "\n";
		}
		sc.close();
		
		System.out.println(solution(S));
	}

	private static String solution(String S) {
		String result = "";
		String fileInfos[] = S.split("\n");

		int totalSizes[] = new int[4]; //index 0: music, 1: image, 2: movie, 3: other
		
		for(int i = 0; i < fileInfos.length; i++) {
			String toks[] = fileInfos[i].split(" ");
			String fileName = toks[0];
			String fileSize = toks[1];
			String nameToks[] = fileName.split("\\.");
			String sizeToks[] = fileSize.split("b");
			String ext = nameToks[nameToks.length - 1];
			int size = Integer.parseInt(sizeToks[0]);
			
			boolean find = false;
			for(int j = 0; j < EXTS.length; j++) {
				find = false;
				for(int z = 0; z < EXTS[j].length; z++) {
					if(ext.equals(EXTS[j][z])) {
						totalSizes[j] += size;
						find = true;
						break;
					}
				}
				if(find) {
					break;
				}
			}
			
			if(find == false) {
				totalSizes[3] += size; //other	
			}
		}
		
		result = result + "music " + Integer.toString(totalSizes[0]) + "b\n";
		result = result + "images " + Integer.toString(totalSizes[1]) + "b\n";
		result = result + "movies " + Integer.toString(totalSizes[2]) + "b\n";
		result = result + "other " + Integer.toString(totalSizes[3]) + "b";
		
		return result;
	}
}
