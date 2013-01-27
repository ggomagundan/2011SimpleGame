/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 60082361
 * Name : 엄기현
 * Date : 2011. 5. 16(시작)
 */

package Unit;

import java.awt.Point;
import java.util.ArrayList;

import Constants.Constants;
import Map.Map;


public class Unit {
	int unitHP;
	int unitPower;
	Point unitPosition;
	private String type;
	boolean liveCondition;
	
	
	public Unit(Point position, int count, boolean userType){
		setUnitPosition(position);
		this.unitHP = Integer.parseInt(Constants.unitHPList[count]);
		this.unitPower = Integer.parseInt(Constants.unitPowerList[count]); 
		if(userType)
			this.type =	 Constants.unitNameList[count];
		else{
			this.type =	 Constants.unitNameList[count].toLowerCase();
		
		}
		liveCondition = true;
		
		
	}

	
	


	public void setUnitHP(int hp){
		unitHP=hp;
	}
	
	public int getUnitHP(){
		return unitHP;
		
	}
	
	public void setUnitPower(int power){
		unitPower=power;
	}
	
	public int getUnitPower(){
		return unitPower;
	}
	
	public void setUnitPosition(Point position){
		
		unitPosition = position;
	}
	
	public Point getUnitPosition(){
		return unitPosition;
	}
	
	public boolean move(Point movePoint){
		
		if(checkCollision(movePoint)==true){
			setUnitPosition(movePoint);
		}else{
			System.out.println("움직일수 없는 위치입니다.");
		}
		return false;
		
		
	}
	
	

	public boolean checkCollision(Point checkPosition){
		if((checkPosition.getX() > 8 && checkPosition.getX() < 0) || (checkPosition.getY()>8 && checkPosition.getY()<0)) { // 움직일 수 없는  위치
			return false;
		}
		
		return true;
			
		//이동하려는 위치에 적군이 있을경우 fight(fight함수 정의)
	
	
	
	}


	public String getType() {
		
		return type;
	}





	public boolean isLive() {
		
		return liveCondition;
	}





	public void attackObstacle(Map map, Unit selectUnit, Point point) {
		
		map.attackObstacle(selectUnit,point);
	}





	public void attackEnemy(ArrayList<Unit> unitList, Unit selectUnit, Point point) {
		
		int power = selectUnit.getUnitPower();
		Unit enemy = null;
		for(Unit unit:unitList){
			if(unit.getUnitPosition().equals(point))
				enemy = unit;
				// Find Enemy Unit
		}
		
		enemy.setUnitHP(enemy.getUnitHP()-power);
		// Attack Enemy and Decrease Enemy HP
		
		if(!enemy.checkLive()) {
			if(enemy.getType().equals("K") || enemy.getType().equals("k")){
				System.out.println("적의 장수를 죽였습니다. 축하드립니다!!!");
				System.exit(0);
				// Print King is Die
			}
			unitList.remove(enemy);
			System.out.println("적이 죽었습니다.");
			// Print Enemy Die
		}else{
			
			System.out.println("적이 다쳐서 " + enemy.getUnitHP() + "의 체력을 가지게 되었습니다." );
			// Print Enemy HP
		}
		
	}





	private boolean checkLive() {
		
		if(unitHP <= 0){
			this.liveCondition = false;
			
			
		}
		return liveCondition;
		
	}







	public void moveUnit(Point point) {
		
		setUnitPosition(point);
		System.out.println(point.getX() + ", " + point.getY()+"로 이동했습니다.");
		// Moveing Unit And Print Moving Point
	}





	public boolean getLive() {
		
		return liveCondition;
	}
}

