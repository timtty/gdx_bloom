package core;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by Tim on 12/25/2014.
 */
public class Particle {
    Vector3 position;
    Model model;
    ModelInstance instance;
    ModelInstance[] layers;
    float bloom_intensity = 1f;
    int bloom_max = 25;
    boolean bloom_shine = true;

    public Particle(Vector3 position, float size) {
        this.position = position;

        ModelBuilder builder = new ModelBuilder();

        this.model = builder.createSphere(size, size, size, 18, 18, 4,
                new Material(ColorAttribute.createDiffuse(Color.CYAN)),
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);

        this.instance = new ModelInstance(this.model);
        this.instance.transform.setToTranslation(position);

        this.layers = new ModelInstance[80];

        for (int i = 0; i < this.layers.length; i++) {
            this.layers[i] = new ModelInstance(this.instance);
            float attribute = (i * 0.01f * bloom_intensity);
            float scale = (i * 0.05f);
            this.layers[i].materials.get(0).set(
                    new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, attribute));
            this.layers[i].transform.setToScaling(scale, scale, scale);
            this.layers[i].calculateTransforms();
        }
    }

    public Particle(Vector3 position) {
        this(position, 6f);
    }

    public void update() {
        if (bloom_shine) {
            bloom_intensity += 0.13f;
            if (bloom_intensity > bloom_max) {
                bloom_intensity = bloom_max;
                bloom_shine = false;
            }
        } else {
            bloom_intensity -= 0.13f;
            if (bloom_intensity < 3) {
                bloom_intensity = 3;
                bloom_shine = true;
            }
        }

        for (int i = 0; i < this.layers.length; i++) {
            float attribute = (i * 0.0001f * bloom_intensity);
            this.layers[i].materials.get(0).set(
                    new BlendingAttribute(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, attribute));
        }
    }

    public void bloom(ModelBatch renderer, Environment environment) {
        for (ModelInstance layer: layers) {
            renderer.render(layer, environment);
        }
    }

    public Vector3 getPosition() {
        return position;
    }

    public void setPosition(Vector3 newPosition) {
        this.position = newPosition;
        for (ModelInstance layer: layers) {
            layer.nodes.get(0).translation.set(position);
            layer.calculateTransforms();
        }
    }

    public Model getModel() {
        return model;
    }

    public ModelInstance getInstance() {
        return instance;
    }
}
