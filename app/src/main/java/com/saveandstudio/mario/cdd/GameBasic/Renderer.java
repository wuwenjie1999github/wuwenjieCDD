package com.saveandstudio.mario.cdd.GameBasic;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.ArrayList;

public class Renderer extends MonoBehavior implements Comparable<Renderer> {

    static public ArrayList<Renderer> renderersList = new ArrayList<>();

    private Bitmap bitmapResource;
    public int bitMapHeight, bitMapWidth;
    private int bitmapId;
    private float zDepth;
    private boolean set = false;

//    public Renderer(){
//        this(R.mipmap.default_sprite);
//    }

    public Renderer(int id) {
        bitmapId = id;
        renderersList.add(this);
    }

    public void setBitmapResource(int id) {
        bitmapId = id;
        set = true;
    }

    public Bitmap getBitmapResource() {
        return bitmapResource;
    }

    public void Draw(Canvas canvas, Paint paint) {

        Transform transform = (Transform) getComponent(Transform.class);
        if (transform != null) {
            if (bitmapResource == null) {
                if (Global.surfaceContext != null) {
                    bitmapResource = BitmapFactory.decodeResource(Global.surfaceContext.getResources(), bitmapId);
                    bitMapWidth = bitmapResource.getWidth();
                    bitMapHeight = bitmapResource.getHeight();
                }
            } else{
                canvas.drawBitmap(bitmapResource, transform.transformMatrix, null);
                if(set){
                    bitmapResource = BitmapFactory.decodeResource(Global.surfaceContext.getResources(), bitmapId);
                    bitMapWidth = bitmapResource.getWidth();
                    bitMapHeight = bitmapResource.getHeight();
                    set = false;
                }

            }
            zDepth = transform.getPosition().z;
        }
        if (gameObject.toBeDestroy) {
            Destroy();
        }
    }

    @Override
    public int compareTo(@NonNull Renderer renderer) {
        if (zDepth > renderer.zDepth) {
            return 1;
        } else if (zDepth == renderer.zDepth) {
            return 0;
        } else {
            return -1;
        }
    }

    public void Destroy() {
        renderersList.remove(this);
    }
}