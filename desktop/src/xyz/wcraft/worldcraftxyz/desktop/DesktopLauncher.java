package xyz.wcraft.worldcraftxyz.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import xyz.wcraft.worldcraftxyz.WorldCraftXYZ;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1024;
		config.height = 786;
		new LwjglApplication(new WorldCraftXYZ(), config);
	}
}
