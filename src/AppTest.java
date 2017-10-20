import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class AppTest {

    public static void main(String[] args) {

        String[] command = new String[]{
                "C:" +
                        File.separator +
                        "Users" +
                        File.separator +
                        System.getProperty("user.name") +
                        File.separator +
                        "AppData" + File.separator + "Roaming" + File.separator + "npm" + File.separator + "openjscad.cmd",

                "example005.jscad"
        };

        StringBuffer output = new StringBuffer();

        try {
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(p.getInputStream()));

            String line = "";
            while ((line = reader.readLine()) != null) {
                output.append(line + "\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println(output.toString());
    }
}
