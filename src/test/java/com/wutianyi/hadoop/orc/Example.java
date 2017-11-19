package com.wutianyi.hadoop.orc;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.hive.ql.exec.vector.*;
import org.apache.orc.OrcFile;
import org.apache.orc.Reader;
import org.apache.orc.RecordReader;
import org.junit.Test;

import java.io.IOException;

public class Example {

    @Test
    public void readWiFiOrcData() throws IOException {
        Reader reader = OrcFile.createReader(new Path("D:\\study\\hadoop\\orc\\orc-1.4.1\\examples\\TestOrcFile.test1.orc"), OrcFile.readerOptions(new Configuration()));
        RecordReader rows = reader.rows();
        VectorizedRowBatch batch = reader.getSchema().createRowBatch();

        while (rows.nextBatch(batch)) {

//            BytesColumnVector c1 = (BytesColumnVector) batch.cols[0];
//            BytesColumnVector c2 = (BytesColumnVector) batch.cols[1];
//            BytesColumnVector c3 = (BytesColumnVector) batch.cols[2];
//            ListColumnVector c4 = (ListColumnVector) batch.cols[3];
            ListColumnVector c11 = (ListColumnVector) batch.cols[10];
            for (int r = 0; r < batch.size; r++) {

                StructColumnVector struct = (StructColumnVector) c11.child;
                LongColumnVector f1 = (LongColumnVector) struct.fields[0];
                BytesColumnVector f2 = (BytesColumnVector) struct.fields[1];
                for (long i = c11.offsets[r]; i < c11.offsets[r] + c11.lengths[r]; i++) {
                    System.out.println(f1.vector[(int) i]);
                }


//                System.out.println(f1.vector);
//                System.out.println(c1.toString(r));
//                StructColumnVector child = (StructColumnVector) c4.child;


//                System.out.println(c4.);
//                String imei = new String(c1.);
//                String mac = new String(c2.vector[r]);
//                String guid = new String(c3.vector[r]);
//                StructColumnVector struct = c4.child.
            }
        }
        rows.close();
    }
}
