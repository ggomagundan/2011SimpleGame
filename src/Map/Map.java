/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 60102337
 * Name : 박종인
 * Date : 2011. 5. 16(시작)
 */

package Map;

import java.awt.Point;
import java.util.ArrayList;

import Game.Game;
import Unit.Unit;


public class Map {

	
	
	
	
	private Game game;
	 
	
	ArrayList<Point> obstacleList = new ArrayList<Point>();
	
	
	public void setingObstacle(){
		int obsNum;								// 장애물의 갯수
		int obsPointX, obsPointY;			// 장애물의 x,y 좌표
		Point obsPosition;
		obsNum = (int) (Math.random() * 5) + 3; 	// 4 ~ 7개의 장애물
		
		for(int i=0 ;i < obsNum; i++){
			obsPointX = (int) (Math.random() * 9);	
			obsPointY = (int) (Math.random() * 9);	// 장애물의 x,y 좌표를 랜덤 지정 
			while(obstacleList.contains(new Point(obsPointX, obsPointY))){ // 좌표가 겹치면
				obsPointX = (int) (Math.random() * 9);	
				obsPointY = (int) (Math.random() * 9);	//다시 지정
			}
			
			obsPosition = new Point(obsPointX, obsPointY);
		
			
			obstacleList.add(obsPosition);		// Array 에 장애물 위치 저장
			//obstacleNumber = (obstacleList.indexOf(new Point(obsPosition)) + 1);
		}	
	}
	


	public void attackObstacle(Unit unit, Point point){				//장애물이 파괴되면 파괴한 유닛에
																	//장애물 번호에 따른 효과 부여
		switch((int)(Math.random()*7)){					
			case 0:
				System.out.println(unit.getType()+"의 힘이 10 증가했습니다.");
				unit.setUnitPower(unit.getUnitPower()+10);
				break;
		
			case 1:
				System.out.println(unit.getType()+"이 장애물을 부셨습니다.");
				break;
		
			case 2:
				System.out.println(unit.getType()+"이.. 으아악!! 돌을 맞아서 힘이 5 줄었습니다.");
				unit.setUnitPower(unit.getUnitPower()-5);
				break;
		
			case 3:
				System.out.println(unit.getType()+"의 체력이 10 증가했습니다.");
				unit.setUnitHP(unit.getUnitHP()+10);
				break;
		
			case 4:
				System.out.println(unit.getType()+"의 체력이 15 증가했습니다.");
				unit.setUnitHP(unit.getUnitHP()+15);
				break;
		
			case 5:
				System.out.println(unit.getType()+"이.. 으아악!! 돌을 맞아서 체력이 10 줄었습니다.");
				unit.setUnitHP(unit.getUnitHP()-10);
				break;
		
			case 6:
				System.out.println(unit.getType()+"이.. 으아악!! 돌을 맞았는데 힘이 15 증가했습니다.");
				unit.setUnitPower(unit.getUnitPower()+15);
				break;
		}
			obstacleList.remove(point);
			
		
	}

	public void init(Game game) {
	
		this.game = game;
		setingObstacle();
		for(Point po :obstacleList){
			System.out.println("장애물 위치 : " + (char)(po.getX()+65) + ", " + po.getY());
		}
	}

	public void print() {
		
		String printCompo;
		System.out.println("  | A | B | C | D | E | F | G | H | I | J |");
		System.out.println("-------------------------------------------");
		// Print Basic Line
		
		for(int j =0;j<=9;j++){
			System.out.print(j +" | ");
			for(int i =0;i<=9;i++){
				printCompo = " ";
				// get Null Area
				if(obstacleList.contains(new Point(i,j))){
					printCompo = "*";
					// get Obstacle
				}else if(game.getUnitList().size() != 0){ 
					
					for(Unit unit : game.getUnitList())
						if(unit.getUnitPosition().equals(new Point(i,j))) {
							printCompo = unit.getType();
							// get Unit Type
						}
				
					
				}
				System.out.print(printCompo +  " | ");
			}
			System.out.println();
		}
		
				
	
			
		
	}

	public boolean isUnitExist(Point point) {
		
		boolean isUnitExist = false;
		for(Unit unit: game.getUnitList())
			if(unit.getUnitPosition().equals(point) && unit.getLive()){
				// Check Friend Exist
				isUnitExist = true;
			}
				
				
		return isUnitExist;
	}

	public boolean isObstacleExist(Point point, Unit selectUnit) {
		
		if(obstacleList.contains(point))
			return true;
		else
			return false;
	}
	public boolean isObstacleExist(Point point) {
		
		if(obstacleList.contains(point))
			return true;
		else
			return false;
	}

	public boolean isEnemyExist(Point point, Unit selectUnit, boolean turn) {
		
		boolean isEnemyExist = false;
		if(turn){
			// Check Player2 Unit Exist
			for(int i = game.getFirstCount();i < game.getFirstCount()+game.getSecondCount();i++){
				if(game.getUnitList().get(i).getUnitPosition().equals(point)){
					isEnemyExist = true;
				}
			}
		}else{
			// Check Player1 Unit Exist
			for(int i = 0;i < game.getFirstCount();i++){
				if(game.getUnitList().get(i).getUnitPosition().equals(point)){
					isEnemyExist = true;
				}
			}
			
		}				
				
		return isEnemyExist;
	}
}
