package com.example.batch.kata.util;

import java.sql.Connection;
import java.sql.Statement;
import javax.sql.DataSource;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class CleanUpAfterTestExtension implements AfterEachCallback {

  @Override
  public void afterEach(ExtensionContext extensionContext) throws Exception {
    var ds = SpringExtension.getApplicationContext(extensionContext).getBean(DataSource.class);
    try (Connection connection = ds.getConnection(); Statement stmt = connection.createStatement();) {
      stmt.execute("TRUNCATE SCHEMA PUBLIC RESTART IDENTITY AND COMMIT NO CHECK");
      connection.commit();
    }
  }
}
