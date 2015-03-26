package com.gmail.user0abc.max_one;/*Created by Sergey on 3/24/2015.*/

import android.app.Activity;
import com.gmail.user0abc.max_one.model.GameContainer;
import com.gmail.user0abc.max_one.util.Logger;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SaveGameService extends Activity {

    private static SaveGameService saveGameService;

    public static void saveGame(GameContainer game) throws IOException {
        //fileSave(game);
    }

    private static void fileSave(GameContainer game) throws IOException {
        String fileName = getName(game);
        Logger.log("Saving into "+fileName);
        FileOutputStream fos = getFile(fileName);
        ObjectOutputStream save = new ObjectOutputStream(fos);
        save.writeObject(game);
        save.close();
        Logger.log("Game saved into "+fileName);
    }

    private static FileOutputStream getFile(String name) throws IOException {
        File f = new File(name);
        if(!f.exists()){
            f.createNewFile();
        }else{
            f.delete();
            f.createNewFile();
        }
        return new FileOutputStream(f);
    }

    private static String getName(GameContainer game) {
        StringBuilder sb = new StringBuilder("/data/com.gmail.user0abc.max_one/files/");
        sb.append(Integer.toString(game.seed)).append("+").append(".sav");
        return sb.toString();
    }

    public static SaveGameService getSaveGameService() {
        if(saveGameService == null) saveGameService = new SaveGameService();
        return saveGameService;
    }
}
