/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 60102328
 * Name : ����
 * Date : 2011. 5. 16(����)
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
				// ���� �ѱ� �� ���
				resetReaminTurn();
				turn = !turn;
			}
			if(turn){
				user = game.getUser(true);
				// Player1 ���� �ѱ�
			}else{
				user = game.getUser(false);
				// Player2 ���� �ѱ�
			}
			System.out.print(user.getUserName() + "�� ����� �Է��ϼ���  > ");
			try {
				command = reader.readLine();
				// Command �Է�
			} catch (IOException e) {
				e.printStackTrace();
			}
			switch(command.charAt(0)){
				case 'h':
				case 'H':
					StartLogo();
					// Help ��ɾ�
					continue;
				case 'm':
				case 'M':
					map.print();
					// Map Print ��ɾ�
					continue;
				case 't':
				case 'T':
					setRemainTurn(0);
					// Turn �ѱ� ��ɾ�
					continue;
				case 'q':
				case 'Q':
					exitMessage();
					System.exit(0);
					// Quit ��ɾ�
					break;
				case 's':
				case 'S':
					// Select ��ɾ�
					break;
				
				default:
					System.out.println("���� ���� �ʴ� ��ɾ��Դϴ�. ������ (H) �Դϴ�");
					continue;
					
				
			}
			
			selectUnit = selectMoveUnit(user);
			// Unit ���� Console �Է�
			moveUnit(selectUnit);
			// Unit Moving Method Call
			
			
				
			
		}
	}

	private void exitMessage() {
		
		System.out.println("������ �����մϴ�.");
	}

	private void StartLogo() {
		
		
		System.out.println("(H)elp : ����");
		System.out.println("(M)ap : ���� ȭ�鿡 �����");
		System.out.println("(T)urn : ���� �ѱ�");
		System.out.println("(Q)uit : ���� ����");
		System.out.println("(S)elect : ���� ����");
		
		
		
		
		
	}

	private void moveUnit(Unit selectUnit) {
		String moveDirection;
		
		try {
			do{
			System.out.println("���� " + selectUnit.getType().toUpperCase() + "�̵�����  (�����Ÿ� "+remainTurn+")  >  "
					+ "���� ��ġ : " + selectUnit.getUnitPosition().getX()+", "+ selectUnit.getUnitPosition().getY());
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
		
			System.out.println("�߸��� ��ġ ���� �Դϴ�.");
			// Print inValid MoveDirection
			return false;
		}
		
		if(tempPoint.getX() < 0 || tempPoint.getX()> 10 || tempPoint.getY() < 0 || tempPoint.getY()> 10){
			System.out.println("�߸��� ��ġ ���� �Դϴ�.");
			// print Invalid Point
			return false;
		}
		
		if(map.isObstacleExist(tempPoint)){
			System.out.println("��ֹ��� �μ��ϴ�.");
			selectUnit.attackObstacle(map,selectUnit,tempPoint);
			selectUnit.moveUnit(tempPoint);
			return true;
		}
			
		if(map.isEnemyExist(tempPoint,selectUnit, turn)){
			System.out.println("���� �����մϴ�.");
			selectUnit.attackEnemy(game.getUnitList(),selectUnit, tempPoint);
			return true;
		}
		if(map.isUnitExist(tempPoint)){
			System.out.println(tempPoint.getX()+", " + tempPoint.getY() +"�� �ٸ� ������ �����մϴ�.");
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
				System.out.print(user.getUserName()+ "�� ������ ����������! : (A, B, C, D, E, K) >");
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
						System.out.println(wantedUnit+"�� ����  �����Դϴ�.");
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
						System.out.println(wantedUnit+"�� ����  �����Դϴ�.");
						
					}
					else{
						canUsingUnit = true;
					}
				}
			}
			
		}
		if(isNotExist)
			System.out.println(wantedUnit+"�� �������� �ʴ� �����Դϴ�.");
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
