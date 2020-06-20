package com.atguigu.findfriend;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/*
 *keyin-valuein : (A-B,C),(A-B,E)
	reduce():
	keyout-valueout  ： (A-B:C,E)
 */
public class Example3Reducer2 extends Reducer<Text, Text, Text, Text> {
    private Text out_value = new Text();

    @Override
    protected void reduce(Text key, Iterable<Text> value, Reducer<Text, Text, Text, Text>.Context context)
            throws IOException, InterruptedException {

        StringBuffer sb = new StringBuffer();

        for (Text text : value) {

            sb.append(text.toString() + ",");
        }

        out_value.set(sb.toString());
        context.write(key, out_value);
    }
}
