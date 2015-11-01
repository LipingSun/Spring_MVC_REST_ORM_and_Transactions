package edu.sjsu.cmpe275.lab2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Lab2MvcRestOrmAndTransactionsApplication.class)
@WebAppConfiguration
public class Lab2MvcRestOrmAndTransactionsApplicationTests {

	@Test
	public void contextLoads() {
	}

}
