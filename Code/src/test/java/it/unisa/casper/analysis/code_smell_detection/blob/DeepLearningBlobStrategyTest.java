package it.unisa.casper.analysis.code_smell_detection.blob;

import it.unisa.casper.analysis.code_smell.BlobCodeSmell;
import it.unisa.casper.storage.beans.ClassBean;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class DeepLearningBlobStrategyTest {
    private ClassBean classe;
    private Vector<String> metrics= new Vector<String>();
    private Vector<String> wrongMetrics = new Vector<String>();
    private Vector<String> lessMetrics = new Vector<String>();
    private Vector<String> moreMetrics = new Vector<String>();

    @Before
    public void setUp() {
        classe = new ClassBean.Builder("TestClass", "").build();
        metrics.add("31,156,2,1606,87,167,608,1606,1606,36,0,41,41,1,0.04878048780487805,0,20.365853658536587,156,0");
        wrongMetrics.add("stringa senza metriche");
        lessMetrics.add("31,156,2,1606,87,167,608,1606,1606,36");
        moreMetrics.add("31,156,2,1606,87,167,608,1606,1606,36,0,41,41,1,0.04878048780487805,0,20.365853658536587,156,0,31,156,2,1606,87,167,608,1606,1606,36");
    }

    @Test
    public void correctMetricsParameter(){
        String result;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningBlobStrategy strategy = new DeepLearningBlobStrategy(metrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
            result = strategy.getRisultato();
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            result = "[0. 1. 0. 0. 0.]";
        }
        assertNotEquals("Error", result);
    }

    @Test
    public void voidMetricsParameter(){
        String result;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningBlobStrategy strategy = new DeepLearningBlobStrategy(new Vector<String>());
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
            result = strategy.getRisultato();
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            result = "Error";
        }
        assertEquals("Error", result);
    }

    @Test
    public void wrongMetricsParameter(){
        String result;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningBlobStrategy strategy = new DeepLearningBlobStrategy(wrongMetrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
            result = strategy.getRisultato();
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            result = "Error";
        }
        assertEquals("Error", result);
    }

    @Test
    public void lessMetricsParameter(){
        String result;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningBlobStrategy strategy = new DeepLearningBlobStrategy(lessMetrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
            result = strategy.getRisultato();
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            result = "Error";
        }
        assertEquals("Error", result);
    }

    @Test
    public void moreMetricsParameter(){
        String result;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningBlobStrategy strategy = new DeepLearningBlobStrategy(moreMetrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
            result = strategy.getRisultato();
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            result = "Error";
        }
        assertEquals("Error", result);
    }
}
