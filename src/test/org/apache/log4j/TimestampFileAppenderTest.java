package org.apache.log4j;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.xml.DOMConfigurator;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class TimestampFileAppenderTest {
  private static final Logger logger =
      Logger.getLogger(TimestampFileAppenderTest.class);

  protected static final String CONFIG_PROPERTIES = "data/test/config.properties";
  protected static final String CONFIG_XML = "data/test/config.xml";
  protected static final String MESSAGE = "Hello world!";
  protected static final String TMP_DIRECTORY = "data/test/tmp/";
  protected static final String GOLDEN_FILE = "data/test/golden.log";

  @Test
  public void testLogFromXMLConfigFile() {
    DOMConfigurator.configure(CONFIG_XML);
    try {
      run();
    } catch (IOException e) {
      fail();
    }
  }

  @Test
  public void testLogFromPropertiesConfigFile() {
    PropertyConfigurator.configure(CONFIG_PROPERTIES);
    try {
      run();
    } catch (IOException e) {
      fail();
    }
  }

  protected void run() throws IOException {
    final String timestamp =
        new SimpleDateFormat("yyyyMMdd-HH'h'mm'm'ss's'").format(Calendar.getInstance().getTime());
    final String filename = "test_" + timestamp + ".log";
    final File golden = new File(GOLDEN_FILE);
    logger.info(MESSAGE);
    File dir = new File(TMP_DIRECTORY);
    Assert.assertEquals(true, dir.exists());
    for(File file: dir.listFiles()) {
      Assert.assertEquals(filename, file.getName());
      Assert.assertEquals(true, FileUtils.contentEquals(golden, file));
    }
  }

  @After
  public void clean() {
    File dir = new File(TMP_DIRECTORY);
    if(dir.exists()) {
      for(File file: dir.listFiles()) {
        file.delete();
      }
      dir.delete();
    }
  }
}
