package com.cursan.anthony.game.sozo.tools;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import java.util.HashMap;

/**
 * Created by cursan_a on 02/05/15.
 */
public class ResourceManager {
    private static ResourceManager instance = new ResourceManager();
    private HashMap<String, Texture> textures = new HashMap<String, Texture>();
    private HashMap<String, Music> musics = new HashMap<String, Music>();
    private HashMap<String, Sound> sounds = new HashMap<String, Sound>();
    private HashMap<String, TextureAtlas> textureAtlass = new HashMap<String, TextureAtlas>();

    private ResourceManager() {

    }
    public static ResourceManager getInstance() {
        return ResourceManager.instance;
    }

    public void loadTexture(String key, String path) {
        textures.put(key, new Texture(Gdx.files.internal(path)));
    }
    public void loadMusic(String key, String path) {
        musics.put(key, Gdx.audio.newMusic(Gdx.files.internal(path)));
    }
    public void loadSound(String key, String path) {
        sounds.put(key, Gdx.audio.newSound(Gdx.files.internal(path)));
    }
    public void loadTextureAtlas(String key, String path) {
        textureAtlass.put(key, new TextureAtlas(Gdx.files.internal(path)));
    }

    public Texture getTexture(String path) {
        return textures.get(path);
    }
    public Music getMusic(String path) {
        return musics.get(path);
    }
    public Sound getSound(String path) {
        return sounds.get(path);
    }
    public TextureAtlas getTextureAtlas(String path) {
        return textureAtlass.get(path);
    }

    public void removeTexture(String path) {
        textures.remove(path);
    }
    public void removeMusic(String path) {
        musics.remove(path);
    }
    public void removeSound(String path) {
        sounds.remove(path);
    }
    public void removeTextureAtlas(String path) {
        textureAtlass.remove(path);
    }

    public void removeAll() {
        textures.clear();
        musics.clear();
        sounds.clear();
        textureAtlass.clear();
    }
}
