package scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.GameMain;

import clouds.Cloud;
import helpers.GameInfo;
import players.Player;

public class MainMenu implements Screen, ContactListener {
    private GameMain game;
    private Texture bg;
    //private Sprite player;
    private Player player;
   private World world;

   private OrthographicCamera box2DCamera;
   private Box2DDebugRenderer debugRenderer;

    public MainMenu(GameMain game) {
        this.game = game;

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);
        box2DCamera.position.set(GameInfo.WIDTH /2f, GameInfo.HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();
        world = new World(new Vector2(0, -9.8f), true);

        //set listeber to for ollision and separation
        world.setContactListener(this);
//new Vector2(0, -9.8f) gravity should not affect our x but y
        //true do sleep and we are saying to libGDX we are going to allow bodies in this world to sleep
        //why? because if nothing is happening to them they need to sleep for effeciency, we don't calculate
        //their position and in that way our game will run faster


        bg = new Texture("Game BG.png");
        //player = new Sprite(new Texture("Player 1.png"));
        //player.setPosition(GameInfo.WIDTH / 2 - player.getWidth() / 2,
                //GameInfo.HEIGHT / 2 - player.getHeight() / 2);

        player = new Player(world,"Player 1.png", GameInfo.WIDTH /2,
                GameInfo.HEIGHT / 2 + 250);

        //LibGDX engine uses 1 X 1 ration meaning 1 pexel  = 1 meter
        //80px width = 80meters tall

        Cloud c = new Cloud(world);
    }

    void update(float dt)
    {
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            //The forces are applied to the body
            // -1 move to the left on the x axis and 0 y
            // apply the force at the center not on the side so not to spin the player
            //true wake will apply the bodies to sleep if not force is applied to them
            //Not colliding with any other objects
            //but on this case if the body is sleep please wake it up
             player.getBody().applyLinearImpulse(new Vector2(-0.1f,0),
                     player.getBody().getWorldCenter(), true);

            //gains sleep out time but it does not move because it is a low force
             /*player.getBody().applyForce(new Vector2(-0.1f,0),
                     player.getBody().getWorldCenter(), true);*/

        }else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
           /* player.getBody().applyForce(new Vector2(0.1f,0),
                    player.getBody().getWorldCenter(), true);  //gains sleep out time*
                    use 5f*/
            player.getBody().applyLinearImpulse(new Vector2(0.1f,0),
                    player.getBody().getWorldCenter(), true);
        }

    }
    @Override
    public void show() { //is the same the as create meaning is the first method to be executed
        // and you can initialise

    }

    @Override
    public void render(float delta) {
        update(delta);
 //this will set the players postion
        player.updatePlayer();
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin(); //call it everytime we want to draw anything
        game.getBatch().draw(bg, 0, 0);
        game.getBatch().draw(player, player.getX(), player.getY() - player.getHeight() / 2f -20);
        game.getBatch().end();

        debugRenderer.render(world, box2DCamera.combined);
        //we need to tell how to calculate physics per second and at the end we will calculate physics
        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
        //it has timeStep - we tell how many times the world calculate physics
        //1/60f the world will calculate physics 60 time in a second
        //Gdx.graphics.getDeltaTime() is the time between each frame to get to another frame

        //--they calculate how two bodies collide with each other
        //if two bodies collide they need to be specifies where to go
        //6, 2 is the precision and the highter they are will affect the performance
        // but the more precise and the more we need to calculate
        //its a good idea to put in 6, 2
        //velocity --they
        //positioniterations

        //it is not going to do anything without updatePlayersPoisition on the player class
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
   bg.dispose();
   player.getTexture().dispose();
    }

//From Contact Listener
    //Called when two and only two objects first contact it other or collide
    @Override
    public void beginContact(Contact contact) {
        //System.out.println("Collision");
        //there is no way in telling if fixture A is going to be a player all the time

        Fixture firstBody, secondBody;

        //Making sure that the name of the firstBody is player
        if(contact.getFixtureA().getUserData() == "Player")
        {
            //System.out.println("Fixture A is the Player");
            firstBody = contact.getFixtureA();
            secondBody = contact.getFixtureB();
        }else
        {
            //System.out.println("Fixture A is the Cloud");

            firstBody = contact.getFixtureB();
            secondBody = contact.getFixtureA();
        }

        System.out.println("The name of the first player is: "+firstBody.getUserData());
    }

    @Override
    public void endContact(Contact contact) {
//Called when objects end contact it other or separate
        System.out.println("Separation");
    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
} // main menu
