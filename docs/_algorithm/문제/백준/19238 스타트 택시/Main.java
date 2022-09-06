import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

public class Main {
	static class Point{
		public int r, c;
		public Point(int r, int c) {
			this.r = r;
			this.c = c;
		}
	}
	static class Taxi{
		public Point pt;
		public int oil;
		public Taxi(Point pt, int oil){
			this.pt = pt;
			this.oil = oil;
		}
	}
	static class Passenger{
		public Point start, end;
		public Passenger(Point start, Point end) {
			this.start = start;
			this.end = end;
		}
	}
	static class NextPassengerInfo{
		public Passenger passenger;
		public int distance;
		public NextPassengerInfo(Passenger passenger, int distance) {
			this.passenger = passenger;
			this.distance = distance;
		}
	}
	static int N; // 2~20
	static int M; // 1~400
	static int O; // 초기 연료
	static int[][] map = new int[20][20];
	static Taxi taxi;
	static ArrayList<Passenger> passengers = new ArrayList<Main.Passenger>();
	static int[] dr = {-1,1,0,0};
	static int[] dc = {0,0,-1,1};
	static int res;
	
	public static void main(String[] args) throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer(br.readLine());
		N = Integer.parseInt(st.nextToken());
		M = Integer.parseInt(st.nextToken());
		O = Integer.parseInt(st.nextToken());
		for(int r = 0; r < N; r++) {
			st = new StringTokenizer(br.readLine());
			for(int c = 0; c < N; c++) {
				map[r][c] = Integer.parseInt(st.nextToken());
			}
		}
		st = new StringTokenizer(br.readLine());
		taxi = new Taxi(new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1), O);
		for(int i = 0; i < M; i++) {
			st = new StringTokenizer(br.readLine());
			Point start = new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
			Point end = new Point(Integer.parseInt(st.nextToken())-1, Integer.parseInt(st.nextToken())-1);
			passengers.add(new Passenger(start, end));
		}
		br.close();
		
		solution();
		System.out.println(res);
	}
	
	public static void solution() {
		while(!passengers.isEmpty()) {
			NextPassengerInfo nextPassengerInfo = bfsForNextPassenger();
			if(nextPassengerInfo.passenger == null) {
				res = -1;
				return;
			}
			int restOil = taxi.oil - nextPassengerInfo.distance;
			if(restOil < 0) {
				// 다음 손님을 픽업하러 갈 연료가 없는 경우
				res = -1;
				return;
			}
			
			// 택시 위치와 연료 갱신
			taxi.oil = restOil;
			taxi.pt.r = nextPassengerInfo.passenger.start.r;
			taxi.pt.c = nextPassengerInfo.passenger.start.c;
			
			int distance = bfsForMove(nextPassengerInfo.passenger.end);
			if(distance == -1) {
				res = -1;
				return;
			}
			restOil = taxi.oil - distance;
			if(restOil < 0) {
				// 도착지점까지 갈 연료가 없는 경우
				res = -1;
				return;
			}
			
			// 택시 위치와 연료 갱신
			taxi.oil = restOil + (distance*2); // 도착지점까지의 이동거리의 두 배 만큼 연료가 추가 된다.
			taxi.pt.r = nextPassengerInfo.passenger.end.r;
			taxi.pt.c = nextPassengerInfo.passenger.end.c;
			
			// 이동 완료된 손님 제거
			passengers.remove(nextPassengerInfo.passenger);
		}
		
		res = taxi.oil; // 모든 손님이 이동되었으면 남은 연료를 출력
	}	
	
	// 현재 택시 위치에서 가장 가까운 손님을 찾는다.
	public static NextPassengerInfo bfsForNextPassenger() {
		NextPassengerInfo nextPassengerInfo = new NextPassengerInfo(null, Integer.MAX_VALUE);
		
		// 택시 출발 지점에 손님이 있는 경우
		for(Passenger p : passengers) {
			if(p.start.r == taxi.pt.r && p.start.c == taxi.pt.c) {
				nextPassengerInfo.passenger = p;
				nextPassengerInfo.distance = 0;
				return nextPassengerInfo;
			}
		}
		
		int distance = 0;
		
		Queue<Point> q = new LinkedList<Main.Point>();
		boolean[][] visit = new boolean[N][N];
		
		q.add(new Point(taxi.pt.r, taxi.pt.c));
		visit[taxi.pt.r][taxi.pt.c] = true;
		
		while(!q.isEmpty()) {
			distance++;
			int length = q.size();
			
			for(int i = 0; i < length; i++) {
				Point curPt = q.poll();
				
				int nr, nc;
				for(int d = 0; d < 4; d++) {
					nr = curPt.r + dr[d];
					nc = curPt.c + dc[d];
					
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || map[nr][nc] == 1 || visit[nr][nc]) continue;

					for(Passenger p : passengers) {
						if(nr == p.start.r && nc == p.start.c) {
							if(nextPassengerInfo.distance > distance) {
								nextPassengerInfo.distance = distance;
								nextPassengerInfo.passenger = p;
							}			
							else if(nextPassengerInfo.distance == distance) {
								// 거리가 같다면 행 번호가 작은거
								if(nextPassengerInfo.passenger.start.r > p.start.r) {
									nextPassengerInfo.passenger = p;
								}
								else if(nextPassengerInfo.passenger.start.r == p.start.r) {
									// 행 번호도 같다면 열 번호가 작은거
									if(nextPassengerInfo.passenger.start.c > p.start.c) {
										nextPassengerInfo.passenger = p;
									}
								}
							}
						}
					}
					
					q.add(new Point(nr, nc));
					visit[nr][nc] = true;
				}
			}
		}
		
		return nextPassengerInfo;
	}
	
	// 현재 택시 위치에서 도착지점까지의 최단경로 거리를 반환한다.
	public static int bfsForMove(Point end) {
		int distance = 0;
		
		Queue<Point> q = new LinkedList<Main.Point>();
		boolean[][] visit = new boolean[N][N];
		
		q.add(new Point(taxi.pt.r, taxi.pt.c));
		visit[taxi.pt.r][taxi.pt.c] = true;
		
		while(!q.isEmpty()) {
			distance++;
			int length = q.size();
			
			for(int i = 0; i < length; i++) {
				Point curPt = q.poll();
				
				int nr, nc;
				for(int d = 0; d < 4; d++) {
					nr = curPt.r + dr[d];
					nc = curPt.c + dc[d];
					
					if(!(nr >= 0 && nr < N && nc >= 0 && nc < N) || map[nr][nc] == 1 || visit[nr][nc]) continue;

					if(nr == end.r && nc == end.c) {
						return distance;
					}
					q.add(new Point(nr, nc));
					visit[nr][nc] = true;
				}
			}
		}
		
		return -1; // 도착지점을 찾지 못한 경우
	}
}
