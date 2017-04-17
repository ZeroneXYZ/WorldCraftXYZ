package xyz.wcraft.worldcraftxyz;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class StoneBlock extends Block{
	public StoneBlock() {
		super(new Texture(Gdx.files.internal("texture/Stone.png")), Type.StoneBlock);
	}
}
