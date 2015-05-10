/*********************************************************************
  This file is part of ui-linkdispatcher,
  see <https://github.com/miraman/ui-linkdispatcher>

  Copyright (C) 2015 Michael Rauch

  ui-linkdispatcher is free software: you can redistribute it and/or modify
  it under the terms of the GNU General Public License as published by
  the Free Software Foundation, either version 3 of the License, or
  (at your option) any later version.

  ui-linkdispatcher is distributed in the hope that it will be useful,
  but WITHOUT ANY WARRANTY; without even the implied warranty of
  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  GNU General Public License for more details.

  You should have received a copy of the GNU General Public License
  along with ui-linkdispatcher.  If not, see <http://www.gnu.org/licenses/>.
*********************************************************************/
package ch.miranet.linkdispatcher;

import java.io.File;
import java.io.IOException;

import org.junit.Assert;
import org.junit.Assume;
import org.junit.Test;

public class DispatcherTest {

	private final DispatcherMock dispatcher = new DispatcherMock();
	
	@Test
	public void dispatchFilePath() throws DispatchException {
		String userHome = System.getProperty("user.home");		
		dispatcher.dispatch(userHome);
		
		Assert.assertTrue(dispatcher.openCalledExactlyOnce());
	}
	
	@Test
	public void dispatchFileURL() throws DispatchException {
		String userHome = System.getProperty("user.home");		
		dispatcher.dispatch("file://" + userHome); 
		
		Assert.assertTrue(dispatcher.openCalledExactlyOnce());
	}
	
	@Test
	public void dispatchWebURL() throws DispatchException {
		dispatcher.dispatch("http://www.miranet.ch");
		
		Assert.assertTrue(dispatcher.browseCalledExactlyOnce());
	}

	// --------------------------------------------
	
	@Test(expected=DispatchException.class)
	public void failOnNonExistantFilePath() throws DispatchException, IOException {
		File tempFile = File.createTempFile("failOnNonExistantFilePath", "test");
		Assume.assumeTrue(tempFile.delete()); 
		
		dispatcher.dispatch("file://" + tempFile.getAbsolutePath());		
	}
	
	@Test(expected=DispatchException.class)
	public void failOnNonExistantFileURL() throws DispatchException, IOException {
		File tempFile = File.createTempFile("failOnNonExistantFilePath", "test");
		Assume.assumeTrue(tempFile.delete()); 
		
		dispatcher.dispatch(tempFile.getAbsolutePath());
	}
	
	@Test(expected=DispatchException.class)
	public void failOnInvalidWebURL() throws DispatchException {
		dispatcher.dispatch("@:@");
	}
	
	// --------------------------------------------
	
	@Test(expected=DispatchException.class)
	public void wrapExceptionsOnOpen() throws DispatchException {
		dispatcher.failInOpenAndBrowse = true;
		
		String userHome = System.getProperty("user.home");		
		dispatcher.dispatch(userHome);		
	}
	
	@Test(expected=DispatchException.class)
	public void wrapExceptionsOnBrowse() throws DispatchException {
		dispatcher.failInOpenAndBrowse = true;
		
		dispatcher.dispatch("http://www.miranet.ch");		
	}
	
}
