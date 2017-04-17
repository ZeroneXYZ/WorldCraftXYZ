package xyz.wcraft.worldcraftxyz;

import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Disposable;

public class Grid implements Disposable{

	private final int grid_size = 20;
	private final float field_size = 5f;
	private Block field[][][];
	
	public Grid(){
		field = new Block[grid_size][grid_size][grid_size];
		for(int i = 0; i < grid_size; i++){
			for(int k = 0; k < grid_size; k++){
				field[i][0][k] = new StoneBlock();
			}
		}
		updatePosition();
	}
	
	public void updatePosition(){
		for(int i = 0; i < grid_size; i++){
			for(int j = 0; j < grid_size; j++){
				for(int k = 0; k < grid_size; k++){
					float x = i * field_size;
					float y = j * field_size;
					float z = k * field_size;
					
					if(field[i][j][k] != null){
						field[i][j][k].setPosition(x, y, z);
					}
						
				}
			}
		}
	}
	
	public void renderGrid(ModelBatch batch, Environment environment){
		for(int i = 0; i < grid_size; i++){
			for(int j = 0; j < grid_size; j++){
				for(int k = 0; k < grid_size; k++){
					if(field[i][j][k] != null){
						batch.render(field[i][j][k].getInstance(), environment);
					}
				}
			}
		}
	}
	
	public void editBoxByRayCast(Vector3 start_point, Vector3 direction, Block.Type type){
		int last_position_x = 0;
		int last_position_y = 0;
		int last_position_z = 0;
		
		for(int i = 0; i < grid_size * 2; i++){
			Vector3 tmp_start = new Vector3(start_point);
			Vector3 tmp_direction = new Vector3(direction);
			tmp_direction.nor();
			tmp_direction.scl(i);
			Vector3 line = tmp_start.add(tmp_direction);
			line.scl(1 / field_size);
			int x = Math.round(line.x);
			int y = Math.round(line.y);
			int z = Math.round(line.z);
			
			if(x > (grid_size - 1) || y > (grid_size - 1) || z > (grid_size - 1) || x < 0 || y < 0 || z < 0){
				break;
			}
			
			if(field[x][y][z] != null){
				if(type == null){
					if(field[x][y][z] != null){
						field[x][y][z].dispose();
						field[x][y][z] = null;
						updatePosition();
					}
				} else if(type == Block.Type.DirtBlock){
					field[last_position_x][last_position_y][last_position_z] = new DirtBlock();
					updatePosition();
				}
				break;
			}
			
			last_position_x = x;
			last_position_y = y;
			last_position_z = z;
		}
	}
	
	@Override
	public void dispose() {
		for(int i = 0; i < grid_size; i++){
			for(int j = 0; j < grid_size; j++){
				for(int k = 0; k < grid_size; k++){
					if(field[i][j][k] != null){
						field[i][j][k].dispose();
					}
				}
			}
		}
	}

}
