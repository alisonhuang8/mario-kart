package newengine.managers.levels;

import java.util.ArrayList;
import java.util.List;

import bus.EventBus;
import commons.point.GamePoint;
import data.SpriteMakerModel;
import newengine.events.SpriteModelEvent;
import newengine.player.Player;
import newengine.skill.skills.BuildSkill;
import newengine.sprite.Sprite;
import newengine.sprite.components.GameBus;
import newengine.sprite.components.Images;
import newengine.sprite.components.Owner;
import newengine.sprite.components.PathFollower;
import newengine.sprite.components.Position;
import newengine.sprite.components.SkillSet;
import newengine.utils.image.LtubImage;

//Not part of masterpiece, just part of refactoring. 
/**
 * Concrete implementation of PathCreator. Just uses an already created strategy for path creating
 */
public class SimplePathCreator implements PathCreator{
	public static final String STONE_FILE_PATH = "images/characters/Stone.jpg";
	private EventBus bus;
	
	/**
	 * Creates a path creator with a bus for event calling
	 * @param bus
	 */
	public SimplePathCreator(EventBus bus){
		this.bus = bus;
	}

	/**
	 * Draws path
	 */
	@Override
	public void createPath(List<SpriteMakerModel> spriteMakerModels) {
		List<Sprite> pathSprites = new ArrayList<>();
		for (int m = 0; m < spriteMakerModels.size(); m++) {
			SkillSet skillSet = (SkillSet) spriteMakerModels.get(m).getComponentByType(SkillSet.TYPE);
			BuildSkill buildSkill = (BuildSkill) skillSet.getSkill(BuildSkill.TYPE);
			PathFollower pathFollowerComponent = (PathFollower) buildSkill.getSpriteMakerModel().getComponentByType(PathFollower.TYPE);
			List<GamePoint> points = new ArrayList<> (pathFollowerComponent.getPath().getPath());
			
			for (int i = 0; i < points.size()-1; i++) {
				GamePoint point1 = points.get(i);
				GamePoint point2 = points.get(i+1);
				double dist = point1.distFrom(point2);
				double dx = point2.x() - point1.x();
				double dy = point2.y() - point1.y();
				double tileInterval = 30;
				for (int j = 0; j <= dist / tileInterval; j++) {
					GamePoint pathPoint = new GamePoint(
							point1.x() + tileInterval * dx / dist * j,
							point1.y() + tileInterval * dy / dist * j);
					Sprite step = new Sprite();
					step.addComponent(new Position(pathPoint));
					LtubImage ltubimage = new LtubImage(STONE_FILE_PATH);
					step.addComponent(new Images(ltubimage));
					step.addComponent(new GameBus());
					step.addComponent(new Owner(Player.NATURE));
					pathSprites.add(step);
				}
			}	
		}
		bus.emit(new SpriteModelEvent(SpriteModelEvent.ADD, pathSprites));
	}

}
