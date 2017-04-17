package xyz.wcraft.worldcraftxyz;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;

public class WorldCraftXYZ extends ApplicationAdapter {
	
	//���� ������ ������
	public final float field_of_view = 67;
	
	//������� ������(�����������)
	//�����
	public final float camera_near = 1;
	//������
	public final float camera_far = 300;
	//��������
	public final float camera_velocity = 15;
	//�������� ��������
	public final float camera_degrees_per_pixel = 0.08f;
	//������ �����������
	public final float crosshair_size = 25;
	
	//������� ������ ������
	public Environment environment;
	
	//������
	public PerspectiveCamera camera;
	
	//��������� ��������
	public SpriteBatch sprite_batch;
	
	//�������� ������
	public ModelBatch model_batch;
	
	//�����������
	public Texture crosshair;
	
	//������� ������ ������ 2 (��� ������� ������ ����)
	public FPSControl camera_controller;
	
	//�������
	public Grid grid;
	
	@Override
	public void create () {
		
		//������� ������ ������
		environment = new Environment();
		
		//��������� ��������� = ������ (������� � ����)
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.5f));
		environment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, 1f, 0.8f, 0.5f));
		
		//�����������
		crosshair = new Texture(Gdx.files.internal("interface/Crosshair.png"));
		
		//�������
		grid = new Grid();
		
		//��������� ��������
		sprite_batch = new SpriteBatch();
		
		//�������� ������
		model_batch = new ModelBatch();
		
		//������ (������� ������)
		camera = new PerspectiveCamera(field_of_view, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//������� ������
		camera.position.set(0, 10f, 10f);
		camera.near = camera_near;
		camera.far = camera_far;
		
		//������� ������ ������ 2 (��� ������� ������ ����)
		camera_controller = new FPSControl(camera){
			@Override
			public boolean touchDown(int screenX, int screenY, int pointer, int button) {
				if(button == 0){
					grid.editBoxByRayCast(camera.position, camera.direction, Block.Type.DirtBlock);
				}else if(button == 1){
					grid.editBoxByRayCast(camera.position, camera.direction, null);
				}
				return super.touchDown(screenX, screenY, pointer, button);
			}
		};
		camera_controller.setDegreesPerPixel(camera_degrees_per_pixel);
		camera_controller.setVelocity(camera_velocity);
		
		//������� ������ ������ 2 (��� ������� ������ ����)
		Gdx.input.setInputProcessor(camera_controller);
		
		//��������� ������ WINDOWS
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		
		//������� ������ ������ 2 (��� ������� ������ ����)
		camera_controller.update();
		
		//���� ����
		Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		model_batch.begin(camera);
		//�������
		grid.renderGrid(model_batch, environment);
		model_batch.end();
		
		//������ �����������
		float crosshair_x = (Gdx.graphics.getWidth() - crosshair_size) / 2;
		float crosshair_y = (Gdx.graphics.getHeight() - crosshair_size) / 2;
		
		//��������� ��������
		sprite_batch.begin();
		//������ �����������
		sprite_batch.draw(crosshair, crosshair_x, crosshair_y, crosshair_size, crosshair_size);
		sprite_batch.end();
	}
	
	@Override
	public void dispose () {
		grid.dispose();
		crosshair.dispose();
	}
}
