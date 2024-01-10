import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class KeywordCount {
    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("KeywordCount").setMaster("spark://172.19.0.2:7077");
        JavaSparkContext sc = new JavaSparkContext(conf);

        // 指定文件夹路径
        String inputPath = "/data/input";
        // 指定输出文件夹路径
        String outputPath = "/data/output";

        JavaRDD<String> input = sc.textFile(inputPath);

        Set<String> keywords = new HashSet<>(Arrays.asList("a", "mystery", "software", "code", "algorithms:", "wizard"));

        JavaPairRDD<String, Integer> counts = input
                .flatMap(x -> Arrays.asList(x.split(" ")).iterator())
                .filter(word -> keywords.contains(word.toLowerCase()))
                .mapToPair((PairFunction<String, String, Integer>) word -> new Tuple2<>(word, 1))
                .reduceByKey(Integer::sum);

        // 将结果保存到文件中
        counts.saveAsTextFile(outputPath);

        sc.stop();
    }
}
