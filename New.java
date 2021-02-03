import java.io.*;

public class MeetingSolver {
    public static void main(String[] args) throws IOException {
        String path = new File(".").getCanonicalPath();
        Runtime re = Runtime.getRuntime();
        BufferedReader output = null;
        try{
            Process cmd = re.exec("java -jar " +path + "\\sat4j-maxsat.jar " + path +"\\e.cnf");
            output =  new BufferedReader(new InputStreamReader(cmd.getInputStream()));
        } catch (IOException ioe){
            ioe.printStackTrace();
        }
        String s = "";
        while((s = output.readLine()) != null){
            System.out.println(s);
        }
    }

}