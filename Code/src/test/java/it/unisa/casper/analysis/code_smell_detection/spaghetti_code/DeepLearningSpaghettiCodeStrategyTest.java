package it.unisa.casper.analysis.code_smell_detection.spaghetti_code;

import it.unisa.casper.analysis.code_smell.BlobCodeSmell;
import it.unisa.casper.analysis.code_smell.SpaghettiCodeCodeSmell;
import it.unisa.casper.analysis.code_smell_detection.blob.DeepLearningBlobStrategy;
import it.unisa.casper.storage.beans.ClassBean;
import org.junit.Before;
import org.junit.Test;

import java.util.Vector;
import java.util.logging.Logger;

import static org.junit.Assert.*;

public class DeepLearningSpaghettiCodeStrategyTest {
    private ClassBean classe;
    private Vector<String> metrics= new Vector<String>();
    private Vector<String> wrongMetrics = new Vector<String>();

    @Before
    public void setUp() {
        classe = new ClassBean.Builder("TestClass", "").build();
        metrics.add("31,156,2,1606,87,167,608,1606,1606,36,0,41,41,1,0.04878048780487805,0,20.365853658536587,156,0");
        wrongMetrics.add("stringa senza metriche");
    }

    @Test
    public void correctMetricsParameter(){
        boolean risultato;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningSpaghettiCodeStrategy strategy = new DeepLearningSpaghettiCodeStrategy(metrics);
        SpaghettiCodeCodeSmell smell = new SpaghettiCodeCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
        }
        assertNotEquals("Error", strategy.getRisultato());
    }

    @Test
    public void voidMetricsParameter(){
        boolean risultato;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningSpaghettiCodeStrategy strategy = new DeepLearningSpaghettiCodeStrategy(new Vector<String>());
        SpaghettiCodeCodeSmell smell = new SpaghettiCodeCodeSmell(strategy, "Deep Learning");        try {
            classe.isAffected(smell);
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
        }
        assertEquals("Error", strategy.getRisultato());
    }

    @Test
    public void wrongMetricsParameter(){
        boolean risultato;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningSpaghettiCodeStrategy strategy = new DeepLearningSpaghettiCodeStrategy(wrongMetrics);
        SpaghettiCodeCodeSmell smell = new SpaghettiCodeCodeSmell(strategy, "Deep Learning");
        try {
            classe.isAffected(smell);
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
        }
        assertEquals("Error", strategy.getRisultato());
    }
}
