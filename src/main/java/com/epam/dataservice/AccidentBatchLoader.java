package com.epam.dataservice;


import com.epam.data.RoadAccident;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;

public class AccidentBatchLoader implements Callable<Integer> {

    private Integer batchSize;
    private BlockingQueue<List<RoadAccident>> dataQueue;
    private String dataFileName;
    private RoadAccidentParser roadAccidentParser;

    public AccidentBatchLoader(int batchSize, BlockingQueue<List<RoadAccident>> dataQueue, String dataFileName){
        this.batchSize = batchSize;
        this.dataQueue = dataQueue;
        this.dataFileName = dataFileName;
        roadAccidentParser = new RoadAccidentParser();
    }
    
    @Override
    public Integer call() throws Exception {
        int dataCount = 0;
        Iterator<CSVRecord> recordIterator = getRecordIterator();

        int batchCount = 0;
        List<RoadAccident> roadAccidentBatch = null;

        boolean isDataLoadFinished = false;
        while(!isDataLoadFinished){
            roadAccidentBatch = getNextBatch(recordIterator);
            dataCount = dataCount + roadAccidentBatch.size();
            if(roadAccidentBatch.isEmpty()){
                isDataLoadFinished = true;
            }else{
                ++batchCount;
                System.out.println(" Completed reading " + dataCount + " in " + batchCount + " batches for " + dataFileName);
            }
            dataQueue.put(roadAccidentBatch);
        }
        //dataQueue.put(roadAccidentBatch); //Epmty batch can be used as identifier for end of record production
        return dataCount;
    }

    private Iterator<CSVRecord> getRecordIterator() throws Exception{
        Reader reader = new FileReader(dataFileName);
        return new CSVParser(reader, CSVFormat.EXCEL.withHeader()).iterator();
    }

    private List<RoadAccident> getNextBatch(Iterator<CSVRecord> recordIterator){
        List<RoadAccident> roadAccidentBatch = new ArrayList<RoadAccident>();
        int recordCount = 0;
        RoadAccident roadAccidentItem = null;
        while(recordCount <= batchSize && recordIterator.hasNext() ){
            roadAccidentItem = roadAccidentParser.parseRecord(recordIterator.next());
            if(roadAccidentItem != null){
                roadAccidentBatch.add(roadAccidentItem);
                recordCount++;
            }
        }
        return  roadAccidentBatch;
    }
}
