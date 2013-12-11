/**
 * 
 * Copyright 2013 Paul Scherrer Institute. All rights reserved.
 * 
 * This code is free software: you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation, either version 3 of the License, or (at your option) any
 * later version.
 * 
 * This code is distributed in the hope that it will be useful, but without any
 * warranty; without even the implied warranty of merchantability or fitness for
 * a particular purpose. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this code. If not, see <http://www.gnu.org/licenses/>.
 * 
 */
package ch.psi.streamer;

import java.nio.file.FileSystems;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.google.common.eventbus.EventBus;

import ch.psi.streamer.FileReceiver;
import ch.psi.streamer.FileSender;

/**
 * @author ebner
 *
 */
public class FileReceiverTest {
	
	private static final Logger logger = Logger.getLogger(FileReceiverTest.class.getName());

	private FileSender sender;
	private FileReceiver receiver;
	
	@Before
	public void setUp() throws Exception {
		sender = new FileSender(new EventBus(), 8080, 100, false);
		receiver = new FileReceiver("emac", 8080, "target");
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void test() {
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
				System.out.println("re");
				sender.start();
				sender.onFile(new DetectedFile(FileSystems.getDefault().getPath("src/test/resources/testfile.png"),""));
				sender.terminate();
				
				receiver.terminate();
				logger.info("Done");
			}
		}, 1000);
		receiver.receive();
	}

}
