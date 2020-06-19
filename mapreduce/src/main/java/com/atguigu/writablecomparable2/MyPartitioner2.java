package com.atguigu.writablecomparable2;

import com.atguigu.writablecomparable.FlowBean;
import org.apache.hadoop.mapreduce.Partitioner;

import javax.xml.soap.Text;

public class MyPartitioner2 extends Partitioner<FlowBean, Text> {

    @Override
    public int getPartition(FlowBean flowBean, Text text, int numPartitions) {
        switch (text.toString().substring(0, 3)) {
            case "136":
                return 0;
            case "137":
                return 1;
            case "138":
                return 2;
            case "139":
                return 3;
            default:
                return 4;
        }
    }
}
