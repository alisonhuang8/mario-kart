package engine.sprite;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import commons.MathUtils;
import engine.camera.GamePoint;
import engine.player.Player;
import engine.sprite.ai.AI;
import engine.sprite.ai.Callback;
import engine.sprite.attacker.Attacker;
import engine.sprite.collidable.Collidable;
import engine.sprite.healthholder.HealthHolder;
import engine.sprite.images.ImageSet;
import engine.sprite.images.LtubImage;
import engine.sprite.movable.Movable;
import engine.sprite.nodeholder.NodeHolder;
import engine.sprite.spritespawner.SpriteSpawner;
import engine.sprite.teammember.TeamMember;
import engine.sprite.weapon.Weapon;

public class Sprite  {

	// initialize empty image.
	private ImageSet imageSet;
	// private boolean locked = false; // TODO
	private GamePoint pos;
	private int z;


	private Callback onHitTarget;
	private SelectionBound selectionBound = SelectionBound.IMAGE;
	private List<GamePoint> selectionBoundVertices;
	// composition objects 
	private Movable movable;
	private Collidable collidable;
	private Attacker attacker;
	private Weapon weapon;
	private HealthHolder healthHolder;
	private SpriteSpawner spriteSpawner;
	private AI ai;
	private TeamMember team;
	private NodeHolder nodeHolder;


	/**
	 * The player that this sprite belongs to.
	 */
	private Player player = Player.DEFAULT;
	private ActionQueue actionQueue = new ActionQueue();

	/**
	 * sprite constructor - sets all composition elements (movable, collidable, spritefactory, health, weapon, images to default values)
	 */
	public Sprite() {
		initAttributes();
	}

	private void initAttributes(){
		movable = null;
		collidable = null;
		attacker = null;
		weapon = null;
		healthHolder = null;
		spriteSpawner = null;
		team = null;
		ai = null;
	}

	public void setImageSet(ImageSet imageSet) {
		this.imageSet = imageSet;		
	}

	/**
	 * Return an image corresponding to the sprite at the 
	 * current frame. Could change with direction and moving distance.
	 * @return
	 */
	public LtubImage getImage() {
		// TODO: pass in angle and dist
		return imageSet.getImage(0, 0);
	}

	public GamePoint getPos() {
		return pos;
	}

	public void setPos(GamePoint pos) {
		this.pos = pos;
	}

	public void setSelectionBound(SelectionBound selectionBound) {
		this.selectionBound = selectionBound;
	}

	public SelectionBound getSelectionBound() {
		return selectionBound;
	}

	private void setSelectionBoundVertices() {
		selectionBoundVertices = new ArrayList<>();
		if (selectionBound == SelectionBound.IMAGE) {
			//			if (ltubImage != null) {
			//				// Image rectangle nodes are added in a clock-wise order
			//				selectionBoundVertices.add(this.getDisplayPos());
			//				selectionBoundVertices.add(new Point(this.getDisplayPos().x() + ltubImage.width(), this.getDisplayPos().y()));
			//				selectionBoundVertices.add(new Point(this.getDisplayPos().x() + ltubImage.width(), this.getDisplayPos().y() + ltubImage.height()));
			//				selectionBoundVertices.add(new Point(this.getDisplayPos().x(), this.getDisplayPos().y() + ltubImage.height()));
			//			}
		}
		else if (selectionBound == SelectionBound.POLYGON) {
			// TODO
		}
	}

	/**
	 * Get a list of Point indicating the definite display positions of selection bound vertices
	 * @return List<Point>
	 */
	public List<GamePoint> getSelectionBoundVertices() {
		setSelectionBoundVertices();
		return selectionBoundVertices;
	}

	public void setDetectionRange(double detectionRange) {
		attacker.setRange(detectionRange);
	}

	public double getDetectionRange() {

		return attacker.getRange();
		
	}


//    public Movable getMovable(){
//		return this.movable;
//	}

	public Optional<Movable> getMovable() {
		return Optional.ofNullable(movable);
	}

	public Optional<Collidable> getCollidable() {
		return Optional.ofNullable(collidable);
	}

	public Optional<Attacker> getAttacker() {
		return Optional.ofNullable(attacker);
	}

	public Optional<Weapon> getWeapon() {
		return Optional.ofNullable(weapon);
	}

	public Optional<HealthHolder> getHealthHolder(){
		return Optional.ofNullable(healthHolder);
	}

	public Optional<SpriteSpawner> getSpawner(){
		return Optional.ofNullable(spriteSpawner);
	}

	public Optional<TeamMember> getTeamMember(){
		return Optional.ofNullable(team);
	}
	
	public Optional<AI> getAI(){
		return Optional.ofNullable(ai);
	}

	
	public Optional<Attribute> getNodeHolder(){
		//System.out.println("it iz a thing " + this.nodeHolder.getClass().getName() );
		return Optional.ofNullable(nodeHolder);
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Player getPlayer() {
		return player;
	}
	public void executeAction(Action action) {
		actionQueue.clearQueue();
		action.execute();
	}
	public void queueAction(Action action) {
		actionQueue.addAction(action);
	}
	
	public void setHitsTarget(Callback callback) {
		onHitTarget = callback;
	}
	
	public void update(double dt) {
		updatePos(dt);
		if (attacker != null){
			attacker.update(dt);
		}
	}
	
	private void updatePos(double dt) {
		double tRemain = dt;
		
		while (movable != null && !MathUtils.doubleEquals(tRemain, 0)){
			if (ai != null && ai.getFinalDest() != null && pos.approxEquals(ai.getFinalDest())) {
				if (onHitTarget != null) {
					onHitTarget.execute();
				}
				break;
			}
			if (ai != null && pos.approxEquals(ai.getCurrentDest())){
				ai.updateCurrentDest();
			}
			movable.setDest(ai.getCurrentDest());
			tRemain = movable.update(dt, pos);
			this.pos = movable.getCurrPos();
		}

	}


	public void setSpawner(SpriteSpawner spawner) {
		this.spriteSpawner = spawner; 		
	}

	public void setMovable(Movable movable){
		this.movable = movable; 
	}

	public void setCollidable(Collidable collidable){
		this.collidable = collidable;
	}
	
	public void setAttacker(Attacker attacker){
		this.attacker = attacker;
	}
	
	public void setWeapon(Weapon weapon){
		this.weapon = weapon;
	}
	
	public void setTeamMember(TeamMember team){
		this.team = team;
	}	
	
	public void setAI(AI ai){
		this.ai = ai;
	}
	
	public void setHealthHolder(HealthHolder healthholder){
		this.healthHolder = healthholder;
	}
	
	public void setNodeHolder(NodeHolder nodeHolder){
		this.nodeHolder = nodeHolder;
		System.out.println("TESTTTT: " + this.nodeHolder.getClass().getName());
	}
}
