package domain;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Random;

public class Generator {
    public Gson gson;
    public String[] fnames;
    public String[] mnames;
    public String[] snames;
    public String[] locations;

    public Generator() throws FileNotFoundException {
        this.gson = new Gson();

        this.fnames  = gson.fromJson(new FileReader(new File("familmapserver/json/fnames.json")),String[].class);
        this.mnames = gson.fromJson(new FileReader(new File("familmapserver/json/mnames.json")),String[].class);
        this.snames = gson.fromJson(new FileReader(new File("familmapserver/json/snames.json")),String[].class);
        this.locations = gson.fromJson(new FileReader(new File("familmapserver/json/fnames.json")),String[].class);

    }

    public String getfName(){
        Random random = new Random();
        return fnames[random.nextInt(fnames.length)];
    }

    public String getmName(){
        Random random = new Random();
        return mnames[random.nextInt(mnames.length)];
    }

    public String getsName(){
        Random random = new Random();
        return snames[random.nextInt(snames.length)];
    }

    public void loadLocation(Event event){

    }
}
