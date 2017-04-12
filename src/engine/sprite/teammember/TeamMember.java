package engine.sprite.teammember;

import data.AttributeData;
import engine.sprite.Attribute;

public class TeamMember implements Attribute {
	private int teamNum;

//	public TeamMember(AttributeData data){
//		this.teamNum = Integer.parseInt(data.getVariable("number"));
//	}
	
	public TeamMember(int number){
		this.teamNum = number;
	}
	
	public int getTeamNum(){
		return teamNum;
	}
	
	public void setTeamNum(int t){
		teamNum = t;
	}

}
