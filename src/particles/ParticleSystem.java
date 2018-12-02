package particles;

public class ParticleSystem {

	private float pps;
	private float speed;
	private float gravityComplient;
	private float lifeLength;
	
	public ParticleSystem(float pps, float speed, float gravityComplient, float lifeLength) {
		this.pps = pps;
		this.speed = speed;
		this.gravityComplient = gravityComplient;
		this.lifeLength = lifeLength;
	}
}
