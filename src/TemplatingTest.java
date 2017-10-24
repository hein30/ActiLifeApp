import java.io.File;
import java.io.StringWriter;
import java.util.Arrays;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class TemplatingTest {

    public static void main(String args[]) {
        File f = new File("test.vm");

        VelocityEngine templateEngine = new VelocityEngine();
        templateEngine.init();

        Template template = templateEngine.getTemplate("test.vm");

        VelocityContext context = new VelocityContext();
        context.put("mpa", Arrays.asList(1, 2, 3, 4, 5).toString());
        context.put("vpa", Arrays.asList(10, 20, 30, 40, 50).toString());
        context.put("subjectID", "I AM TEST SUBJECT");

        StringWriter writer = new StringWriter();
        template.merge(context, writer);


        System.out.println(writer.toString());
    }
}
