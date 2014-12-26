package lifecycle;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;
import core.Particle;

import java.util.ArrayList;

/**
 * Created by Tim on 12/25/2014.
 */
public class Scene implements ApplicationListener {
    PerspectiveCamera cam;
    ModelBatch renderer;
    ModelBuilder builder;
    Environment environment;

    final Vector3 ORIGIN = new Vector3(0, 0, 0);

    ArrayList<Particle> particles;

    @Override
    public void create() {
        this.cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.cam.position.set(0, 0, 22f);
        this.cam.lookAt(ORIGIN);
        this.cam.near = 0.1f;
        this.cam.far = 300f;
        this.cam.update();

        this.renderer = new ModelBatch();

        this.environment = new Environment();
        this.environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.2f, 0.2f, 0.2f, 1f));
        this.environment.add(new DirectionalLight().set(12f, 12f, 12f, -0.1f, -0.1f, -3f));

        this.builder = new ModelBuilder();

        this.particles = new ArrayList<Particle>();
        this.particles.add(new Particle(ORIGIN, 0.6f));
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void render() {
        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        this.particles.get(0).update();

        this.renderer.begin(this.cam);
        this.renderer.render(this.particles.get(0).getInstance(), this.environment);
        this.particles.get(0).bloom(renderer, environment);
        this.renderer.end();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }
}
