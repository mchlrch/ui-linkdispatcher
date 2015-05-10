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
import java.net.URI;

import javax.swing.JOptionPane;

public class Launcher {

	public static void main(String[] args) {
		if (args.length == 0) {
			fail("missing <link-target> parameter.");

		} else {
			final String link = args[0];
			
			final Dispatcher dispatcher = createDispatcher();
			try {
				dispatcher.dispatch(link);
			} catch (DispatchException e) {
				fail(e);
			}
		}
	}
	
	private static void fail(DispatchException e) {
		StringBuffer msg = new StringBuffer();
		msg.append(e.getMessage()).append("\n");
		if (e.getCause() != null) {
			msg.append(e.getCause().getMessage());	
		}
		
		fail(msg.toString());
	}
	
	private static void fail(String message) {
		JOptionPane.showMessageDialog(null, message, "Linkdispatcher Error", JOptionPane.ERROR_MESSAGE);
		System.exit(1);
	}

	private static Dispatcher createDispatcher() {
		final LinkHandler linkHandler = new LinkHandler();

		final Dispatcher dispatcher = new Dispatcher() {
			
			@Override
			protected void open(File file) throws IOException {
				linkHandler.open(file);					
			}
			
			@Override
			protected void browse(URI uri) throws IOException {
				linkHandler.browse(uri);			
			}
		};
		
		return dispatcher;
	}

}
