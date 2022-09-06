import java.util.Scanner;

public class main {

	public static void main(String[] args) {
		int[][] lands = null;
		int[][] wells = null;
		int[] points = new int[4];
		String[] strLands = null;
		String[] strWells = null;
		String[] strPoints = null;

		Scanner scan = new Scanner(System.in);
		strLands = scan.nextLine().split(" ");
		strWells = scan.nextLine().split(" ");
		strPoints = scan.nextLine().split(" ");
		scan.close();
		
		lands = new int[strLands.length / 4][4];
		wells = new int[strWells.length / 4][4];
		
		int j = 0;
		for(int i = 0; i < strLands.length; i++) {
			if(j == 4) {
				j = 0;
			}
			lands[i/4][j++] = Integer.parseInt(strLands[i]);
		}
		j = 0;
		for(int i = 0; i < strWells.length; i++) {
			if(j == 4) {
				j = 0;
			}
			wells[i/4][j++] = Integer.parseInt(strWells[i]);
		}
		
		for(int i = 0; i < strPoints.length; i++) {
			points[i] = Integer.parseInt(strPoints[i]);
		}
		
		
		boolean isPossible = true;
		
		for(int r = 0; r < lands.length; r++) {
			if(
					(isIn(lands[r][0], lands[r][1], lands[r][2], lands[r][3], points[0], points[1], points[2], points[3])) ||
					(isIn(points[1], points[2], points[3], lands[r][0], lands[r][1], lands[r][2], lands[r][3], points[0]))){
				isPossible = false;
				break;
			}
		}
		
		if(!isPossible) {
			System.out.println("false");
			return;
		}
		
		isPossible = false;
		for(int r = 0; r < wells.length; r++) {
			if(
					(isIn(points[0], points[1], points[2], points[3], wells[r][0], wells[r][1], wells[r][2], wells[r][3])) || 
					(isIn(wells[r][0], wells[r][1], wells[r][2], wells[r][3], points[0], points[1], points[2], points[3]))) { //식수원 범주 내에 분양 받을 수 있다고 생각했을 때
				isPossible = true;
				break;
			}
		}
		
		if(!isPossible) {
			System.out.println("false");
		}
		else {
			System.out.println("true");
		}
		/*
		for(int r = 0; r < lands.length; r++) {
			for(int c = 0; c < 4; c++) {
				System.out.print(lands[r][c] + " ");	
			}
		}*/
	}
	
	//사각형 x1, y1, x2, y2 안에 사각형이 tx1, ty1, tx2, ty2 겹치는지 확인
	//(t)x1 < (t)x2, (t)y1 < (t)y2
	public static boolean isIn(int x1, int y1, int x2, int y2, int tx1, int ty1, int tx2, int ty2) {
		int temp;
		
		//순서 정리
		//x1 < x2
		//y1 < y2
		if(x1 > x2) {
			temp = x1;
			x1 = x2;
			x2 = temp;
		}
		
		if(y1 > y2) {
			temp = y1;
			y1 = y2;
			y2 =temp;
		}
		
		//비교
		//왼쪽 x가 범주 안에 있을 경우 또는 오른쪽 x가 범주 안에 있을 경우
		if((tx1 > x1 && tx2 < x2) || (tx2 > x1 && tx2 < x2)) {
			//위나 아래의 y가 범주에 속하면 겹친다.
			if((ty2 > y1 && ty2 < y2) || (ty1 > y1 && ty2 < y2)) {
				return true;
			}
		}
		
		return false;
	}
}
