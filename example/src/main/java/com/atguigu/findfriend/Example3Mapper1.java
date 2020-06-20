package com.atguigu.findfriend;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/*
 * keyin-valuein:  （A:B,C,D,F,E,O）
	map(): 将valuein拆分为若干好友，作为keyout写出
			将keyin作为valueout
	keyout-valueout: （友:用户）
					（c:A）,(C:B),(C:E)
 */
public class Example3Mapper1 extends Mapper<Text, Text, Text, Text> {
    private Text out_key = new Text();

    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        String[] friends = value.toString().split(",");

        for (String friend : friends) {
            out_key.set(friend);
            context.write(out_key, key);
        }

    }
}
