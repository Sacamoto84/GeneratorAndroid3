package com.example.generator2;

import android.graphics.Bitmap;

public class itemList {
    private String name = "xxx";     // название без окончания
    private String filename; // Название файла
    private String path;     // Путь к файлу

    Bitmap bitmap; //Картинка несущей

    //Конструктор
    public itemList(String _path, String _filename, int mod){
        path = _path;
        filename = _filename;
        name = filename.replace(".dat","");
        if (mod == 0)
            bitmap = Utils.CreateBitmapCarrier2( path+filename );
        else
            bitmap = Utils.CreateBitmapModulation( path+filename );
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
    public Bitmap getBitmap() {
        return this.bitmap;
    }
}
