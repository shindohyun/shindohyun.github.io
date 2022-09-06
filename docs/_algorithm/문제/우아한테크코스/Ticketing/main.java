import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.Scanner;

public class main {
		
	public static void main(String[] args) {
		
		int totalTicket = 0;
		int logCnt = 0;
		String[] logs = null;
		
		Scanner scan = new Scanner(System.in);
		totalTicket = scan.nextInt();
		logCnt = scan.nextInt();
		logs = new String[logCnt];
		
		scan.nextLine();
		for(int i = 0; i < logCnt; i++) {
			logs[i] = scan.nextLine();
		}
		
		scan.close();
		
		ticketingSimulation(totalTicket, logs);
	}
	
	private static void ticketingSimulation(int totalTicket, String[] logs) {
		ArrayList<String> succeededIdList = new ArrayList<String>();
		
		LogInfo[] logInfos = parseLog(logs);
		boolean isLogin = false;
		String loginId = "";
		Date loginTime = null;
		
		for(LogInfo logInfo : logInfos) {
			
			//로그인 상태인 경우 티켓 구매 상태 확인(1분 지났는지 확인)
			if(isLogin) {
				long currTime = logInfo.time.getTime();

				//로그인 상태로 1분이 지남(티켓 하나 소진, 구매자 리스트에 올린다, 로그인 상태 정보 초기화)
				if((currTime - loginTime.getTime()) >= 1000* 60) {
					totalTicket--;
					succeededIdList.add(loginId);
					
					loginId = "";
					loginTime = null;
					isLogin = false;
				}
			}
			
			switch(logInfo.log) {
				case 1: //request
					if(!isLogin) {
						loginId = logInfo.id;
						loginTime = logInfo.time;
						isLogin = true;
					}
					break;
				case 2: //leave
					if(isLogin) {
						loginId = "";
						loginTime = null;
						isLogin = false;
					}
					break;
			}
		}
		
		//마지막에 로그인 상태로 남았다면 1분 지나 티켓 구매한 것으로 판단
		if(isLogin) {
			totalTicket--;
			succeededIdList.add(loginId);
				
			loginId = "";
			loginTime = null;
			isLogin = false;
		}
		
		//오름차순 정렬
		succeededIdList.sort(Comparator.naturalOrder());
		
		for(String succeededId : succeededIdList) {
			System.out.println(succeededId);
		}
	}
	
	private static LogInfo[] parseLog(String[] logs) {
		LogInfo[] logInfos = new LogInfo[logs.length];
	
		String[] toks = null;
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
		
		try {
			for(int i = 0; i < logs.length; i++) {
				toks = logs[i].split(" ");
				date = sdf.parse(toks[2]);
				logInfos[i] = new LogInfo(toks[0].toString(), toks[1].equals("request")? 1 : 2, date);
			}	
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return logInfos;
	}
	
	static class LogInfo{
		protected String id;
		protected int log; //1 : request, 2 : leave
		protected Date time;
		
		public LogInfo(String id, int log, Date time) {
			this.id = id;
			this.log = log;
			this.time = time;
		}
	}
}
