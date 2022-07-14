package it.unisa.casper.analysis.code_smell_detection;

import it.unisa.casper.analysis.code_smell.BlobCodeSmell;
import it.unisa.casper.storage.beans.*;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.logging.Logger;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DeepLearningCodeSmellStrategyTest {

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
        DeepLearningStrategy strategy = new DeepLearningStrategy(metrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            risultato = classe.isAffected(smell);
            log.info("\n" + strategy.getResult());
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            risultato = true;
        }
        assertTrue(risultato);
    }

    @Test
    public void voidMetricsParameter(){
        boolean risultato;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningStrategy strategy = new DeepLearningStrategy(new Vector<String>());
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            risultato = classe.isAffected(smell);
            log.info("\n" + strategy.getResult());
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            risultato = false;
        }
        assertFalse(risultato);
    }

    @Test
    public void wrongMetricsParameter(){
        boolean risultato;
        java.util.logging.Logger log = Logger.getLogger(getClass().getName());
        DeepLearningStrategy strategy = new DeepLearningStrategy(wrongMetrics);
        BlobCodeSmell smell = new BlobCodeSmell(strategy, "Deep Learning");
        try {
            risultato = classe.isAffected(smell);
            log.info("\n" + strategy.getResult());
        }
        catch (RuntimeException e){
            log.info("\n" + e.getMessage());
            risultato = false;
        }
        assertFalse(risultato);
    }
}

