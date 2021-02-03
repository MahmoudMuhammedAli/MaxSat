import java.lang.StringBuilder;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.util.*;
import java.util.logging.*;

public class MaxSat {
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        ArrayList<String> lines = new ArrayList<>(); // an arraylist with each line in the cnf file inside of it
        InputStream terminalOutput; // to read the terminal output
        Reader reader; // to create the buffered reader with
        BufferedReader bufferReader; // to search for v
        String line;
        String solLine = null;
        int[] days;
       
        System.out.println("How many people are we talking here:");
        int n = input.nextInt();
        int m = 7;
        System.out.println("Now enter the free days for each user using: \n \n"
                + "1-Saturday "
                + "\n2-Sunday "
                + "\n3-Monday "
                + "\n4-Tuesday "
                + "\n5-Wednesday "
                + "\n6-thursday "
                + "\n7-Friday  \n \n");
        
        String freedays = input.nextLine(); // i had to d it like that cuz other wise the first line was always 0 for some reason
                
        lines.add("p cnf " + m + " " + n); 
        /*  p cnf  num of days    num of users
            user1 free days 0
            user2 free days 0
            ...
        */
        for(int i=1; i<=n; i++){
            freedays = input.nextLine() + " 0";
            lines.add(freedays); //we add the lines to both the string and array list 
        }
        System.out.println("Now calculating for:");
        
        for(int i = 0; i < lines.size(); i++){
            System.out.println(lines.get(i)); //printing the whole thing all together
        }
        System.out.println("Is that right? Enter 'Y' to continue or 'N' to try again"); // let the user check if the input is onpoint or not
        char x = input.next().charAt(0);
        if(x=='y'|| x=='Y'){
        Path file = Paths.get("e.cnf"); // now we open the cnf file to save the input inside of it to get it ready  for the max sat
        try {
            Files.write(file, lines, Charset.forName("UTF-8"));
        } catch (IOException ex) { //can also be handeled by the user as the input gets shown to him and it's up to him to either continue or not
            Logger.getLogger(Boolean.class.getName()).log(Level.SEVERE, null, ex);
        }
        Process p = null;
        Runtime r = Runtime.getRuntime(); // making a process to excute from the terminal
        try {
            p = r.exec("java -jar sat4j-maxsat.jar e.cnf" ); // execute the command to open the jar file in the terminal and pass e.cnf to it
            p.getOutputStream().close(); // close stdin of child

             // making an inputstream to save the output of the terminal command so that we can find the out put of the program later by searching for "V" inside of it
            terminalOutput = p.getInputStream();
            reader = new InputStreamReader(terminalOutput); //reading it using an inputStream reader to make a buffer reader out of it 
            bufferReader = new BufferedReader(reader);
            while ((line = bufferReader.readLine()) != null) {
                if(line.charAt(0)=='v'){
                    solLine=line;
                    System.out.println("The Solution is :");
                    System.out.println(line); // we read the terminal output until we find v "which is the flag for the ot put int the terminal"when we find it  we print every thing after it
                }
            }
            p.waitFor(); // freeze the thread until the process p is done then continue
        }
        catch (InterruptedException | IOException e) {
            System.out.println(e.toString());
        }
        finally{
            if (p != null)
                p.destroy(); // kill the process when it's done for
        }
        System.out.println("\n\n\nYou guys can go out on:");
        String[] s = solLine.split(" ");
        days = new int[s.length-1];
        for(int i =1 ; i<s.length ; i++){
           days[i-1]= Integer.parseInt(s[i]);
        }
        for(int i =0 ; i<days.length ; i++){
            switch(days[i]){
                case 1:
                    System.out.println("-saturday");
                    break;
                case 2:
                    System.out.println("-sunday");
                    break;
                case 3:
                    System.out.println("-monday");
                    break;
                case 4:
                    System.out.println("-tuesday");
                    break;
                case 5:
                    System.out.println("-wednesday");
                    break;
                case 6:
                    System.out.println("-thurstday");
                    break;
                case 7:
                    System.out.println("-friday");
                    break;
                
                default:
                    
                    break;
                
            }
        }
         System.out.println("\n\n\n\n");

    }    
          else{
            System.out.println("The input is wrong and you should try again");
            System.exit(1);
          }
   }
  
}
/*
Sample output of the -jar sat4j-maxsat.jar e.cnf command which gets saved in the inputStream terminaloutput:
C:\Users\mahmoud\OneDrive\Desktop\task305>java -jar sat4j-maxsat.jar e.cnf
c SAT4J: a SATisfiability library for Java (c) 2004-2013 Artois University and CNRS
c This is free software under the dual EPL/GNU LGPL licenses.
c See www.sat4j.org for details.
c This software uses some libraries from the Jakarta Commons project. See jakarta.apache.org for details.
c version 2.3.5.v20130525
c java.runtime.name     Java(TM) SE Runtime Environment
c java.vm.name          Java HotSpot(TM) 64-Bit Server VM
c java.vm.version       14.0.1+7
c java.vm.vendor        Oracle Corporation
c sun.arch.data.model   64
c java.version          14.0.1
c os.name               Windows 10
c os.version            10.0
c os.arch               amd64
c Free memory           264322432
c Max memory            4246732800
c Total memory          266338304
c Number of processors  12
c Pseudo Boolean Optimization by upper bound
c c --- Begin Solver configuration ---
c org.sat4j.pb.constraints.CompetResolutionPBLongMixedWLClauseCardConstrDataStructure@3b192d32
c Learn all clauses as in MiniSAT
c claDecay=0.999 varDecay=0.95 conflictBoundIncFactor=1.5 initConflictBound=100
c VSIDS like heuristics from MiniSAT using a heap lightweight component caching from RSAT taking into account the objective function
c No reason simplification
c Glucose 2.1 dynamic restart strategy
c Glucose 2 learned constraints deletion strategy
c timeout=2147483s
c DB Simplification allowed=false
c Listener: org.sat4j.minisat.core.VoidTracing@2a33fae0
c --- End Solver configuration ---
c solving e.cnf
c reading problem ...
c ... done. Wall clock time 0.012s.
c declared #vars     7
c internal #vars     11
c #constraints  4
c constraints type
c org.sat4j.minisat.constraints.cnf.OriginalWLClause => 4
c 4 constraints processed.
c OPTIMIZING...
c Got one! Elapsed wall clock time (in seconds):0.025
o 0
c starts                : 1
c conflicts             : 0
c decisions             : 10
c propagations          : 11
c inspects              : 7
c shortcuts             : 0
c learnt literals       : 0
c learnt binary clauses : 0
c learnt ternary clauses        : 0
c learnt constraints    : 0
c ignored constraints   : 0
c root simplifications  : 0
c removed literals (reason simplification)      : 0
c reason swapping (by a shorter reason) : 0
c Calls to reduceDB     : 0
c Number of update (reduction) of LBD   : 0
c Imported unit clauses : 0
c number of reductions to clauses (during analyze)      : 0
c number of learned constraints concerned by reduction  : 0
c number of learning phase by resolution        : 0
c number of learning phase by cutting planes    : 0
c speed (assignments/second)    : 234.04255319148936
c non guided choices    6
c learnt constraints type
c constraints type
c org.sat4j.minisat.constraints.cnf.OriginalWLClause => 4
c 4 constraints processed.
s OPTIMUM FOUND
c Found 1 solution(s)
v -1 -2 3 -4 -5 -6 -7 0 
c objective function=0
c Total wall clock time (in seconds): 0.087 
*/