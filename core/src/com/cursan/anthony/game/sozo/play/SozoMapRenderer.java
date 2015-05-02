package com.cursan.anthony.game.sozo.play;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.cursan.anthony.game.sozo.GameMaster;
import com.cursan.anthony.game.sozo.tools.ASpriteAnimated;

import java.util.ArrayList;

/**
 * Created by cursan_a on 30/04/15.
 */
public class SozoMapRenderer extends OrthogonalTiledMapRenderer {
    private ArrayList<ASpriteAnimated> sprites = new ArrayList<ASpriteAnimated>();

    public SozoMapRenderer(TiledMap map) {
        super(map, GameMaster.getInstance().getSpriteBatch());
    }

    public void render(float elapsedTime) {
        beginRender();
        for (MapLayer layer : map.getLayers()) {
            if (layer.getName().equals("sprites")) {
                for (ASpriteAnimated sprite : sprites) {
                    sprite.update(elapsedTime);
                    sprite.draw(this.getBatch());
                }
            }
            if (layer.isVisible()) {
                if (layer instanceof TiledMapTileLayer) {
                    renderTileLayer((TiledMapTileLayer)layer);
                }
            }
        }
        endRender();
    }

    public void addSprite(ASpriteAnimated sprite) {
        this.sprites.add(sprite);
    }
}
