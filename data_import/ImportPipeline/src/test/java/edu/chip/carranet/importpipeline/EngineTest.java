package edu.chip.carranet.importpipeline;

import edu.chip.carranet.importpipeline.dispatch.Dispatchable;
import edu.chip.carranet.importpipeline.fetch.Fetcher;
import edu.chip.carranet.importpipeline.output.Outputter;
import edu.chip.carranet.importpipeline.process.ProcessException;
import edu.chip.carranet.importpipeline.process.Processor;
import org.apache.log4j.BasicConfigurator;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static junit.framework.Assert.*;

/**
 * Created by IntelliJ IDEA.
 * User: justinquan
 * Date: 2/16/11
 * Time: 1:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class EngineTest {
    @Before
    public void setUp() {
        BasicConfigurator.configure();
//        Logger.getRootLogger().setLevel(Level.DEBUG);
    }

    @Test
    public void testHappyCase() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(1);
        CountDownLatch outputterLatch = new CountDownLatch(1);


        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher(), fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor(), processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);

        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());

            assertFalse(sb.toString().isEmpty());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testNullFetcher() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(0);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher(){
            @Override
            public StringData fetch() throws FetchException {
                return null;
            }
        }, fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor(), processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);
        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());

            assertTrue(sb.toString().isEmpty());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testNullProcessor() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(0);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher(), fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor() {
            @Override
            public Dispatchable process(Dispatchable data) {
                return null;
            }
        }, processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);
        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());

            assertTrue(sb.toString().isEmpty());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testEngineAbortOnException() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(1);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher(), fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor() {
            @Override
            public Dispatchable process(Dispatchable data) throws ProcessException {
                throw new ProcessException("FAILED");
            }
        }, processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);

        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());
            assertTrue(sb.toString().isEmpty());
            engine.getShutDownLatch().await(10, TimeUnit.SECONDS);
            assertEquals(0,engine.getShutDownLatch().getCount());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testEngineFetcherAbortOnException() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(0);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher() {
            @Override
            public StringData fetch() throws FetchException {
                throw new NullPointerException("NPE");
            }
        }, fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor(), processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);

        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());
            assertTrue(sb.toString().isEmpty());
            engine.getShutDownLatch().await(10, TimeUnit.SECONDS);
            assertEquals(0,engine.getShutDownLatch().getCount());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testEngineFetcherAbortOnError() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(0);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher() {
            @Override
            public StringData fetch() throws FetchException {
                throw new Error("Error");
            }
        }, fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor(), processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);

        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());
            assertTrue(sb.toString().isEmpty());
            engine.getShutDownLatch().await(10, TimeUnit.SECONDS);
            assertEquals(0,engine.getShutDownLatch().getCount());
        } finally {
            engine.stop();
        }
    }

    @Test
    public void testEngineAbortOnError() throws Exception {
        StringBuffer sb = new StringBuffer();
        CountDownLatch fetcherLatch = new CountDownLatch(1);
        CountDownLatch processorLatch = new CountDownLatch(1);
        CountDownLatch outputterLatch = new CountDownLatch(0);

        Fetcher mockFetcher = new DelegatingTestFetcher(new MockFetcher(), fetcherLatch);
        Processor mockProcessor = new DelegatingTestProcessor(new MockProcessor() {
            @Override
            public Dispatchable process(Dispatchable data) throws ProcessException {
                throw new Error("FAILED");
            }
        }, processorLatch);
        Outputter mockOutputter = new DelegatingTestOutputter(new MockOutputter(sb), outputterLatch);

        List<Fetcher> fetcherList = new ArrayList<Fetcher>();
        fetcherList.add(mockFetcher);
        Map<Dispatchable.DispatchType,Processor> processorList = new HashMap<Dispatchable.DispatchType,Processor>();
        processorList.put(Dispatchable.DispatchType.TEST, mockProcessor);
        Map<Dispatchable.DispatchType,Outputter> outputterList = new HashMap<Dispatchable.DispatchType,Outputter>();
        outputterList.put(Dispatchable.DispatchType.TEST, mockOutputter);

        IngestionEngine engine = new IngestionEngine(fetcherList, processorList, outputterList);

        try {
            engine.start();
            fetcherLatch.await();
            assertEquals(0, fetcherLatch.getCount());
            processorLatch.await();
            assertEquals(0, processorLatch.getCount());
            outputterLatch.await();
            assertEquals(0, outputterLatch.getCount());
            assertTrue(sb.toString().isEmpty());
            engine.getShutDownLatch().await(10, TimeUnit.SECONDS);
            assertEquals(0,engine.getShutDownLatch().getCount());
        } finally {
            engine.stop();
        }
    }
}
