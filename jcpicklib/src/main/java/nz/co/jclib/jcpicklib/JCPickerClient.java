package nz.co.jclib.jcpicklib;

/**
 * Created by Johnnie on 28/03/17.
 */
public class JCPickerClient {

    private static JCPickerClient instance;

    public static JCPickerClient getInstance(){
        if(instance == null){
            instance = new JCPickerClient();
        }

        return instance;
    }

    private JCPickerClient() {}
}
