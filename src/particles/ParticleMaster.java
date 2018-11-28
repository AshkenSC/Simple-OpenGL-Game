package particles;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Matrix4f;

import renderEngine.Loader;

public class ParticleMaster {

	private static List<Particle> particles = new ArrayList<Particle> ();
	private static ParticleRenderer renderer;
	
	public static void init(Loader loader, Matrix4f projectionMatrix) {
		renderer = new ParticleRenderer(loader, projectionMatrix);
	}
}
