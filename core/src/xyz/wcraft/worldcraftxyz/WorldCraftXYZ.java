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
	
	//сцнк нагнпю йюлепш
	public final float field_of_view = 67;
	
	//онгхжхъ йюлепш(оепелеыемхе)
	//бнгке
	public final float camera_near = 1;
	//дюкейн
	public final float camera_far = 300;
	//яйнпнярэ
	public final float camera_velocity = 15;
	//яйнпнярэ бпюыемхъ
	public final float camera_degrees_per_pixel = 0.08f;
	//пюглеп оепейпеярхъ
	public final float crosshair_size = 25;
	
	//дбхцюрэ йюлепс лшьйни
	public Environment environment;
	
	//йюлепю
	public PerspectiveCamera camera;
	
	//сярюмнбйю рейярспш
	public SpriteBatch sprite_batch;
	
	//йнлокейр акнйнб
	public ModelBatch model_batch;
	
	//оепейпеярхе
	public Texture crosshair;
	
	//дбхцюрэ йюлепс лшьйни 2 (опх мюфюрни ймнойе лшьх)
	public FPSControl camera_controller;
	
	//кюмьютр
	public Grid grid;
	
	@Override
	public void create () {
		
		//дбхцюрэ йюлепс лшьйни
		environment = new Environment();
		
		//днаюбкъел нябеыемхе = янкмже (ъпйнярэ х ремэ)
		environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.5f, 0.5f, 0.5f, 1f));
		environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.5f));
		environment.add(new DirectionalLight().set(0.2f, 0.2f, 0.2f, 1f, 0.8f, 0.5f));
		
		//оепейпеярхе
		crosshair = new Texture(Gdx.files.internal("interface/Crosshair.png"));
		
		//кюмьютр
		grid = new Grid();
		
		//сярюмнбйю рейярспш
		sprite_batch = new SpriteBatch();
		
		//йнлокейр акнйнб
		model_batch = new ModelBatch();
		
		//йюлепю (накюярэ гпемхъ)
		camera = new PerspectiveCamera(field_of_view, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		//онгхжхъ йюлепш
		camera.position.set(0, 10f, 10f);
		camera.near = camera_near;
		camera.far = camera_far;
		
		//дбхцюрэ йюлепс лшьйни 2 (опх мюфюрни ймнойе лшьх)
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
		
		//дбхцюрэ йюлепс лшьйни 2 (опх мюфюрни ймнойе лшьх)
		Gdx.input.setInputProcessor(camera_controller);
		
		//нрйкчвюел йспянп WINDOWS
		Gdx.input.setCursorCatched(true);
	}

	@Override
	public void render () {
		
		//дбхцюрэ йюлепс лшьйни 2 (опх мюфюрни ймнойе лшьх)
		camera_controller.update();
		
		//жбер тнмю
		Gdx.gl.glClearColor(0.5f, 0.8f, 1f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		
		model_batch.begin(camera);
		//кюмьютр
		grid.renderGrid(model_batch, environment);
		model_batch.end();
		
		//пюглеп оепейпеярхъ
		float crosshair_x = (Gdx.graphics.getWidth() - crosshair_size) / 2;
		float crosshair_y = (Gdx.graphics.getHeight() - crosshair_size) / 2;
		
		//сярюмнбйю рейярспш
		sprite_batch.begin();
		//пюглеп оепейпеярхъ
		sprite_batch.draw(crosshair, crosshair_x, crosshair_y, crosshair_size, crosshair_size);
		sprite_batch.end();
	}
	
	@Override
	public void dispose () {
		grid.dispose();
		crosshair.dispose();
	}
}
