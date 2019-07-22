package clouds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import helpers.GameInfo;

public class Cloud extends Sprite {

    private World world;
    private Body body;

    public Cloud(World world) {
        super(new Texture("Cloud 1.png"));
        this.world = world;
        setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT /2f - 130);
        createBody();

    }

    void createBody(){
        BodyDef bodyDef = new BodyDef();
        //a static body is not affected by gravity or by any forces it will not move
        //a kinemic bdy is not affected by gravity but it is affected by other forces
        //but if we push it with a linear force or angular forces it will move
        //Dynamic body is affect by gravity and other forces
        //the player is going fall down
        bodyDef.type = BodyDef.BodyType.StaticBody;

        //position our body if the position of of our player
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);

        //create the player
        body = world.createBody(bodyDef);

        //Shape will enable our body to collide with other object
        PolygonShape shape  = new PolygonShape();
        shape.setAsBox(getWidth() / GameInfo.PPM , (getHeight() / 2) / GameInfo.PPM);
        //to assign this shape to our player we need to create a FixtureDef
        // it is going to define the shape and mass(gravity) and fraction e.t.c
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1f; // our mass

        //we need to create fixture as we created body for out BodyDef
        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Cloud");

        //for to collide and pass
        //this is what we will do when we touch the collectable item like coin or life and ..
        fixture.setSensor(true);
        shape.dispose();
    }
}
