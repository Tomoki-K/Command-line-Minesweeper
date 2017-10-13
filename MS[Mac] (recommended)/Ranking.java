import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;;

class Rank {
    String name;
    double time;

    public Rank(String name, double time){
        this.name = name;
        this.time = time;
    }

}

/*==================== Ranking class ====================*/

public class Ranking extends Rank {
    public Ranking(String name, double time){
        super(name, time);
    }
    static Rank[] ranks = new Rank[10];
    // show ranking
    public static void rank() {
        try {
            // read file
            Scanner scan = new Scanner(new FileReader("Ranking.txt"));
            // read line -> add each line to Array
            for (int i = 0 ; i < ranks.length; i++) {
                ranks[i] = new Rank(scan.next(), toDouble(scan.next()));
            }
            // add new data if time is shorter than last data
            if (ranks[9].time >= getTime() || ranks[9].time == 0.0) {
                ranks[9] = new Rank(getName(), getTime());
            }

            // sort by time
            Arrays.sort(ranks, new Comparator<Rank>() {
                @Override
                public int compare(Rank rank1, Rank rank2) {
                    return (int) rank1.time * 10 - (int) rank2.time * 10;
                }
            });

            // overwrite ranking file
            BufferedWriter writer = new BufferedWriter(new FileWriter("Ranking.txt"));
            String[] Cnt = {"1st : ", "2nd : ", "3rd : ", "4th : ", "5th : ", "6th : ", "7th : ", "8th : ", "9th : ", "10th : "};
            for (int j = 0; j < ranks.length; j++) {
                if (Objects.equals(ranks[j].name, "(noData)") && ranks[j].time == 3600.0) {
                    System.out.println(j + 1 + "th : -no data-");
                } else {
                    if (myRank(j)) {
                        System.out.println("\u001B[00;31m" + Cnt[j] + format(ranks[j].name, ranks[j].time) + "\u001B[00m");
                    } else {
                        System.out.println(Cnt[j] + format(ranks[j].name, ranks[j].time));
                    }
                }
                // overwrite Ranking.txt
                writer.write(ranks[j].name + " " + ranks[j].time);
                writer.newLine();
            }
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error : Could not find file.");
        } catch (IOException e) {
            System.out.println("Error : Something went wrong.");
        }
    }

    // convert to Double
    private static double toDouble(String time) {
        return Double.parseDouble(time);
    }

    private static String format(String name, Double time) {
        return name + " [ " + time + " seconds ]";
    }

    private static String getName() {
        return Minesweeper.getName();
    }

    private static Double getTime() {
        return Timer.dblTime;
    }

    private static boolean myRank(int j) {
        return Objects.equals(ranks[j].name, getName()) && Objects.equals(ranks[j].time, getTime());
    }
}
