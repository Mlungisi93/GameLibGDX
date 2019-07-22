package players;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Player extends Sprite {

    private World world;
    private Body body;
    public Player(World world, String name, float x, float y)
    {
        super(new Texture(name));
        this.world = world;
        setPosition(x - getWidth() / 2f, y - getHeight() / 2f);
        createBody();

    }

    void createBody(){

        BodyDef bodyDef = new BodyDef();
        //a static body is not affected by gravity or by any forces it will not move
        //a kinemic bdy is not affected by gravity but it is affected by other forces
        //but if we push it with a linear force or angular forces it will move
        //Dynamic body is affect by gravity and other forces
        //the player is going fall down
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        //position our body if the position of of our player
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        //create the player
        body = world.createBody(bodyDef);

        //Shape will enable our body to collide with other object
        PolygonShape shape  = new PolygonShape();
        shape.setAsBox((getWidth() / 2)/GameInfo.PPM , (getHeight() / 2) / GameInfo.PPM);
        //to assign this shape to our player we need to create a FixtureDef
        // it is going to define the shape and mass(gravity) and fraction e.t.c
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // our mass

        //we need to create fixture as we created body for out BodyDef
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player"); //must be distinct
        shape.dispose();


    }

    //we are going to update the player 's position accoding to his body
    public void updatePlayer()
    {

        this.setPosition(body.getPosition().x * GameInfo.PPM, body.getPosition().y * GameInfo.PPM);
        //this is our sprite will follow our body

    }

    public Body getBody()
    {
        return this.body; //the forces are going to act on the body
    }

}//player


















