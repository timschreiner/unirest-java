
import com.mashape.unirest.http.utils.MapUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
public class Benchmarks {

    public static void main(String[] args) throws InterruptedException {
        int maxRuns = 10000;
        int samples = 10;
        int mapSize = 10;

        long start;
        long end;

        Map<String, Object> map = new HashMap<String, Object>();

        List<Integer> track = new ArrayList<Integer>();

        for (int sample = 0; sample < samples; sample++) {
            for (int size = 0; size < mapSize; size++) {
                for (int j = 0; j < size; j++) {
                    map.put("key_" + j, "value");
                }
                System.out.println("size = " + size);

                start = System.nanoTime();

                for (int i = 0; i < maxRuns; i++) {
                    track.add(MapUtil.getList(map).size());
                }

                end = System.nanoTime();
                Thread.sleep(100);
                System.out.println("DEFAULT " + (end - start) / 1000);

                start = System.nanoTime();

                for (int i = 0; i < maxRuns; i++) {
                }

                end = System.nanoTime();
                Thread.sleep(100);
                System.out.println("YOURS " + (end - start) / 1000);

                System.out.println("");

            }
        }

        System.out.println("\n\n");
        System.out.println(track.size());
    }
}
