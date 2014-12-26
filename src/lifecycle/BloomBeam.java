package lifecycle;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

/**
 * Created by Tim on 12/25/2014.
 */
public class BloomBeam {
    public static void Bloom() {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Bloom Beam Bitches!!";
        cfg.width = 800;
        cfg.height = 600;

        new LwjglApplication(new Scene());
    }
}
