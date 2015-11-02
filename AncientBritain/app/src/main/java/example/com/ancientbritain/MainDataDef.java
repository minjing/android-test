package example.com.ancientbritain;

/**
 * Created by min on 15/11/2.
 */
public class MainDataDef {

    int image;
    String name;
    String info;

    public MainDataDef(int image, String name, String info) {
        this.image = image;
        this.name = name;
        this.info = info;
    }

    public int getImage() { return image; }
    public String getName() { return name; }
    public String getInfo() { return info; }
}
