package com.lightdemo.postmsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:dispatcher-servlet.xml")
public class TestMsgSend extends AbstractJUnit4SpringContextTests{
	
	@Test
	public void testPostMsg() throws Exception {
		
	}
}

