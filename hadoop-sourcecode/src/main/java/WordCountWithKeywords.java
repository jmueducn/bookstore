import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Counter;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

public class WordCountWithKeywords {

    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, IntWritable> {

        static enum CountersEnum { KEYWORD_COUNT }

        private final static IntWritable one = new IntWritable(1);
        private Text word = new Text();
        private Set<String> keywords = new HashSet<>(Arrays.asList("a", "mystery", "software", "code", "algorithms:", "wizard"));

        @Override
        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            StringTokenizer itr = new StringTokenizer(value.toString());
            while (itr.hasMoreTokens()) {
                String token = itr.nextToken().toLowerCase();// Convert to lowercase for case-insensitive comparison
                if (keywords.contains(token)) {


                    word.set(token);

                    context.write(word, one);
                    Counter counter = context.getCounter(CountersEnum.class.getName(),
                            CountersEnum.KEYWORD_COUNT.toString());
                    counter.increment(1);
                }
            }
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, IntWritable, Text, IntWritable> {
        private IntWritable result = new IntWritable();

        public void reduce(Text key, Iterable<IntWritable> values,
                           Context context
        ) throws IOException, InterruptedException {
            int sum = 0;
            for (IntWritable val : values) {

                sum += val.get();
            }
            result.set(sum);
            System.out.println(sum);
            context.write(key, result);
        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf, "word count");
        job.setJarByClass(WordCountWithKeywords.class);
        job.setMapperClass(TokenizerMapper.class);
        job.setCombinerClass(IntSumReducer.class);
        job.setReducerClass(IntSumReducer.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        FileInputFormat.addInputPath(job, new Path(args[0]));
        FileOutputFormat.setOutputPath(job, new Path(args[1]));
        System.out.println("Number of requested mappers: " + conf.getInt("mapreduce.job.maps", -1));
        System.out.println("Number of requested reducers: " + conf.getInt("mapreduce.job.reduces", -1));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
