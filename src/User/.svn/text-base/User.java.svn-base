/*
 * OOP2(Java) Programming-B Project (0903)
 * Student Number : 60102356
 * Name : 염철언
 * Date : 2011. 5. 16(시작)
 */

package User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User {
	protected String userName;
	
	public void setUserName(String name){
		this.userName = name;
	}
	
	public String getUserName(){
		return userName;
	}

	public void inputUserName(String player) {
	
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.print(player + "의 이름을 입력하세요!!  : ");
		try {
			String name = reader.readLine();
			setUserName(name);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		
	}
}
