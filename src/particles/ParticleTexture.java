package particles;

public class ParticleTexture {

	private int textureID;
	private int numberOfRows;
	private boolean additive;
	
	public ParticleTexture(int textureID, int numberOfRows) {
		super();
		this.textureID = textureID;
		this.numberOfRows = numberOfRows;
	}
	
	protected boolean useAdditiveBlending() {
		return additive;
	}

	public int getTextureID() {
		return textureID;
	}

	public int getNumberOfRows() {
		return numberOfRows;
	}
	
}
