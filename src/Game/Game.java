/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 
 * Name : �ں���
 * Date : 2011. 5. 16(����)
 */

package Game;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import Constants.Constants;
import Control.Control;
import Map.Map;
import Unit.Unit;
import User.User;

public class Game {
	
	ArrayList<User> userList = new ArrayList<User>();
	ArrayList<Unit> unitList = new ArrayList<Unit>();
	int firstUnitCount = 6, secondUnitCount = 6;
	User user1 = new User();
	User user2 = new User();
	Map map = new Map();
	Unit unit;
	Control control = new Control();
	

	

	public void initGame() {
		
		boolean userType= true;
		
		addUser();
		createMap();
		createUnit(user1, userType);
		createUnit(user2, !userType);
		System.out.println();
		System.out.println();
		control.init(this);
		control.startGame();
		
	}

	private void createUnit(User user, boolean userType) {
		
		String inputPosition = null;
		Point unitPosition;
		boolean isDataValid;
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		try {
			for(String name : Constants.unitNameList){
								
				while(true){
						
					printUserPosition(user, name, userType);
					inputPosition  = reader.readLine();
					if(inputPosition.length() != 2){
						System.out.println("�߸� �Է��ϼ̽��ϴ�.");
						continue;
					}
					unitPosition = new Point(inputPosition.toUpperCase().charAt(0)-65, inputPosition.charAt(1)-48);
					
					isDataValid = checkValidData(unitPosition,userType,inputPosition);
					if(isDataValid)
						break;
				}
				
				addUnit(unitPosition,userType, name);
				
						
							
						
			}
			
		} catch (IOException e) {
		
			e.printStackTrace();
		}
		
	}
		
		
	

	private boolean checkValidData(Point unitPosition, boolean userType, String inputPosition) {
	
		boolean isDataValid = true;
		
		if(isUnitExist(unitPosition)){
			isDataValid = false;
			System.out.println("Unit�� �̹� ���� �մϴ�. �ٽ� �Է��ϼ���.");
			
		}
		if(isObstacleExist(unitPosition)){
			isDataValid = false;
			System.out.println("��ֹ��� �̹� ���� �մϴ�. �ٽ� �Է��ϼ���.");
			
		}
		if(isInvalidPosition(unitPosition,userType) || inputPosition.length()> 2){
			isDataValid = false;
			System.out.println("��ġ�� �̻��մϴ�. �ٽ� �Է��ϼ���.");
			
		}
		return isDataValid;
	}

	private void addUnit(Point unitPosition, boolean userType, String name) {
	
		switch(name.charAt(0)){
			case 'A':
				unit = new Unit(unitPosition,0,userType);
				unitList.add(unit);
				break;
			case 'B':
				unit = new Unit(unitPosition,1, userType);
				unitList.add(unit);
				break;
			case 'C':
				unit = new Unit(unitPosition,2, userType);
				unitList.add(unit);
				break;
			case 'D':
				unit = new Unit(unitPosition,3, userType);
				unitList.add(unit);
				break;
			case 'E':
				unit = new Unit(unitPosition,4, userType);
				unitList.add(unit);
				break;
			case 'K':
				unit = new Unit(unitPosition,5, userType);
				unitList.add(unit);
				break;
		}
	}

	private void printUserPosition(User user, String name, boolean userType) {
	
		if(userType)
			System.out.print(">> " + user.getUserName() + "���� '" + name + "' Unit�� ��ġ�� ? (A0 ~ J3) > ");
		else
			System.out.print(">> " + user.getUserName() + "���� '" + name + "' Unit�� ��ġ�� ? (A7 ~ J9) > ");
		
	}

	private boolean isInvalidPosition(Point unitPosition, boolean userType) {
	
		int type;
		
		if(userType)
			type = 0;
		else 
			type =1;
		
		switch(type){
		case 0:
			if(unitPosition.getX() < 0 || unitPosition.getX() > 9
					|| unitPosition.getY() < 0 || unitPosition.getY() > 3){
				return true;
			}else{
				return false;
			}
		case 1:
			if(unitPosition.getX() < 0 || unitPosition.getX() > 9
					|| unitPosition.getY() < 7 || unitPosition.getY() > 9){
				return true;
			}else{
				return false;
			}
		}
		return userType;
		
		
	}

	private boolean isUnitExist(Point point) {
	
		return map.isUnitExist(point);
		
	}
	
	private boolean isObstacleExist(Point point) {
		
		
		return map.isObstacleExist(point);
	}

	private void createMap() {
		
		map.init(this);
		map.print();
	}

	private void addUser() {
	
		user1.inputUserName("Player1");
		user2.inputUserName("Player2");
		
		userList.add(user1);
		userList.add(user2);
	}

	public ArrayList<Unit> getUnitList() {
	
		return this.unitList;
	}

	public User getUser(boolean userType) {
	
		if(userType)
			return user1;
		else
			return user2;
	}

	public Map getMap() {
	
		return map;
	}

	public int getSecondCount() {
	
		return secondUnitCount;
	}

	public int getFirstCount() {
	
		return firstUnitCount;
	}

}
