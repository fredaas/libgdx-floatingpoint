package com.fredaas.handlers;

import static com.fredaas.handlers.Vars.PPM;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;

public class B2DObjectProcessor {
    
    private TiledMap tm;
    private World world;
    private BodyDef bdef;
    private FixtureDef fdef;
    
    public B2DObjectProcessor(TiledMap tm, World world) {
        this.tm = tm;
        this.world = world;
        init();
    }
    
    private void init() {
        bdef = new BodyDef();
        fdef = new FixtureDef();
    }
    
    public void loadObjects() {
        for (MapLayer layer : tm.getLayers()) {
            if (layer.getProperties().get("ignore") != null) {
                continue;
            }
            for (MapObject obj : layer.getObjects()) {
                if (obj instanceof RectangleMapObject) {
                    createBody(getRectangleShape((RectangleMapObject) obj), layer.getName());
                }
                else if (obj instanceof EllipseMapObject) {
                    createBody(getCircleShape((EllipseMapObject) obj), layer.getName());
                }
                else if (obj instanceof PolylineMapObject) {
                    createBody(getChainShape((PolylineMapObject) obj), layer.getName());
                }
                else if (obj instanceof PolygonMapObject) {
                    createBody(getPolygonShape((PolygonMapObject) obj), layer.getName());
                }
            }
        }
    }
    
    public void loadTileMap(String id) {
        TiledMapTileLayer layer = (TiledMapTileLayer) tm.getLayers().get(id);
        float tileSize = layer.getTileWidth();
        
        for (int row = 0; row < layer.getHeight(); row++) {
            for (int col = 0; col < layer.getWidth(); col++) {
                Cell cell = layer.getCell(col, row);
                
                if (cell == null || cell.getTile() == null) {
                    continue;
                }
                
                Vector2[] vb = new Vector2[4];
                
                vb[0] = new Vector2(
                        (col + 0.5f) * (tileSize / PPM) - tileSize / 2 / PPM, 
                        (row + 0.5f) * (tileSize / PPM) - tileSize / 2 / PPM);
                vb[1] = new Vector2(
                        (col + 0.5f) * (tileSize / PPM) - tileSize / 2 / PPM, 
                        (row + 0.5f) * (tileSize / PPM) + tileSize / 2 / PPM);
                vb[2] = new Vector2(
                        (col + 0.5f) * (tileSize / PPM) + tileSize / 2 / PPM, 
                        (row + 0.5f) * (tileSize / PPM) + tileSize / 2 / PPM);
                vb[3] = new Vector2(
                        (col + 0.5f) * (tileSize / PPM) + tileSize / 2 / PPM, 
                        (row + 0.5f) * (tileSize / PPM) - tileSize / 2 / PPM);
                
                ChainShape cs = new ChainShape();
                cs.createLoop(vb);
                createBody(cs, id);
            }
        }
    }
    
    private void setBodyAttributes(Shape shape) {
        bdef.type = BodyType.StaticBody;
        fdef.shape = shape;
    }
    
    private void createBody(Shape shape, String id) {
        setBodyAttributes(shape);
        world.createBody(bdef).createFixture(fdef).setUserData(id);
    }
    
    private PolygonShape getRectangleShape(RectangleMapObject obj) {
        PolygonShape ps = new PolygonShape();
        Rectangle rect = obj.getRectangle();
        rect.width /= 2;
        rect.height /= 2;
        Vector2 center = new Vector2(
                (rect.x + rect.width) / PPM,
                (rect.y + rect.height) / PPM);
        ps.setAsBox(rect.width / PPM, rect.height / PPM, center, 0);
        
        return ps;
    }
    
    private CircleShape getCircleShape(EllipseMapObject obj) {
        Ellipse circle = obj.getEllipse();
        CircleShape cs = new CircleShape();
        cs.setRadius(circle.width / (2 * PPM));
        cs.setPosition(new Vector2(circle.x / PPM, circle.y / PPM));
        
        return cs;
    }
    
    private PolygonShape getPolygonShape(PolygonMapObject obj) {
        PolygonShape ps = new PolygonShape();
        float[] vertices = obj.getPolygon().getTransformedVertices();
        float[] vb = new float[vertices.length];
        
        for (int i = 0; i < vb.length; i++) {
            vb[i] = vertices[i] / PPM;
        }
        
        ps.set(vb);
        
        return ps;
    }
    
    private ChainShape getChainShape(PolylineMapObject polyline) {
        float[] vertices = polyline.getPolyline().getTransformedVertices();
        Vector2[] vb = new Vector2[vertices.length / 2];
        
        for (int i = 0; i < vb.length; i++) {
            vb[i] = new Vector2(
                    vertices[i * 2] / PPM,
                    vertices[i * 2 + 1] / PPM);
        }
        
        ChainShape cs = new ChainShape();
        cs.createChain(vb);
        
        return cs;
    }
    
    public void setCategoryBits(Short bit) {
        fdef.filter.categoryBits = bit;
    }
    
    public void setMaskBits(Short bit) {
        fdef.filter.maskBits = bit;
    }
    
}
