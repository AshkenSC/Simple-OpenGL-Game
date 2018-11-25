package particles;

import java.util.List;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import entities.Camera;
import models.RawModel;
import renderEngine.Loader;
import toolbox.Maths;

public class ParticleRenderer {
	
	private static final float[] VERTICES = {-0.5f, 0.5f, -0.5f, -0.5f, 0.5f, 0.5f, 0.5f, -0.5f};
	
	private RawModel quad;
	private ParticleShader shader;
	
	protected ParticleRenderer(Loader loader, Matrix4f projectionMatrix){
		quad = loader.loadToVAO(VERTICES, 2);
		shader = new ParticleShader();
		shader.start();
		shader.loadProjectionMatrix(projectionMatrix);
		shader.stop();
		
	}
	
	protected void render(List<Particle> particles, Camera camera){
		Matrix4f viewMatirx = Maths.createViewMatrix(camera);
		prepare();
		for(Particle particle : particles) {
			
		}
	}

	//The code below is for the updateModelViewMatrix() method
	//modelMatrix.m00 = viewMatrix.m00;
	//modelMatrix.m01 = viewMatrix.m10;
	//modelMatrix.m02 = viewMatrix.m20;
	//modelMatrix.m10 = viewMatrix.m01;
	//modelMatrix.m11 = viewMatrix.m11;
	//modelMatrix.m12 = viewMatrix.m21;
	//modelMatrix.m20 = viewMatrix.m02;
	//modelMatrix.m21 = viewMatrix.m12;
	//modelMatrix.m22 = viewMatrix.m22;

	protected void cleanUp(){
		shader.cleanUp();
	}
	
	private void updateModelViewMatrix(Vector3f position, float rotation, float scale, Matrix4f viewMatrix) {
		Matrix4f modelMatrix = new Matrix4f();
		Matrix4f.translate(position, modelMatrix, modelMatrix);
	}
	
	private void prepare(){
		shader.start();
		GL30.glBindVertexArray(quad.getVaoID());
		GL20.glEnableVertexAttribArray(0);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthMask(false);
	}
	
	private void finishRendering(){

	}

}
