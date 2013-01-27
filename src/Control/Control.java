/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 60102328
 * Name : 라예지
 * Date : 2011. 5. 16(시작)
 */

package Control;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import Game.Game;
import Map.Map;
import Unit.Unit;
import User.User;

public class Control {
	
	private Game game;
	private Map map;
	private boolean turn;
	private int remainTurn;
	private User user;
	private Unit selectUnit;
	BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
	
	public void init(Game game){
		this.game = game;
		this.map = game.getMap();
	}

	public void startGame() {
	
		turn = false;					// Turn : true -> player1
										//		: flase -> playse2
		
		String command = null;
		StartLogo();
		while(true){
			if(getRemainTurn() == 0){
				// 턴을 넘길 시 사용
				resetReaminTurn();
				turn = !turn;
			}
			if(turn){
				user = game.getUser(true);
				// Player1 정보 넘김
			}else{
				user = game.getUser(false);
				// Player2 정보 넘김
			}
			System.out.print(user.getUserName() + "님 명령을 입력하세요  > ");
			try {
				command = reader.readLine();
				// Command 입력
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(command.charAt(0)){
				case 'h':
				case 'H':
					StartLogo();
					// Help 명령어
					continue;
				case 'm':
				case 'M':
					map.print();
					// Map Print 명령어
					continue;
				case 't':
				case 'T':
					setRemainTurn(0);
					// Turn 넘김 명령어
					continue;
				case 'q':
				case 'Q':
					exitMessage();
					System.exit(0);
					// Quit 명령어
					break;
				case 's':
				case 'S':
					// Select 명령어
					break;
				
				default:
					System.out.println("존재 하지 않는 명령어입니다. 도움말은 (H) 입니다");
					continue;
					
				
			}
			
			selectUnit = selectMoveUnit(user);
			// Unit 선택 Console 입력
			moveUnit(selectUnit);
			// Unit Moving Method Call
			
			
				
			
		}
	}

	private void exitMessage() {
		
		System.out.println("게임을 종료합니다.");
	}

	private void StartLogo() {
		
		
		System.out.println("(H)elp : 도움말");
		System.out.println("(M)ap : 맵을 화면에 출력함");
		System.out.println("(T)urn : 턴을 넘김");
		System.out.println("(Q)uit : 게임 종료");
		System.out.println("(S)elect : 유닛 선택");
		
		
		
		
		
	}

	private void moveUnit(Unit selectUnit) {
		String moveDirection;
		
		try {
			do{
			System.out.println("유닛 " + selectUnit.getType().toUpperCase() + "이동방향  (남은거리 "+remainTurn+")  >  "
					+ "현재 위치 : " + selectUnit.getUnitPosition().getX()+", "+ selectUnit.getUnitPosition().getY());
			System.out.print("(N, NE, E, SE, S, SW, W, NW)");
			moveDirection = reader.readLine();
			if(checkMoveDirection(selectUnit.getUnitPosition(), moveDirection.toUpperCase(),selectUnit)){
				// Validate MoveDirection And Decrease Remain Turn
				remainTurn--;
				
			}
			}while(remainTurn > 0);
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}

	private boolean checkMoveDirection(Point point, String moveDirection, Unit selectUnit) {
		

		Point tempPoint = new Point();
		tempPoint = point.getLocation();
		// Copy Moving Point
		
		if(moveDirection.equals("N")){
			tempPoint.y = (int) (tempPoint.getY()-1);
			// Change North Point
			
		}else if(moveDirection.equals("NE")){
			tempPoint.y = (int) (tempPoint.getY()-1);
			tempPoint.x = (int) (point.getX()+1);
			// Change North East Point
			
		}else if(moveDirection.equals("E")){
			
			tempPoint.x = (int) (tempPoint.getX()+1);
			// Change East Point
			
		}else if(moveDirection.equals("SE")){
			tempPoint.y = (int) (tempPoint.getY()+1);
			tempPoint.x = (int) (tempPoint.getX()+1);
			// Change South East Point
		}else if(moveDirection.equals("S")){
			tempPoint.y = (int) (tempPoint.getY()+1);
			// Change South Point
		}else if(moveDirection.equals("SW")){
			tempPoint.y = (int) (tempPoint.getY()+1);
			tempPoint.x = (int) (tempPoint.getX()-1);
			// Change South West Point
		}else if(moveDirection.equals("W")){
			
			tempPoint.x = (int) (tempPoint.getX()-1);
			// Change West Point
		}else if(moveDirection.equals("NW")){
			tempPoint.y = (int) (tempPoint.getY()-1);
			tempPoint.x = (int) (tempPoint.getX()-1);
			// Change North West Point
			
		}else {
		
			System.out.println("잘못된 위치 정보 입니다.");
			// Print inValid MoveDirection
			return false;
		}
		
		if(tempPoint.getX() < 0 || tempPoint.getX()> 10 || tempPoint.getY() < 0 || tempPoint.getY()> 10){
			System.out.println("잘못된 위치 정보 입니다.");
			// print Invalid Point
			return false;
		}
		
		if(map.isObstacleExist(tempPoint)){
			System.out.println("장애물을 부숩니다.");
			selectUnit.attackObstacle(map,selectUnit,tempPoint);
			selectUnit.moveUnit(tempPoint);
			return true;
		}
			
		if(map.isEnemyExist(tempPoint,selectUnit, turn)){
			System.out.println("적을 공격합니다.");
			selectUnit.attackEnemy(game.getUnitList(),selectUnit, tempPoint);
			return true;
		}
		if(map.isUnitExist(tempPoint)){
			System.out.println(tempPoint.getX()+", " + tempPoint.getY() +"에 다른 유닛이 존재합니다.");
			return false;
		}
		selectUnit.moveUnit(tempPoint);
		// Move Unit Point
		return true;
	}

	private Unit selectMoveUnit(User user) {
	
		String wantedUnit = null;
		
		try {
			do{
				System.out.print(user.getUserName()+ "님 움직일 말을고르세요! : (A, B, C, D, E, K) >");
				wantedUnit = reader.readLine();
			
			}while(!checkSelectUnit(wantedUnit));		// Select Unit Validate
			
			
			
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
		
			return getUnit(wantedUnit,turn);
		
			
		
		
	}

	private Unit getUnit(String wantedUnit, boolean turn) {
		
		Unit tempUnit= null;
		
		if(turn)
			wantedUnit = wantedUnit.toUpperCase();
			// Change Player1 Unit Type
		else
			wantedUnit = wantedUnit.toLowerCase();
			// Change Player2 Unit Type
	
		
		for(Unit unit: game.getUnitList()){
			if(unit.getType().equals(wantedUnit)){
				tempUnit = unit;
				// Select Unit Type
				break;
			}
		}
		
		return tempUnit;

		
	}

	private boolean checkSelectUnit(String wantedUnit) {
		
		boolean canUsingUnit = false;
		boolean isNotExist = true;
		if(turn){
			// Player1 Unit Select Validate
			for(Unit unit: game.getUnitList()){
				if(unit.getType().equals(wantedUnit.toUpperCase())){
					isNotExist = false;
					if(!unit.isLive()){
						System.out.println(wantedUnit+"는 죽은  유닛입니다.");
						// Print Die Unit
						
					}
					else{
						canUsingUnit = true;
						// Change Boolean Can Using Unit
					}
				}
			}
			
		}else{
			// Player2 Unit Select Validate
			for(Unit unit: game.getUnitList()){
				if(unit.getType().equals(wantedUnit.toLowerCase())){
					isNotExist = false;
					if(!unit.isLive()){
						System.out.println(wantedUnit+"는 죽은  유닛입니다.");
						
					}
					else{
						canUsingUnit = true;
					}
				}
			}
			
		}
		if(isNotExist)
			System.out.println(wantedUnit+"는 존재하지 않는 유닛입니다.");
			// Not Existing Unit
		
		return canUsingUnit;
	}

	public void setRemainTurn(int remainTurn) {
		this.remainTurn = remainTurn;
	}

	public int getRemainTurn() {
		return remainTurn;
	}
	
	public void resetReaminTurn(){
		remainTurn = 3;
	}

}
